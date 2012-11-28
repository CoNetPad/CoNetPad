package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.git.JGit;
import org.ndacm.acmgroup.cnp.git.NotDirectoryException;
import org.ndacm.acmgroup.cnp.network.ServerNetwork;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.task.CloseFileTask;
import org.ndacm.acmgroup.cnp.task.CommitTask;
import org.ndacm.acmgroup.cnp.task.CreateAccountTask;
import org.ndacm.acmgroup.cnp.task.CreateFileTask;
import org.ndacm.acmgroup.cnp.task.CreatePrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateSessionTask;
import org.ndacm.acmgroup.cnp.task.DeleteSessionTask;
import org.ndacm.acmgroup.cnp.task.FileTask;
import org.ndacm.acmgroup.cnp.task.JoinPrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.LeaveSessionTask;
import org.ndacm.acmgroup.cnp.task.LoginTask;
import org.ndacm.acmgroup.cnp.task.OpenFileTask;
import org.ndacm.acmgroup.cnp.task.SendResponseTask;
import org.ndacm.acmgroup.cnp.task.ServerTask;
import org.ndacm.acmgroup.cnp.task.ServerTaskExecutor;
import org.ndacm.acmgroup.cnp.task.SessionTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory;
import org.ndacm.acmgroup.cnp.task.response.CommitTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateAccountTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.JoinSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LeaveSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;

/**
 * Server Class This is the main class that handles the server
 * 
 * @author Cesar Ramirez
 * @version 1.5
 */
public class CNPServer implements TaskReceivedEventListener, ServerTaskExecutor {

	private static final int USER_TOKEN_LENGTH = 10; // The length of a user
														// token

	// The available characters used in a token
	private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private ServerNetwork network; // The network class for handing soccket
									// connection
	private Database database; // The database object for SQL query handling
	private JGit jGit;
	private Compiler compiler; // The compiler class for compiling files
	private String baseDirectory; // The base directory of the file handling
	private Map<Integer, CNPSession> openSessions; // maps sessionID to //
	// CNPSession
	private TaskMessageFactory messageFactory; // This generates strings for
												// network messages
	private ExecutorService serverExecutor; // for server-wide tasks (e.g.
											// account creation)
	private Map<Integer, String> userAuthTokens; // userID to userAuthToken

	private SecretKey key; // TODO implement
	private Cipher cipher; // TODO implement
	private Random rand;

	/**
	 * This is teh default constructor the CNP Server
	 * 
	 * @param baseDirectory
	 *            The base directory for the files to be stored and work on
	 */
	public CNPServer(String baseDirectory) {

		this.baseDirectory = baseDirectory;
		network = new ServerNetwork();
		compiler = new Compiler();
		openSessions = new ConcurrentHashMap<Integer, CNPSession>();
		userAuthTokens = new ConcurrentHashMap<Integer, String>();
		serverExecutor = Executors.newCachedThreadPool();
		rand = new Random();

		try {
			database = new Database(this);
			jGit = new JGit(new File(baseDirectory));
		} catch (ClassNotFoundException e) {
			System.err.println("Error setting up databse.");
			System.err.println(e.getMessage());
			System.exit(1);

		} catch (SQLException e) {
			System.err.println("Error setting up databse.");
			System.err.println(e.getMessage());
			System.exit(1);

		} catch (NotDirectoryException e) {
			System.err.println("Error setting up git.");
			System.err.println(e.getMessage());
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
			server = new CNPServer(".");
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
	 * This compiles a list of files [Not Implemented]
	 * 
	 * @param fileNames
	 *            The List of files that need to be compiled
	 * @param session
	 *            The Session in which the files belon gto
	 * @return the executable file of the compiles
	 */
	public File compile(List<String> fileNames, CNPSession session) {
		// TODO implement
		return new File(""); // return tar or something
	}

	public List<String> retrieveSessionFileList(int sessionID) {
		// TODO implement
		return null;
	}

	public List<String> retrieveSessionFileList(String sessionName) {
		File repo = jGit.retrieveRepo(sessionName);
		if (repo.isDirectory()) {
			ArrayList<String> list = new ArrayList<String>();
			File[] files = repo.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().compareTo(".git") == 0) {
					continue;
				}
				list.add(files[i].getName());
			}
			return list;
		} else {
			return null;
		}
	}

