/**
 * Server Class
 * This is the main class that handles the server
 * @author Cesar Ramirez
 * @version 1.5
 */
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
import org.ndacm.acmgroup.cnp.task.FileTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.LoginTask;
import org.ndacm.acmgroup.cnp.task.SendResponseTask;
import org.ndacm.acmgroup.cnp.task.ServerTask;
import org.ndacm.acmgroup.cnp.task.SessionTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory;
import org.ndacm.acmgroup.cnp.task.response.CreateAccountTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;

public class CNPServer implements TaskReceivedEventListener {

	private static final int USER_TOKEN_LENGTH = 10;			//The length of a user token		
	private static final String 
		TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";		//The available characters used in a token

	private ServerNetwork network;				//The network class for handing soccket connection
	private Database database;					//The database object for SQL query handling
	private Compiler compiler;					//The compiler class for compiling files
	private String baseDirectory;				//The base directory of the file handling
	private Map<Integer, CNPSession> openSessions; // maps sessionID to //
	// CNPSession

	private TaskMessageFactory messageFactory;	//This generates JSON format strings for network messages
	private ExecutorService serverExecutor; // for server-wide tasks (e.g. account creation)
	private Map<Integer, String> userAuthTokens; // userID to userAuthToken

	private SecretKey key; // TODO implement
	private Cipher cipher; // TODO implement
	private Random rand;

