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
import org.ndacm.acmgroup.cnp.task.response.CommitTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateAccountTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.JoinSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LeaveSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;

/**
 * 
 * ###################KK###################
 * ##################KEDK##################
 * #################K#EEfK#################
 * ################KWWWDEEK################
 * ###############KWWWK###DK###############
 * ##############KWWWW#K#EKDK##############
 * #############KWWKWWWWWKEKEK#############
 * ############KKW#WDWW#WEKKKKK############
 * ###########KiLKW        WW#KK###########
 * ##########K;E#  ,#WWWK#,  #EGK##########
 * #########KWj  ###W##W##KE#  DGK#########
 * ########KW#  WKWW####WWDGLG  EEK########
 * #######KWW  W#KWKWWWWEWGGLLD  E#K#######
 * ######KK## WWK#KKKKEEDEDGGDDD ##EK######
 * #####KWKW #WKKWK#W#WKKEEDDDDE# EEEK#####
 * ####KKK#W #WKKKKWWWWKEEEDDDDDE WEKKK####
 * ###K#WWK ,KWWKKEKKKKKEEEEEEEEE, KE#DK###
 * ##KE##WE ##   WWWj  #; W :#  W# EWKLEK##
 * #KEWW##K ##,#  #  Dt ;       EW #WEKW#K#
 * KGWWED#W ##K   #  ###; ## ## j# #tKWWW#K
 * DEWWGEK# #  ## #  ###; ## ## j# EEWKKKKD
 * #DGWWKEW W  #. #  ## ; WW WW tW DEW#KKD#
 * ##EKWKKK #W  W WW   K; WW KK tW EWWW#E##
 * ###EGWWE ,KKWWKKKWWKWKKKKKEEEE, KDWWE###
 * ####DKEEW KKKKK##KWW#WWWKKKEEE WEDED####
 * #####DDGK #EKEDK#E##WKWWEEGDD# GEWD#####
 * ######EDW# DGGDDDDDEEDDGGGLL# ##WE######
 * #######EEW  DDDEDDKWWW###WWE  W#E#######
 * ########DKW  EDEEG##KWWWEEL  fKD########
 * #########DK#  #fEKDDGLfDE#  LGD#########
 * ##########DE##  ,WDLDLE,  #WGD##########
 * ###########DKWKW        WDfDD###########
 * ############EKWKKEK##KfG#DGE############
 * #############EEWKEEEKKGEKGE#############
 * ##############DDWEEEEEDK#D##############
 * ###############EEWKKEG##D###############
 * ################KKWKDGLK################
 * #################KEWEGK#################
 * ##################KEKK##################
 * ###################KK###################
 * 
 * 
 * The CoNetPad server. This is the main class that handles the server.
 * 
 */
public class CNPServer implements TaskReceivedEventListener, ServerTaskExecutor {

	// the length of a user token
	private static final int USER_TOKEN_LENGTH = 10;
	// the available characters that may be used in a token
	private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	// network class for handling the socket connection
	private ServerNetwork network;
	// database object for SQL query handling
	private Database database;
	// manager for Git functionality
	private JGit jGit;
	// compiler for source files
	private Compiler compiler;
	// base installation directory for CNP files
	private String baseDirectory;
	// maps sessionID to CNPSession
	private Map<Integer, CNPSession> openSessions;
	// task executor for server-wide tasks
	private ExecutorService serverExecutor;
	// maps userID to user authentication token
	private Map<Integer, String> userAuthTokens;
	private Random rand;
	private SecretKey key; // TODO implement
	private Cipher cipher; // TODO implement

	/**
	 * Default constructor.
	 * 
	 * @param baseDirectory The base directory for storing CNP files.
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
			System.err.println("Error setting up database.");
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (SQLException e) {
			System.err.println("Error setting up database.");
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (NotDirectoryException e) {
			System.err.println("Error setting up Git.");
			System.err.println(e.getMessage());
			System.exit(1);
		}

		// register as task event listener with the network
		network.addTaskReceivedEventListener(this);

	}
	
	/**
	 * Entry point for the CNP server.
	 * 
	 * @param args args[0] is the base installation directory.
	 */
	public static void main(String[] args) {
		CNPServer server;
		if (args.length > 0) {
			server = new CNPServer(args[0]);
		} else {
			// base installation directory is the current directory
			server = new CNPServer(".");
		}
		server.startNetwork();
	}

