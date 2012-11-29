package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel;
import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.git.JGit;
import org.ndacm.acmgroup.cnp.git.JRepository;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.CloseFileTask;
import org.ndacm.acmgroup.cnp.task.CompileTask;
import org.ndacm.acmgroup.cnp.task.CreateFileTask;
import org.ndacm.acmgroup.cnp.task.DeleteFileTask;
import org.ndacm.acmgroup.cnp.task.DownloadRepoTask;
import org.ndacm.acmgroup.cnp.task.OpenFileTask;
import org.ndacm.acmgroup.cnp.task.SendResponseTask;
import org.ndacm.acmgroup.cnp.task.SessionTask;
import org.ndacm.acmgroup.cnp.task.SessionTaskExecutor;
import org.ndacm.acmgroup.cnp.task.response.ChatTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CloseFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.OpenFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

/**
 * A CoNetPad session. This is the class that handles the CNP session.
 * 
 */
public class CNPSession implements SessionTaskExecutor {

	/**
	 * The allowed characters for a session name.
	 */
	public static final String SESSION_NAME_CHARS = "abcdefghijklmnopqrstuvwxyz";
	/**
	 * A unique INTEGER to be used for the next file ID.
	 */
	private static volatile int NEXT_FILEID;
	/**
	 * Max length of session names.
	 */
	public static final int NAME_LENGTH = 5;
	/**
	 * The base directories of the files stored
	 */
	private static String baseDirectory;
	/**
	 * maps fileID to ServerSourceFile
	 */
	private Map<Integer, ServerSourceFile> sourceFiles;
	/**
	 * maps userID to CNPConnection
	 */
	private Map<Integer, CNPConnection> clientConnections; 

	private int sessionID;
	private CNPServer server;
	private String sessionName;
	private JRepository gitRepo;
	private int sessionLeader;
	private Map<Integer, String> clientIdToName;
	private Map<Account, Account.FilePermissionLevel> filePermissions;
	private Map<Account, Account.ChatPermissionLevel> chatPermissions;

	private ExecutorService sessionTaskCourier; // multi-threaded
	private ExecutorService sessionTaskQueue; // single-threaded

	/**
	 * Default constructor.
	 * @param sessionID 	sessionID for this session
	 * @param sessionName 	name for this session
	 * @param server 		server managing this session
	 * @param sessionLeader database ID of the user who is the session leader
	 */
	public CNPSession(int sessionID, String sessionName, CNPServer server,
			int sessionLeader) {

		this.server = server;

		baseDirectory = server.getBaseDirectory()
				+ System.getProperty("file.separator") + "sessions"
				+ System.getProperty("file.separator") + sessionName
				+ System.getProperty("file.separator");

		sourceFiles = new ConcurrentHashMap<Integer, ServerSourceFile>();
		sessionTaskCourier = Executors.newCachedThreadPool();
		sessionTaskQueue = Executors.newSingleThreadExecutor();
		this.sessionID = sessionID;
		this.sessionLeader = sessionLeader;
		this.sessionName = sessionName;
		clientConnections = new ConcurrentHashMap<Integer, CNPConnection>();
		clientIdToName = new ConcurrentHashMap<Integer, String>();
		filePermissions = new ConcurrentHashMap<Account, FilePermissionLevel>();
		chatPermissions = new ConcurrentHashMap<Account, ChatPermissionLevel>();
	}

	/**
	 * Returns the database ID of the session leader.
	 * 
	 * @return database ID of the session leader
	 */
	public int getSessionLeader() {
		return sessionLeader;
	}

	/**
	 * Returns the unique session name.
	 * 
	 * @return the unique name of the session
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * Returns the Database ID of the session.
	 * 
	 * @return The database ID
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * Adds a user to the session.
	 * 
	 * @param userAccount the account to be added
	 * @param connection the connection of the user to be added
	 */
	public void addUser(int userID, String username, CNPConnection connection,
			String AuthToken) {
		connection.setUserID(userID);
		connection.setSessionID(sessionID);
		connection.setAuth(AuthToken);
		clientConnections.put(userID, connection);
		clientIdToName.put(userID, username);
	}

