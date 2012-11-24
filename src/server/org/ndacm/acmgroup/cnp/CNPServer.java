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
import org.ndacm.acmgroup.cnp.task.CreateAccountTask;
import org.ndacm.acmgroup.cnp.task.CreateFileTask;
import org.ndacm.acmgroup.cnp.task.CreatePrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateSessionTask;
import org.ndacm.acmgroup.cnp.task.FileTask;
import org.ndacm.acmgroup.cnp.task.JoinPrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.LoginTask;
import org.ndacm.acmgroup.cnp.task.OpenFileTask;
import org.ndacm.acmgroup.cnp.task.SendResponseTask;
import org.ndacm.acmgroup.cnp.task.ServerTask;
import org.ndacm.acmgroup.cnp.task.SessionTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory;
import org.ndacm.acmgroup.cnp.task.response.CreateAccountTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.JoinSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;

public class CNPServer implements TaskReceivedEventListener {

	private static final int USER_TOKEN_LENGTH = 10;
	private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private ServerNetwork network;
	private Database database;
	private Compiler compiler;
	private String baseDirectory;
	private Map<Integer, CNPSession> openSessions; // maps sessionID to CNPSession
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

	public List<String> retrieveSessionFileList(int sessionID) {
		// TODO implement
		return null;
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

	/**
	 * Execute a task request from the client to create a new session.
	 * @param task The task for creating a new session.
	 */
	public void executeTask(CreateSessionTask task) {

		CNPSession newSession = null;
		CreateSessionTaskResponse response = null;

		if (userIsAuth(task.getSessionLeader(), task.getUserAuthToken())) {
			// try to create a new public or private session, depending on the type of the task
			try {

				if (task instanceof CreatePrivateSessionTask) {
					newSession = database.createSession(task.getSessionLeader(), this, ((CreatePrivateSessionTask) task).getSessionPassword());
				} else {
					newSession = database.createSession(task.getSessionLeader(), this);
				}

				response = new CreateSessionTaskResponse(newSession.getSessionID(), true);

			} catch (FailedSessionException ex){
				// if creating the session fails, create a response signifying this
				response = new CreateSessionTaskResponse(-1, false);
			}
		} else {
			// user authentication failed
			response = new CreateSessionTaskResponse(-1, false);
		}

		// send back response
		SendResponseTask sessionResponseTask = new SendResponseTask(response, task.getConnection());
		serverExecutor.submit(sessionResponseTask);
	}

	public void executeTask(JoinSessionTask task) {

		CNPSession joinedSession = null;
		JoinSessionTaskResponse response = null;

		if (userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			// try to join an existing public or private session, depending on the type of the task
			try {
				if (task instanceof JoinPrivateSessionTask) {
					joinedSession = database.retrieveSession(task.getSessionName(), this, ((JoinPrivateSessionTask) task).getSessionPassword());
				} else {
					joinedSession = database.retrieveSession(task.getSessionName(), this);
				}

				// add connection to session list
				joinedSession.addUser(task.getUserID(), task.getConnection());

				// construct response
				List<String> sessionFiles = retrieveSessionFileList(joinedSession.getSessionID());
				response = new JoinSessionTaskResponse(task.getUserID(), task.getUsername(), joinedSession.getSessionName(), joinedSession.getSessionID(), true, sessionFiles);

			} catch (FailedSessionException ex) {
				// if joining the session fails, create a response signifying this
				response = new JoinSessionTaskResponse(-1, "", "", -1, false, null);
			}
		} else {
			// tokens don't match, join session task fails
			response = new JoinSessionTaskResponse(-1, "", "", -1, false, null);
		}

		// send back response to client if fails, otherwise send it to all session members so their user list is updated
		if (response.isSuccess()) {
			joinedSession.distributeTask(response);

		} else {
			SendResponseTask sessionResponseTask = new SendResponseTask(response, task.getConnection());
			serverExecutor.submit(sessionResponseTask);
		}

	}

	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {

		Task task = evt.getTask();

		// based on specific  task type, will need to set different variable references (for execution)
		// and forward on to a specific ExecutorService (server, session, or file)
		if (task instanceof ServerTask) {

			ServerTask serverTask = (ServerTask) task;
			// set server and connection references
			serverTask.setServer(this);
			serverTask.setConnection(evt.getConnection());
			// submit to server task executor
			serverExecutor.submit(task);

		} else if (task instanceof SessionTask) {

			SessionTask sessionTask = (SessionTask) task;
			CNPSession session = openSessions.get(sessionTask.getSessionID());
			// set session reference
			sessionTask.setSession(session);

			if (sessionTask instanceof CreateFileTask) {
				((CreateFileTask) task).setConnection(evt.getConnection());
			} else if (task instanceof OpenFileTask) {
				((OpenFileTask) task).setConnection(evt.getConnection());
			}
			// submit to session task executor
			session.submitTask(sessionTask);

		} else if (task instanceof FileTask) {

			FileTask fileTask = (FileTask) task;
			ServerSourceFile file = openSessions.get(fileTask.getSessionID()).getFile(fileTask.getFileID());
			// set file reference
			fileTask.setFile(file);

			// submit to server source file task executor
			file.submitTask(fileTask);

		} else {
			System.err.println("Received task has an unknown type.");
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
