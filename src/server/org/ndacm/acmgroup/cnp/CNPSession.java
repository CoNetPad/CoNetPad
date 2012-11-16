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
	private SessionType type;
	private Account sessionLeader;
	private String ircChannel;
	private String encryptedPassword;
	private Map<Account, CNPConnection> clientConnections; // implement with ConcurrentHashMap
	private Map<Account, Account.FilePermissionLevel> filePermissions; // implement with CHM ^
	private Map<Account, Account.ChatPermissionLevel> chatPermissions;



	public enum SessionType {
		PUBLIC,
		PRIVATE;

		public int intValue()
		{
			if(this.equals(SessionType.PUBLIC) )
			{
				return 0;
			}
			return 1;
		}

		public static SessionType getType(int i)
		{
			if(i == 0)
			{	return SessionType.PUBLIC;	}
			if(i == 1)
			{	return SessionType.PRIVATE;	}
			return SessionType.PUBLIC;
		}
	}


	public CNPSession(int sessionID, String sessionName, CNPServer server, Account sessionLeader) {

		this.server = server;

		baseDirectory = server.getBaseDirectory() + System.getProperty("file.separator") +
				"sessions" + System.getProperty("file.separator") +
				sessionName + System.getProperty("file.separator");

		gitRepo = new JGit();
		sourceFiles = new ConcurrentHashMap<Integer, ServerSourceFile>();

		sessionTaskCourier = Executors.newCachedThreadPool();
		sessionTaskQueue = Executors.newSingleThreadExecutor();

		this.sessionLeader = sessionLeader;
		clientConnections = new ConcurrentHashMap<Account, CNPConnection>();
		filePermissions = new ConcurrentHashMap<Account, FilePermissionLevel>();
		chatPermissions = new ConcurrentHashMap<Account, ChatPermissionLevel>();

	}

	public CNPSession() {
		// TODO implement
		sessionTaskQueue = Executors.newSingleThreadExecutor();
	}
	public CNPSession(Account leader, String name, SessionType t){
		sessionLeader = leader;
		sessionName = name;
		type = t;
		encryptedPassword = null;
	}
	public CNPSession(Account leader, String name, SessionType t, String pass){
		sessionLeader = leader;
		sessionName = name;
		type = t;
		encryptedPassword = pass;
	}
	public Account getSessionLeader()
	{
		return sessionLeader;
	}

	public String getSessionName()
	{
		return sessionName;
	}
	public String getIrcChannel()
	{
		return ircChannel;
	}
	public SessionType getType()
	{
		return type;
	}
	public String getEncryptedPassword()
	{
		return encryptedPassword;
	}
	public String getGitPath()
	{
		return gitPath;
	}
	public boolean equals(CNPSession s)
	{
		boolean passMatch = true;

		if(type == SessionType.PRIVATE)
		{
			if( (s.getEncryptedPassword() == null) || (encryptedPassword == null))
			{
				return false;
			}
			else
			{
				if(!s.getEncryptedPassword().equals(encryptedPassword))
				{
					passMatch = false;
				}
			}
		}

		if( (s.getSessionName().equals(sessionName)) && (s.getSessionLeader().equals(sessionLeader)) &&
				(s.getType() == type) && (s.getGitPath().equals(gitPath)) && (s.getIrcChannel().equals(ircChannel)))
		{

			return true & passMatch;
		}
		return false;
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

}
