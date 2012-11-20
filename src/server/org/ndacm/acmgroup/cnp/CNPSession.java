package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel;
import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.git.JGit;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;


public class CNPSession {

	private static final String SESSION_NAME_CHARS = "abcdefghijklmnopqrstuvwxyz";
	private static volatile int NEXT_FILEID;
	private static int NAME_LENGTH = 5;
	private static String baseDirectory;
	private static Random rnd = new Random();

	private int sessionID;
	private CNPServer server;
	private String sessionName;
	private JGit gitRepo;
	private Map<Integer, ServerSourceFile> sourceFiles; // maps fileID to ServerSourceFile
	private String gitPath;
	private ExecutorService sessionTaskCourier;
	private ExecutorService sessionTaskQueue; // single-thread
//	private SessionType type;
	private int sessionLeader;
	private String ircChannel;
	private String encryptedPassword;
	private Map<Account, CNPConnection> clientConnections; // implement with ConcurrentHashMap
	private Map<Account, Account.FilePermissionLevel> filePermissions; // implement with CHM ^
	private Map<Account, Account.ChatPermissionLevel> chatPermissions;



	/**
	 * This creates a new instance of a CNP Session
	 * @param sessionID				The database ID of the session
	 * @param sessionName			The unique session name
	 * @param server				The sever object
	 * @param sessionLeader			The database ID of the user who is the session leader
	 */
	public CNPSession(int sessionID, String sessionName, CNPServer server, int sessionLeader) {

		this.server = server;

		baseDirectory = server.getBaseDirectory() + System.getProperty("file.separator") +
				"sessions" + System.getProperty("file.separator") +
				sessionName + System.getProperty("file.separator");

		gitRepo = new JGit();
		sourceFiles = new ConcurrentHashMap<Integer, ServerSourceFile>();

		sessionTaskCourier = Executors.newCachedThreadPool();
		sessionTaskQueue = Executors.newSingleThreadExecutor();
		this.sessionID = sessionID;
		this.sessionLeader = sessionLeader;
		this.sessionName = sessionName;
		clientConnections = new ConcurrentHashMap<Account, CNPConnection>();
		filePermissions = new ConcurrentHashMap<Account, FilePermissionLevel>();
		chatPermissions = new ConcurrentHashMap<Account, ChatPermissionLevel>();

	}

	public CNPSession() {
		// TODO implement
		sessionTaskQueue = Executors.newSingleThreadExecutor();
	}
	/**
	 * This returns the database ID of the sesison leader
	 * @return		Database Id of the session leader
	 */
	public int getSessionLeader()
	{
		return sessionLeader;
	}
	/**
	 * This returns the unique session name
	 * @return		The unique name of the session.
	 */
	public String getSessionName()
	{
		return sessionName;
	}
	public String getIrcChannel()
	{
		return ircChannel;
	}

	public String getEncryptedPassword()
	{
		return encryptedPassword;
	}
	public String getGitPath()
	{
		return gitPath;
	}
	public int getSessionID()
	{
		return sessionID;
	}
	public void addUser(Account userAccount, CNPConnection connection) {
		clientConnections.put(userAccount, connection);
	}

	public void removeUser (Account userAccount) {
		clientConnections.remove(userAccount);
	}

	public synchronized void createFile(String filename, SourceType type) {
		ServerSourceFile file = new ServerSourceFile(NEXT_FILEID, filename, type);
		sourceFiles.put(NEXT_FILEID, file);
		NEXT_FILEID++;
	}

	public void deleteFile(String filename) {
		sourceFiles.remove(filename);
	}

	public boolean commitAndPush(String message) {
		// TODO implement
		return false;
	}

	public boolean commitAndPush() {
		return commitAndPush("");
	}

	public File cloneRepo() {
		// TODO implement
		return new File("");
	}

	public void executeTask(EditorTask task) {
		// execute task using ServerSourceFile's ExecutorService
		SourceFile file = task.getFile();
		if (file instanceof ServerSourceFile) {
			ServerSourceFile serverFile = (ServerSourceFile) file;
			serverFile.executeTask(task);
		}
	}

	public void distributeTask(TaskResponse task) { // have throw TaskExecutionException
		sessionTaskCourier.submit(task);
	}

	// http://stackoverflow.com/questions/2863852/how-to-generate-a-random-string-in-java
	public static String generateString() {
		char[] text = new char[NAME_LENGTH];
		for (int i = 0; i < NAME_LENGTH; i++) {
			text[i] = SESSION_NAME_CHARS.charAt(rnd.nextInt(SESSION_NAME_CHARS.length()));
		}
		return new String(text);
	}

	public ServerSourceFile getFile(int fileID) {
		return sourceFiles.get(fileID);
	}
	public boolean equals(CNPSession session)
	{
		//int sessionID, String sessionName, CNPServer server, int sessionLeader
		if( (this.sessionID == session.getSessionID()) && (this.sessionName.equals(session.getSessionName())) && (this.sessionLeader == session.getSessionLeader()) )
		{
			return true;
		}
		return false;
	}
}