	/**
	 * This is teh default constructor the CNP Server
	 * @param baseDirectory			The base directory for the files to be stored and work on
	 */
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
		CNPServer server;
		if (args.length > 0) {
			server = new CNPServer(args[0]);
		} else {
			server = new CNPServer("");
		}
		server.startNetwork();
	}

	/**
	 * This starts network object to listen on sockets for connections
	 */
	public void startNetwork() {
		network.startListening();
	}
	/**
	 * This adds a specific user to a specific Session
	 * @param task				The JoinSessionTask that joins a user to a task
	 * @throws SQLException		
	 */
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
	/**
	 * This creates a new public session	[Not Implemented]
	 * @param task						The CreateSessionTask that will be used to create the session
	 * @return							The Session Object of the newly created session
	 * @throws FailedSessionException
	 * @throws SQLException
	 */
	public CNPSession createCNPSession(CreateSessionTask task)
			throws FailedSessionException, SQLException {

		// return database.createSession(task.getSessionLeader(),
		// CNPSession.generateString(), this);
		return null;

	}
	/**
	 * This creates a new private session 	[Not Implemented]
	 * @param task						The CreatePrivateSessionTask for creating the new private session
	 * @return							The Private session task of the newly created session
	 * @throws FailedSessionException
	 * @throws SQLException
	 */
	public CNPSession createCNPSession(CreatePrivateSessionTask task)
			throws FailedSessionException, SQLException {

		// return database.createSession(task.getSessionLeader(),
		// CNPSession.generateString(), this, task.getSessionPassword());
		return null;
	}

	
	/**
	 * This compiles a list of files	 [Not Implemented]
	 * @param fileNames			The List of files that need to be compiled
	 * @param session			The Session in which the files belon gto
	 * @return					the executable file of the compiles
	 */
	public File compile(List<String> fileNames, CNPSession session) {
		// TODO implement
		return new File(""); // return tar or something
	}
	
	
	/**
	 * This executes a createAccount Task, creating a new account
	 * @param task				The CreateAccountTask in which you want to use to create a new account
	 */
	public void executeTask(CreateAccountTask task) {

		CreateAccountTaskResponse response = null;
		Account newAccount = null;

		try {
			newAccount = database.createAccount(task.getUsername(),
					task.getEmail(), task.getPassword());
			// create positive response
			response = new CreateAccountTaskResponse(newAccount.getUserID(),
					true);
		} catch (FailedAccountException e) {
			// negative response
			response = new CreateAccountTaskResponse(-1, false);
		}

		// send back response
		SendResponseTask accountResponseTask = new SendResponseTask(response,
				task.getConnection());
		serverExecutor.submit(accountResponseTask);

	}
	/**
	 * This executes a LoginTask that will authenticate a user
	 * @param task				The LoginTask that you want to use to login a user.
	 */
	public void executeTask(LoginTask task) {

		LoginTaskResponse response = null;
		Account loggedInAccount = null;

		try {
			loggedInAccount = database.retrieveAccount(task.getUsername(),
					task.getPassword());
			String userAuthToken = generateToken();
			userAuthTokens.put(loggedInAccount.getUserID(), userAuthToken);
			// create positive response
			response = new LoginTaskResponse(loggedInAccount.getUserID(),
					loggedInAccount.getUsername(), true, userAuthToken);
		} catch (FailedAccountException e) {
			// negative response
			response = new LoginTaskResponse(-1, "", false, "");
		}

		// send back response
		SendResponseTask accountResponseTask = new SendResponseTask(response,
				task.getConnection());
		serverExecutor.submit(accountResponseTask);

	}

	/**
	 * Execute a task request from the client to create a new session.
	 * 
	 * @param task
	 *            The task for creating a new session.
	 */
	public void executeTask(CreateSessionTask task) {

		CNPSession newSession = null;
		CreateSessionTaskResponse response = null;

		// try to create a new public or private session, depending on the type
		// of the task
		try {

			if (task instanceof CreatePrivateSessionTask) {
				newSession = database.createSession(task.getSessionLeader(),
						this,
						((CreatePrivateSessionTask) task).getSessionPassword());
			} else {
				newSession = database.createSession(task.getSessionLeader(),
						this);
			}

			response = new CreateSessionTaskResponse(newSession.getSessionID(),
					true);

		} catch (FailedSessionException ex) {
			// if creating the session fails, create a response signifying this
			response = new CreateSessionTaskResponse(-1, false);
		}

		// send back response
		SendResponseTask sessionResponseTask = new SendResponseTask(response,
				task.getConnection());
		serverExecutor.submit(sessionResponseTask);
	}

	
	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {

		Task task = evt.getTask();

		// based on specific task type, will need to set different variable
		// references (for execution)
		// and forward on to a specific ExecutorService (server, session, or
		// file)
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
			// submit to session task executor
			session.submitTask(sessionTask);

		} else if (task instanceof FileTask) {

			FileTask fileTask = (FileTask) task;
			ServerSourceFile file = openSessions.get(fileTask.getSessionID())
					.getFile(fileTask.getFileID());
			// set file reference
			fileTask.setFile(file);
			// submit to server source file task executor
			file.submitTask(fileTask);

		} else {
			System.err.println("Received task has an unknown type.");
		}
	}
	/**
	 * This returns the string that represents the base directory of file
	 * @return			The directory where the files are being stored and worked on
	 */
	public String getBaseDirectory() {
		return baseDirectory;
	}
	/**
	 * This gets a session object for a given session id
	 * @param sessionID			The database ID of the session
	 * @return					The session object
	 */
	public CNPSession getSession(int sessionID) {
		return openSessions.get(sessionID);
	}
	/**
	 * This checks if a given user is authenticated
	 * @param userID			The database ID of the user
	 * @param authToken			The authentication token of the user
	 * @return					True if the user is authenticated, false otherwise
	 */
	public boolean userIsAuth(int userID, String authToken) {
		return userAuthTokens.get(userID).equals(authToken);
	}

	/**
	 * This checks if a session exists or not
	 * @param sessionName			The unique name of the session
	 * @return						True if the session exists, false otherwise
	 */
	public static boolean sessionExists(String sessionName) {
		return Database.sessionExists(sessionName);
	}
	/**
	 * This generates a random token for user authentication
	 * @return			The string token
	 */
	public String generateToken() {

		char[] text = new char[USER_TOKEN_LENGTH];
		for (int i = 0; i < USER_TOKEN_LENGTH; i++) {
			text[i] = TOKEN_CHARS.charAt(rand.nextInt(TOKEN_CHARS.length()));
		}

		return new String(text);
	}
}