	/**
	 * This executes a createAccount Task, creating a new account
	 * 
	 * @param task
	 *            The CreateAccountTask in which you want to use to create a new
	 *            account
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
	 * 
	 * @param task
	 *            The LoginTask that you want to use to login a user.
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
			response = new LoginTaskResponse(-1, "n/a", false, "n/a");
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

		if (userIsAuth(task.getSessionLeader(), task.getUserAuthToken())) {
			// try to create a new public or private session, depending on the
			// type of the task
			try {

				if (task instanceof CreatePrivateSessionTask) {
					newSession = database.createSession(
							task.getSessionLeader(), this,
							((CreatePrivateSessionTask) task)
									.getSessionPassword());
				} else {
					newSession = database.createSession(
							task.getSessionLeader(), this);
				}

				jGit.createRepo(newSession.getSessionName());
				newSession.setGitRepo(jGit.activateRepo(newSession
						.getSessionName()));
				newSession.createFile("HelloWorld.txt", SourceType.GENERAL);
				openSessions.put(newSession.getSessionID(), newSession);
				response = new CreateSessionTaskResponse(
						newSession.getSessionID(), newSession.getSessionName(),
						true);

			} catch (FailedSessionException ex) {
				// if creating the session fails, create a response signifying
				// this
				response = new CreateSessionTaskResponse(-1, "n/a", false);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				response = new CreateSessionTaskResponse(-1, "n/a", false);
			}
		} else {
			// user authentication failed
			response = new CreateSessionTaskResponse(-1, "n/a", false);
		}

		// send back response
		SendResponseTask sessionResponseTask = new SendResponseTask(response,
				task.getConnection());
		serverExecutor.submit(sessionResponseTask);
	}

	public void executeTask(JoinSessionTask task) {

		CNPSession joinedSession = null;
		JoinSessionTaskResponse response = null;

		if (userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			// try to join an existing public or private session, depending on
			// the type of the task
			try {

				// get sessionID from sessionName - will throw exception if
				// doesn't exist
				int sessionID = database.getSessionID(task.getSessionName());

				// check if already open - if so, load that session
				if (openSessions.containsKey(sessionID)) {
					joinedSession = openSessions.get(sessionID);

				} else {
					// otherwise load a new session object from the database
					// information

					if (task instanceof JoinPrivateSessionTask) {
						joinedSession = database.retrieveSession(task
								.getSessionName(), this,
								((JoinPrivateSessionTask) task)
										.getSessionPassword());
					} else {
						joinedSession = database.retrieveSession(
								task.getSessionName(), this);
					}

					openSessions.put(joinedSession.getSessionID(),
							joinedSession);

				}

				// add connection to session list
				joinedSession.addUser(task.getUserID(), task.getUsername(),
						task.getConnection(), task.getUserAuthToken());

				// construct response
				List<String> sessionFiles = new ArrayList<String>();
				List<Integer> sessionFileID = new ArrayList<Integer>();
				for (SourceFile file : joinedSession.getSourceFilesList()) {
					sessionFiles.add(file.getFilename());
					sessionFileID.add(file.getFileID());
				}

				response = new JoinSessionTaskResponse(task.getUserID(),
						task.getUsername(), joinedSession.getSessionName(),
						joinedSession.getSessionID(), true, sessionFiles,
						sessionFileID, joinedSession.getClientIdToName()
								.values());

			} catch (FailedSessionException ex) {
				// if joining the session fails, create a response signifying
				// this
				response = new JoinSessionTaskResponse(-1, "n/a", "n/a", -1,
						false, null, null, null);
			} 
		} else {
			// tokens don't match, join session task fails
			response = new JoinSessionTaskResponse(-1, "n/a", "n/a", -1, false,
					null, null, null);
		}

		// send back response to client if fails, otherwise send it to all
		// session members so their user list is updated
		if (response.isSuccess()) {
			joinedSession.distributeTask(response);

		} else {
			SendResponseTask sessionResponseTask = new SendResponseTask(
					response, task.getConnection());
			serverExecutor.submit(sessionResponseTask);
		}

	}

	/**
	 * Execute the task for leaving a session.
	 */
	@Override
	public void executeTask(LeaveSessionTask task) {

		CNPSession sessionToLeave = openSessions.get(task.getSessionID());
		LeaveSessionTaskResponse response = null;

		if (userIsAuth(task.getUserID(), task.getUserAuthToken())) {

			int userID = task.getUserID();

			// unregister as listener from any files currently listening to
			for (ServerSourceFile file : sessionToLeave.getSourceFiles()
					.values()) {
				if (file.userIsListening(userID)) {
					file.removeFileTaskEventListener(userID);
				}
			}

			// remove connection from session list
			response = new LeaveSessionTaskResponse(userID, sessionToLeave
					.getClientIdToName().get(userID), true);
			sessionToLeave.removeUser(userID);

		} else {
			// tokens don't match, join session task fails
			response = new LeaveSessionTaskResponse(-1, "n/a", false);
		}

		// send back response to client if fails, otherwise send it to all
		// session members so their user list is updated
		if (response.isSuccess()) {
			sessionToLeave.distributeTask(response);

		} else {
			SendResponseTask sessionResponseTask = new SendResponseTask(
					response, task.getConnection());
			serverExecutor.submit(sessionResponseTask);
		}

	}

