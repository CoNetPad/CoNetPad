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
import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.git.JRepository;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.CloseFileTask;
import org.ndacm.acmgroup.cnp.task.CompileTask;
import org.ndacm.acmgroup.cnp.task.CreateFileTask;
import org.ndacm.acmgroup.cnp.task.DeleteFileTask;
import org.ndacm.acmgroup.cnp.task.DownloadFileTask;
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
 * CNP Session Class This is the class that handles the session specified in the
 * SRS document
 * 
 * @author Josh Tan
 * @version 2.0
 */
public class CNPSession implements SessionTaskExecutor {

	/**
	 * The allowed characters for the Session name generator
	 */
	public static final String SESSION_NAME_CHARS = "abcdefghijklmnopqrstuvwxyz";
	/**
	 * A unique INTEGER for getting next file ID.
	 */
	private static volatile int NEXT_FILEID;
	/**
	 * Max length of session names
	 */
	public static final int NAME_LENGTH = 5;
	/**
	 * The base directories of the files stored
	 */
	private static String baseDirectory;

	/**
	 * The database object to run queries
	 */
	private Database db;
	/**
	 * ;
	 */
	private int sessionID;
	private CNPServer server;
	private String sessionName;
	// private JGit gitRepo;
	private JRepository gitRepo;
	private Map<Integer, ServerSourceFile> sourceFiles; // maps fileID to
														// ServerSourceFile
	private ExecutorService sessionTaskCourier;
	private ExecutorService sessionTaskQueue; // single-thread
	// private SessionType type;
	private int sessionLeader;
	private String encryptedPassword;
	private Map<Integer, CNPConnection> clientConnections; // maps userID to
															// CNPConnection
	private Map<Integer, String> clientIdToName;
	private Map<Account, Account.FilePermissionLevel> filePermissions;
	private Map<Account, Account.ChatPermissionLevel> chatPermissions;

	/**
	 * This creates a new instance of a CNP Session
	 * 
	 * @param sessionID
	 *            The database ID of the session
	 * @param sessionName
	 *            The unique session name
	 * @param server
	 *            The sever object
	 * @param sessionLeader
	 *            The database ID of the user who is the session leader
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
	 * This returns the database ID of the sesison leader
	 * 
	 * @return Database Id of the session leader
	 */
	public int getSessionLeader() {
		return sessionLeader;
	}

	/**
	 * This returns the unique session name
	 * 
	 * @return The unique name of the session.
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * This returns the Database ID of the session
	 * 
	 * @return The database ID
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * This adds user to the session
	 * 
	 * @param userAccount
	 *            The account in which you wish to add
	 * @param connection
	 *            The connection of the user
	 */
	public void addUser(int userID, String username, CNPConnection connection, String AuthToken) {
		connection.setUserID(userID);
		connection.setSessionID(sessionID);
		connection.setAuth(AuthToken);
		clientConnections.put(userID, connection);
		clientIdToName.put(userID, username);
	}

	/**
	 * This removes an user from the session
	 * 
	 * @param userID
	 *            The account you wish to remove
	 */
	public void removeUser(int userID) {
		clientConnections.remove(userID);
		clientIdToName.remove(userID);
	}

	/**
	 * This creates a file to be worked on. It is synchronized for
	 * multi-threading
	 * 
	 * @param filename
	 *            The name of the file. No path
	 * @param type
	 *            The file type. Include period.
	 */
	public synchronized ServerSourceFile createFile(String filename,
			SourceType type) {
		ServerSourceFile file = new ServerSourceFile(NEXT_FILEID, sessionName
				+ File.separator + filename, type, server);
		sourceFiles.put(NEXT_FILEID, file);
		NEXT_FILEID++;
		return file;
	}

	/**
	 * This deletes the file
	 * 
	 * @param filename
	 *            The filename you wish to remove. No need for file type
	 */
	public void deleteFile(String filename) {
		sourceFiles.remove(filename);
	}

	/**
	 * This clones a repository using git. [Not Implemented]
	 * 
	 * @return The new file?
	 */
	public File cloneRepo() {
		// TODO implement
		return new File("");
	}

	/**
	 * This executes a given chat class
	 * 
	 * @param task
	 *            The chat class you wish to execute
	 */
	public void executeTask(CreateFileTask task) {

		CreateFileTaskResponse response = null;
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

	public void executeTask(OpenFileTask task) {
		OpenFileTaskResponse response = null;

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
	 * This executes a given chat class
	 * 
	 * @param task
	 *            The chat class you wish to execute
	 */
	public void executeTask(ChatTask task) {

		ChatTaskResponse response = new ChatTaskResponse(task.getUsername(),
				task.getMessage());
		distributeTask(response);

	}

	@Override
	public void executeTask(CloseFileTask task) {
		CloseFileTaskResponse response = null;

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

	@Override
	public void executeTask(DeleteFileTask task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeTask(DownloadFileTask task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeTask(DownloadRepoTask task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeTask(CompileTask task) {
		// TODO Auto-generated method stub

	}

	/**
	 * This submits a sessionTask to the queue for execution
	 * 
	 * @param task
	 */
	public void submitTask(SessionTask task) {
		sessionTaskQueue.submit(task);
	}

	/**
	 * This distributes a response task to session users
	 * 
	 * @param task
	 *            The distribution task you wish to send out
	 */
	public void distributeTask(TaskResponse task) { // have throw
													// TaskExecutionException
		for (Entry<Integer, CNPConnection> clientEntry : clientConnections
				.entrySet()) {
			SendResponseTask responseTask = new SendResponseTask(task,
					clientEntry.getValue());
			sessionTaskCourier.submit(responseTask);
		}
	}

	public void distributeTask(TaskResponse task, int userId) { // have throw
		// TaskExecutionException
		SendResponseTask responseTask = new SendResponseTask(task,
				clientConnections.get(userId));
		sessionTaskCourier.submit(responseTask);
	}

	/**
	 * This gets a file given a file ID
	 * 
	 * @param fileID
	 *            The file ID you wish to get
	 * @return The serverSourceFile with the given ID.
	 */
	public ServerSourceFile getFile(int fileID) {
		return sourceFiles.get(fileID);
	}

	/**
	 * This checks to see if two sessions are equal
	 * 
	 * @param session
	 *            The session you wish to test is equal
	 * @return True if the two sessions are equal, false otherwise
	 */
	public boolean equals(CNPSession session) {

		if ((this.sessionID == session.getSessionID())
				&& (this.sessionName.equals(session.getSessionName()))
				&& (this.sessionLeader == session.getSessionLeader())) {
			return true;
		}
		return false;
	}

	public Map<Integer, ServerSourceFile> getSourceFiles() {
		return sourceFiles;
	}

	public List<SourceFile> getSourceFilesList() {
		ArrayList<SourceFile> list = new ArrayList<SourceFile>();
		for (Integer key : sourceFiles.keySet()) {
			list.add(sourceFiles.get(key));
		}
		return list;
	}

	public void setSourceFiles(Map<Integer, ServerSourceFile> sourceFiles) {
		this.sourceFiles = sourceFiles;
	}

	public Map<Integer, CNPConnection> getClientConnections() {
		return clientConnections;
	}

	public void setClientConnections(
			Map<Integer, CNPConnection> clientConnections) {
		this.clientConnections = clientConnections;
	}

	public Map<Integer, String> getClientIdToName() {
		return clientIdToName;
	}

	public void setClientIdToName(Map<Integer, String> clientIdToName) {
		this.clientIdToName = clientIdToName;
	}

	public JRepository getGitRepo() {
		return gitRepo;
	}

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