	/**
	 * Removes an user from the session.
	 * 
	 * @param userID the account to be removed
	 */
	public void removeUser(int userID) {
		clientConnections.remove(userID);
		clientIdToName.remove(userID);
	}

	/**
	 * Creates a source file for this session. It is synchronized for
	 * multi-threading.
	 * 
	 * @param filename the name of the file (no path).
	 * @param type the file type. Include period.
	 */
	public synchronized ServerSourceFile createFile(String filename,
			SourceType type) {
		ServerSourceFile file = new ServerSourceFile(NEXT_FILEID, JGit.REPO_DIR
				+ File.separator + gitRepo.getName() + File.separator
				+ filename, type, server);
		sourceFiles.put(NEXT_FILEID, file);
		NEXT_FILEID++;
		return file;
	}

	/**
	 * Delete a source file.
	 * 
	 * @param filename the filename to be deleted. No need for file type.
	 */
	public void deleteFile(String filename) {
		sourceFiles.remove(filename);
	}

	/**
	 * Clone a repository using Git. [Not Implemented]
	 * 
	 * @return an archive of the Git repository
	 */
	public File cloneRepo() {
		// TODO implement
		return new File("");
	}

	/**
	 * Execute a task for creating files.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(CreateFileTask task) {

		// the response to send to the client
		CreateFileTaskResponse response = null;

		// must be session leader to create files
		if (task.getUserID() == sessionLeader
				&& server.userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			ServerSourceFile sourceFile = createFile(task.getFilename(),
					task.getType());

			response = new CreateFileTaskResponse(sourceFile.getFileID(),
					task.getUserID(), sourceFile.getFilename(),
					sourceFile.getType(), true);

			// register sessionLeader as a file listener, since sessionLeader
			// will automatically have file opened
			sourceFile.addFileTaskEventListener(task.getUserID(),
					task.getConnection());
			distributeTask(response);

		} else {
			// session leader authentication failed
			response = new CreateFileTaskResponse(-1, -1, "n/a",
					SourceType.GENERAL, false);
			distributeTask(response, task.getUserID());
		}

	}

	/**
	 * Execute a task for opening a file.
	 */
	public void executeTask(OpenFileTask task) {
		OpenFileTaskResponse response = null;

		// authenicate user using token
		if (server.userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			ServerSourceFile sourceFile = sourceFiles.get(task.getFileID());

			// create response that includes file contents
			response = new OpenFileTaskResponse(sourceFile.getFileID(),
					sourceFile.getFilename(), sourceFile.toString(), true);

			// register user as a file listener
			sourceFile.addFileTaskEventListener(task.getUserID(),
					task.getConnection());
		} else {
			// user authentication fails
			response = new OpenFileTaskResponse(-1, "n/a", "n/a", false);
		}

		distributeTask(response, task.getUserID());
	}

	/**
	 * Execute a task for user chat.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(ChatTask task) {

		ChatTaskResponse response = new ChatTaskResponse(task.getUsername(),
				task.getMessage());
		distributeTask(response);

	}

	/**
	 * Execute a task for closing files.
	 */
	@Override
	public void executeTask(CloseFileTask task) {
		CloseFileTaskResponse response = null;

		// authenticate user using token
		if (server.userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			ServerSourceFile sourceFile = sourceFiles.get(task.getFileID());

			// create response that includes tab index to close
			response = new CloseFileTaskResponse(sourceFile.getFileID(),
					task.getTabIndex(), true);

			// unregister user as a file listener
			sourceFile.addFileTaskEventListener(task.getUserID(),
					task.getConnection());
		} else {
			// user authentication fails
			response = new CloseFileTaskResponse(-1, -1, false);
		}

		distributeTask(response);
	}

	/**
	 * Execute a task for deleting a file.
	 */
	@Override
	public void executeTask(DeleteFileTask task) {
		// TODO implement

	}

	/**
	 * Execute a task for downloading a repo.
	 */
	@Override
	public void executeTask(DownloadRepoTask task) {
		// TODO implement

	}

	/**
	 * Execute a task for compiling source code.
	 */
	@Override
	public void executeTask(CompileTask task) {
		// TODO implement

	}

	/**
	 * Submit a sessionTask to the queue for execution.
	 * 
	 * @param task the task to submit
	 */
	public void submitTask(SessionTask task) {
		sessionTaskQueue.submit(task);
	}