	/**
	 * Starts the network object to listen on sockets for connections.
	 */
	public void startNetwork() {
		network.startListening();
	}
	
	/**
	 * Compiles the files in the given list of filenames for 
	 * a given session. The result will be an archive file
	 * containing the compiled files.
	 * 
	 * @param fileNames filenames for the files to compile
	 * @param session the session that the files belong to
	 * @return an archive of the compilation files
	 */
	public File compile(List<String> fileNames, CNPSession session) {
		// TODO implement
		return new File("");
	}

	/**
	 * Retrieve the list of files for a given session ID.
	 * 
	 * @param sessionID the session ID of the session
	 * @return a list of filenames
	 */
	public List<String> retrieveSessionFileList(int sessionID) {
		// TODO implement
		return null;
	}
	
	/**
	 * Retrieve the list of files for a given session name.
	 * 
	 * @param sessionName the name of the session
	 * @return a list of filenames
	 */
	public List<String> retrieveSessionFileList(String sessionName) {
		File repo = jGit.retrieveRepo(sessionName);
		
		// if the repository has an existing directory, return the associated
		// list of filenames
		if (repo.isDirectory()) {
			ArrayList<String> list = new ArrayList<String>();
			File[] files = repo.listFiles();
			for (int i = 0; i < files.length; i++) {
				// ignore the .git files/directories
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
	 * Execute task for creating an account.
	 * 
	 * @param task the task to execute
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
	 * Execute task for creating an account.
	 * 
	 * @param task the task to execute
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
	 * Execute task for creating an account.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(CreateSessionTask task) {

		CNPSession newSession = null;
		CreateSessionTaskResponse response = null;

		// authenticate user using token
		if (userIsAuth(task.getSessionLeader(), task.getUserAuthToken())) {
			// create a new public or private session, depending on the task type
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
				// initialize the session Git repo
				jGit.createRepo(newSession.getSessionName());
				newSession.setGitRepo(jGit.activateRepo(newSession
						.getSessionName()));
				// create a dummy file
				newSession.createFile("HelloWorld.txt", SourceType.GENERAL);
				openSessions.put(newSession.getSessionID(), newSession);
				response = new CreateSessionTaskResponse(
						newSession.getSessionID(), newSession.getSessionName(),
						true);

			} catch (FailedSessionException ex) {
				response = new CreateSessionTaskResponse(-1, "n/a", false);
			} catch (FileNotFoundException e) {
				response = new CreateSessionTaskResponse(-1, "n/a", false);
			}
		} else {
			// user authentication failed
			response = new CreateSessionTaskResponse(-1, "n/a", false);
		}

		// send response to client
		SendResponseTask sessionResponseTask = new SendResponseTask(response,
				task.getConnection());
		serverExecutor.submit(sessionResponseTask);
	}

	/**
	 * Execute task for joining a session.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(JoinSessionTask task) {

		CNPSession joinedSession = null;
		JoinSessionTaskResponse response = null;

		// authenticate user using toke 
		if (userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			// join an existing public or private session, depending on
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

					// add session to list of open sessions
					openSessions.put(joinedSession.getSessionID(),
							joinedSession);
					// activate the Git repository for the session
					joinedSession.setGitRepo(jGit.activateRepo(joinedSession
							.getSessionName()));
				}

				// add connection and auth token to list
				joinedSession.addUser(task.getUserID(), task.getUsername(),
						task.getConnection(), task.getUserAuthToken());

				// populate session files into the session
				List<String> sessionFiles = new ArrayList<String>();
				List<Integer> sessionFileID = new ArrayList<Integer>();
				for (SourceFile file : joinedSession.getSourceFilesList()) {
					sessionFiles.add(file.getFilename());
					sessionFileID.add(file.getFileID());
				}

				// construct the response
				response = new JoinSessionTaskResponse(task.getUserID(),
						task.getUsername(), joinedSession.getSessionName(),
						joinedSession.getSessionID(), true, sessionFiles,
						sessionFileID, joinedSession.getClientIdToName()
						.values());

			} catch (FailedSessionException ex) {
				response = new JoinSessionTaskResponse(-1, "n/a", "n/a", -1,
						false, null, null, null);
			} catch (FileNotFoundException e) {
				response = new JoinSessionTaskResponse(-1, "n/a", "n/a", -1,
						false, null, null, null);
			}
		} else {
			// tokens don't match; join session task fails
			response = new JoinSessionTaskResponse(-1, "n/a", "n/a", -1, false,
					null, null, null);
		}

		// send back response to client if fails; otherwise, send it to all
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
	 * Execute task for leaving a session.
	 * 
	 * @param task the task to execute
	 */
	@Override
	public void executeTask(LeaveSessionTask task) {

		CNPSession sessionToLeave = openSessions.get(task.getSessionID());
		LeaveSessionTaskResponse response = null;

		// authenticate user using token
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

		// send back response to client if fails; otherwise, send it to all
		// session members so their user list is updated
		if (response.isSuccess()) {
			sessionToLeave.distributeTask(response);

		} else {
			SendResponseTask sessionResponseTask = new SendResponseTask(
					response, task.getConnection());
			serverExecutor.submit(sessionResponseTask);
		}
	}

	/**
	 * Execute task for deleting a session.
	 * 
	 * @param task the task to execute
	 */
	@Override
	public void executeTask(DeleteSessionTask task) {
		// authenticate user using token
		if (userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			// if others are connected, kick them off first.
			// then delete session and any associated data
			// TODO implement
		}
	}

	/**
	 * Execute task for committing to the Git repository.
	 * 
	 * @param task the task to execute
	 */
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

	/**
	 * Forward a task on to a specific ExecutorService when a TaskReceivedEvent
	 * is fired.
	 */
	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {

		Task task = evt.getTask();

		// based on specific task type, will need to set different variable
		// references (for execution)
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
	 * Returns the string that represents the base directory of file.
	 * 
	 * @return the directory where the files are being stored and worked on
	 */
	public String getBaseDirectory() {
		return baseDirectory;
	}

	/**
	 * Gets a session object for a given session ID
	 * 
	 * @param sessionID the database ID of the session
	 * @return the session object
	 */
	public CNPSession getSession(int sessionID) {
		return openSessions.get(sessionID);
	}

	/**
	 * Checks if a given user has previously authenticated.
	 * 
	 * @param userID the database ID of the user
	 * @param authToken the authentication token of the user
	 * @return true, if the user is authenticated; false otherwise
	 */
	public boolean userIsAuth(int userID, String authToken) {
		return userAuthTokens.get(userID).equals(authToken);
	}

	/**
	 * Checks if a session exists or not.
	 * 
	 * @param sessionName the unique name of the session
	 * @return true, if the session exists; false otherwise
	 * @throws FailedSessionException
	 */
	public boolean sessionExists(String sessionName)
			throws FailedSessionException {
		return database.sessionExists(sessionName);
	}

	/**
	 * Generates unique session names.
	 * Source: http://stackoverflow.com/questions/2863852
	 *         /how-to-generate-a-random-string-in-java
	 * 
	 * @return A unique string name. 
	 * @throws FailedSessionException
	 */
	public String generateString() throws FailedSessionException {

		boolean isUnique = false;

		char[] text = null;
		String sessionName = null;
		// while generated name is not unique, continue generating
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
	 * Generates a random token for user authentication.
	 * 
	 * @return the generated user authentication token
	 */
	public String generateToken() {

		char[] text = new char[USER_TOKEN_LENGTH];
		for (int i = 0; i < USER_TOKEN_LENGTH; i++) {
			text[i] = TOKEN_CHARS.charAt(rand.nextInt(TOKEN_CHARS.length()));
		}

		return new String(text);
	}

	/**
	 * Get the Git repo manager for the server.
	 * 
	 * @return the Git instance that handles all repos.
	 */
	public JGit getjGit() {
		return jGit;
	}

}