	@Override
	public void executeTask(DeleteSessionTask task) {
		if (userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			// if others are connected, kick them off first

			// then delete session and any associated data
		}

	}

	@Override
	public void executeTask(CommitTask task) {
		CommitTaskResponse response = null;

		// make sure user requesting task has authenticated
		if (userIsAuth(task.getUserID(), task.getUserAuthToken())) {

			try {

				// write all session ropes to files
				CNPSession session = openSessions.get(task.getSessionID());
				for (SourceFile file : session.getSourceFiles().values()) {
					file.save();
				}

				// commit the task
				jGit.commitToRepo(task.getSessionID(), task.getMessage());

				// return a response
				response = new CommitTaskResponse(true);

			} catch (GitAPIException e) {
				response = new CommitTaskResponse(false);
			}

			SendResponseTask commitResponseTask = new SendResponseTask(
					response, task.getConnection());
			serverExecutor.submit(commitResponseTask);
		}
	}

	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {

		Task task = evt.getTask();

		// based on specific task type, will need to set different variable
		// references (for execution) and forward on to a specific
		// ExecutorService
		// (server, session, or file)
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
			if (session == null) {
				return;
			}
			if (sessionTask instanceof CreateFileTask) {
				((CreateFileTask) task).setConnection(evt.getConnection());
			} else if (task instanceof OpenFileTask) {
				((OpenFileTask) task).setConnection(evt.getConnection());
			} else if (task instanceof CloseFileTask) {
				((CloseFileTask) task).setConnection(evt.getConnection());
			} else if (task instanceof CommitTask) {
				((CommitTask) task).setConnection(evt.getConnection());
			}
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
	 * 
	 * @return The directory where the files are being stored and worked on
	 */
	public String getBaseDirectory() {
		return baseDirectory;
	}

	/**
	 * This gets a session object for a given session id
	 * 
	 * @param sessionID
	 *            The database ID of the session
	 * @return The session object
	 */
	public CNPSession getSession(int sessionID) {
		return openSessions.get(sessionID);
	}

	/**
	 * This checks if a given user is authenticated
	 * 
	 * @param userID
	 *            The database ID of the user
	 * @param authToken
	 *            The authentication token of the user
	 * @return True if the user is authenticated, false otherwise
	 */
	public boolean userIsAuth(int userID, String authToken) {
		return userAuthTokens.get(userID).equals(authToken);
	}

	/**
	 * This checks if a session exists or not
	 * 
	 * @param sessionName
	 *            The unique name of the session
	 * @return True if the session exists, false otherwise
	 * @throws FailedSessionException
	 */
	public boolean sessionExists(String sessionName)
			throws FailedSessionException {
		return database.sessionExists(sessionName);
	}

	/**
	 * This is a string generator for unique session names
	 * 
	 * @return A unique string name. Source:
	 *         http://stackoverflow.com/questions/2863852
	 *         /how-to-generate-a-random-string-in-java
	 * @throws FailedSessionException
	 */
	public String generateString() throws FailedSessionException {

		boolean isUnique = false;

		char[] text = null;
		String sessionName = null;
		while (!isUnique) {
			text = new char[CNPSession.NAME_LENGTH];
			for (int i = 0; i < CNPSession.NAME_LENGTH; i++) {
				text[i] = CNPSession.SESSION_NAME_CHARS.charAt(rand
						.nextInt(CNPSession.SESSION_NAME_CHARS.length()));
			}
			sessionName = new String(text);

			if (!sessionExists(sessionName)) {
				isUnique = true;
			}
		}
		return sessionName;
	}

	/**
	 * This generates a random token for user authentication
	 * 
	 * @return The string token
	 */
	public String generateToken() {

		char[] text = new char[USER_TOKEN_LENGTH];
		for (int i = 0; i < USER_TOKEN_LENGTH; i++) {
			text[i] = TOKEN_CHARS.charAt(rand.nextInt(TOKEN_CHARS.length()));
		}

		return new String(text);
	}

	public JGit getjGit() {
		return jGit;
	}

}