	/**
	 * Distribute a response task to session users.
	 * 
	 * @param task the distribution task you wish to send out
	 */
	public void distributeTask(TaskResponse task) {
		for (Entry<Integer, CNPConnection> clientEntry : clientConnections
				.entrySet()) {
			SendResponseTask responseTask = new SendResponseTask(task,
					clientEntry.getValue());
			sessionTaskCourier.submit(responseTask);
		}
	}

	/**
	 * 
	 * Distribute a task to a specific user.
	 * 
	 * @param task the task to distribute
	 * @param userId user ID of the target user
	 */
	public void distributeTask(TaskResponse task, int userId) {
		SendResponseTask responseTask = new SendResponseTask(task,
				clientConnections.get(userId));
		sessionTaskCourier.submit(responseTask);
	}

	/**
	 * Get a file, given a file ID.
	 * 
	 * @param fileID the file ID you wish to get
	 * @return the serverSourceFile for the given file ID
	 */
	public ServerSourceFile getFile(int fileID) {
		return sourceFiles.get(fileID);
	}

	/**
	 * Check to see if two sessions are equal.
	 * 
	 * @param session the session you wish to test for equality.
	 * @return true, if the two sessions are equal; false otherwise.
	 */
	public boolean equals(CNPSession session) {

		if ((this.sessionID == session.getSessionID())
				&& (this.sessionName.equals(session.getSessionName()))
				&& (this.sessionLeader == session.getSessionLeader())) {
			return true;
		}
		return false;
	}

	/**
	 * Get the map source files for the session.
	 * 
	 * @return the map from fileID to sourceFiles
	 */
	public Map<Integer, ServerSourceFile> getSourceFiles() {
		return sourceFiles;
	}

	/**
	 * Get the list of source files for the session.
	 *  
	 * @return the list of sourceFile.
	 */
	public List<SourceFile> getSourceFilesList() {
		ArrayList<SourceFile> list = new ArrayList<SourceFile>();
		for (Integer key : sourceFiles.keySet()) {
			list.add(sourceFiles.get(key));
		}
		return list;
	}

	/**
	 * Set the source files for the session.
	 * 
	 * @param sourceFiles the source files to be set in the map for fileIDs 
	 * to sourceFiles
	 */
	public void setSourceFiles(Map<Integer, ServerSourceFile> sourceFiles) {
		this.sourceFiles = sourceFiles;
	}

	/**
	 * Get the client connections for the session.
	 * 
	 * @return the map between the connectionID and CNPConnections.
	 */
	public Map<Integer, CNPConnection> getClientConnections() {
		return clientConnections;
	}

	/**
	 * Set the client connections for the session.
	 * 
	 * @param clientConnections the connections to be set in the map for 
	 * connectionsID and CNPConnections.
	 */
	public void setClientConnections(
			Map<Integer, CNPConnection> clientConnections) {
		this.clientConnections = clientConnections;
	}

	/**
	 * Get the client ID mapping to the client name.
	 * 
	 * @return the map between the clientID and clientName
	 */
	public Map<Integer, String> getClientIdToName() {
		return clientIdToName;
	}

	/**
	 * Set the client ID mapping to the client name.
	 * 
	 * @param clientIdToName the map to be set 
	 * between clientID and clientName.
	 */
	public void setClientIdToName(Map<Integer, String> clientIdToName) {
		this.clientIdToName = clientIdToName;
	}

	/**
	 * Return the Git repo for this session.
	 * 
	 * @return the repository that is bound to this session
	 */
	public JRepository getGitRepo() {
		return gitRepo;
	}

	/**
	 * Set the Git repo for this session.
	 * 
	 * @param gitRepo repo to be set for this session
	 */
	public void setGitRepo(JRepository gitRepo) {
		this.gitRepo = gitRepo;
		for (int i = 0; i < gitRepo.getDirectoryFiles().length; i++) {
			if (gitRepo.getDirectoryFiles()[i].getName().compareTo(".git") == 0) {
				continue;
			}
			createFile(gitRepo.getDirectoryFiles()[i].getName(),
					SourceType.GENERAL);
		}
	}
}
