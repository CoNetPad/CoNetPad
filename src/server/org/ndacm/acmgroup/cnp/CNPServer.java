package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.network.ServerNetwork;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.CreateAccountTask;
import org.ndacm.acmgroup.cnp.task.CreatePrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateSessionTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.LoginTask;
import org.ndacm.acmgroup.cnp.task.SendResponseTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.TaskRequest;
import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory;
import org.ndacm.acmgroup.cnp.task.response.CreateAccountTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;

public class CNPServer implements TaskReceivedEventListener {

	private static final int USER_TOKEN_LENGTH = 10;
	private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private ServerNetwork network;
	private Database database;
	private Compiler compiler;
	private String baseDirectory;
	private Map<Integer, CNPSession> openSessions; // maps sessionID to //
	// CNPSession
	private TaskMessageFactory messageFactory;
	private ExecutorService serverExecutor; // for server-wide tasks (e.g. account creation)
	private Map<Integer, String> userAuthTokens; // userID to userAuthToken

	private SecretKey key; // TODO implement
	private Cipher cipher; // TODO implement
	private Random rand;

	public CNPServer(String baseDirectory) {

		this.baseDirectory = baseDirectory;
		network = new ServerNetwork();
		compiler = new Compiler();
		openSessions = new ConcurrentHashMap<Integer, CNPSession>();
		userAuthTokens = new ConcurrentHashMap<Integer, String>();
		TaskMessageFactory.initalizeMessageFactory(this);
		serverExecutor = Executors.newCachedThreadPool();
		rand = new Random();

		try {
			database = new Database();
		} catch (Exception ex) {
			System.err.println("Error setting up databse.");
			System.exit(1);
		}

		// register as task event listener with network
		network.addTaskReceivedEventListener(this);

	}

	// args[0] is base installation directory
	public static void main(String[] args) {
		CNPServer server = new CNPServer(args[0]);
		server.startNetwork();
	}

	// start listening for client connection
	public void startNetwork() {
		network.startListening();
	}

	public void connectToSession(JoinSessionTask task) throws SQLException {

		// CNPSession session = null;
		// Account userAccount = database.retrieveAccount(task.getUserID(),
		// task.getPassword());
		//
		// if (!openSessions.containsKey(task.getSessionName())) {
		// // retrieve session from database and add to openSessions
		//
		// } else {
		// // retrieve from openSessions
		// session = openSessions.get(task.getSessionName());
		// }
		//
		// // associate account with session
		// session.addUser(userAccount, task.getConnection());
	}

	public CNPSession createCNPSession(CreateSessionTask task)
			throws FailedSessionException, SQLException {

		// return database.createSession(task.getSessionLeader(),
		// CNPSession.generateString(), this);
		return null;

	}

	public CNPSession createCNPSession(CreatePrivateSessionTask task)
			throws FailedSessionException, SQLException {

		// return database.createSession(task.getSessionLeader(),
		// CNPSession.generateString(), this, task.getSessionPassword());
		return null;
	}

	public File compile(List<String> fileNames, CNPSession session) {
		// TODO implement
		return new File(""); // return tar or something
	}

	public void executeTask(CreateAccountTask task) {

		CreateAccountTaskResponse response = null;
		Account newAccount = null;

		try {
			newAccount = database.createAccount(task.getUsername(), task.getEmail(),
					task.getPassword());
			// create positive response
			response =  new CreateAccountTaskResponse(newAccount.getUserID(), true);
		} catch (FailedAccountException e) {
			// negative response
			response = new CreateAccountTaskResponse(-1, false);
		}

		// send back response
		SendResponseTask accountResponseTask = new SendResponseTask(response, task.getConnection());
		serverExecutor.submit(accountResponseTask);

	}

	public void executeTask(LoginTask task) {

		LoginTaskResponse response = null;
		Account loggedInAccount = null;

		try {
			loggedInAccount = database.retrieveAccount(task.getUsername(), task.getPassword());
			String userAuthToken = generateToken();
			userAuthTokens.put(loggedInAccount.getUserID(), userAuthToken);
			// create positive response
			response =  new LoginTaskResponse(loggedInAccount.getUserID(), loggedInAccount.getUsername(),
					true, userAuthToken);
		} catch (FailedAccountException e) {
			// negative response
			response = new LoginTaskResponse(-1, "", false, "");
		}

		// send back response
		SendResponseTask accountResponseTask = new SendResponseTask(response, task.getConnection());
		serverExecutor.submit(accountResponseTask);

	}

	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {

		Task task = evt.getTask();

		if (task instanceof TaskRequest) {
			TaskRequest request = (TaskRequest) task;
			request.setServer(this); // for execution


			if (request instanceof EditorTask) {
				EditorTask editorTask = (EditorTask) request;
				ServerSourceFile file = openSessions.get(editorTask.getSessionID()).getFile(editorTask.getFileID());
				editorTask.setFile(file); // for execution
				file.submitTask(editorTask); // submit to ExecutorService

			} else if (task instanceof CreateAccountTask) {
				CreateAccountTask accountTask = (CreateAccountTask) request;
				accountTask.setConnection(evt.getConnection());
				serverExecutor.submit(accountTask);

			} else if (task instanceof LoginTask) {
				LoginTask loginTask = (LoginTask) request;
				loginTask.setConnection(evt.getConnection());
				serverExecutor.submit(loginTask);


			} else if (task instanceof ChatTask) {
				ChatTask chatTask = (ChatTask) request;
				CNPSession session = openSessions.get(chatTask.getSessionID());
				chatTask.setSession(session);
				session.submitTask(chatTask);

			} else if (task instanceof JoinSessionTask) {

				JoinSessionTask connectTask = (JoinSessionTask) task;
				try {
					connectToSession(connectTask);
				} catch (SQLException e) {
					// System.err.println("Failed to connect " +
					// connectTask.getUsername() +
					// S " to " + connectTask.getSessionName());
				}

			} else if (task instanceof CreateSessionTask) {

				CreateSessionTask createTask = (CreateSessionTask) task;
				try {
					createCNPSession(createTask);
				} catch (SQLException e) {
					System.err.println("Failed to create session.");
				} catch (FailedSessionException e) {
					System.err.println("Failed to create session.");
				}

			} else { // send to sessionTaskQueue if should

				// TODO implement
			}

		}


	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public CNPSession getSession(int sessionID) {
		return openSessions.get(sessionID);
	}

	public boolean userIsAuth(int userID, String authToken) {
		return userAuthTokens.get(userID).equals(authToken);
	}

	public static boolean sessionExists(String sessionName) {
		return Database.sessionExists(sessionName);
	}

	public String generateToken() {

		char[] text = new char[USER_TOKEN_LENGTH];
		for (int i = 0; i < USER_TOKEN_LENGTH; i++) {
			text[i] = TOKEN_CHARS.charAt(rand.nextInt(TOKEN_CHARS.length()));
		}
		
		return new String(text);
	}
}
