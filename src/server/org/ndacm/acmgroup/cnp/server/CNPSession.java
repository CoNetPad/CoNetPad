package org.ndacm.acmgroup.cnp.server;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel;
import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.git.JGit;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;


public class CNPSession {
	
	private static final String SESSION_NAME_CHARS = "abcdefghijklmnopqrstuvwxyz";
	private static String baseDirectory;
	private static Random rnd = new Random();
	
	private int sessionID;
	private CNPServer server;
	private String sessionName;
	private JGit gitRepo;
	private Map<String, ServerSourceFile> sourceFiles; // implement with ConcurrentHashMap
	private String sourceFileDirectory;
	
	private ExecutorService taskCourier;
	private ExecutorService sessionTaskQueue; // single-thread
	
	private int sessionLeader;
	private Map<Account, CNPConnection> clientConnections; // implement with ConcurrentHashMap
	private Map<Account, FilePermissionLevel> filePermissions; // implement with CHM ^
	private Map<Account, ChatPermissionLevel> chatPermissions;
	
	
	public CNPSession(int sessionID, String sessionName, CNPServer server, int sessionLeader) {
	
		this.server = server;
		
		baseDirectory = server.getBaseDirectory() + System.getProperty("file.separator") +
				"sessions" + System.getProperty("file.separator") +
				sessionName + System.getProperty("file.separator");
		
		gitRepo = new JGit();
		sourceFiles = new ConcurrentHashMap<String, ServerSourceFile>();
		
		taskCourier = Executors.newCachedThreadPool();
		sessionTaskQueue = Executors.newSingleThreadExecutor();
		
		this.sessionLeader = sessionLeader;
		clientConnections = new ConcurrentHashMap<Account, CNPConnection>();
		filePermissions = new ConcurrentHashMap<Account, FilePermissionLevel>();
		chatPermissions = new ConcurrentHashMap<Account, ChatPermissionLevel>();

	}
	
	public void addUser(Account userAccount, CNPConnection connection) {
		clientConnections.put(userAccount, connection);
	}
	
	public void removeUser (Account userAccount) {
		clientConnections.remove(userAccount);
	}
	
	public void createFile(String filename, SourceType type) {
		ServerSourceFile file = new ServerSourceFile(filename, type);
		sourceFiles.put(filename, file);
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
	
	public void distributeTask(TaskResponse task) { // have throw TaskExecutionException
		taskCourier.submit(task);
	}
	
	// http://stackoverflow.com/questions/2863852/how-to-generate-a-random-string-in-java
	public static String generateString(int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = SESSION_NAME_CHARS.charAt(rnd.nextInt(SESSION_NAME_CHARS.length()));
		}
		return new String(text);
	}
	
}
