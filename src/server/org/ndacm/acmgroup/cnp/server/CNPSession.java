package org.ndacm.acmgroup.cnp.server;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;


public class CNPSession {
	
	private String sessionName;
	private CNPServer server;
	private GitRepo gitRepo;
	private Map<String, ServerSourceFile> sourceFiles; // implement with ConcurrentHashMap
	
	private ExecutorService taskCourier;
	private ExecutorService chatQueue; // single-thread
	
	private Account sessionLeader;
	private Map<Account, CNPConnection> clientConnections; // implement with ConcurrentHashMap
	private Map<Account, Account.FilePermissionLevel> filePermissions; // implement with CHM ^
	private Map<Account, Account.ChatPermissionLevel> chatPermissions;
	
	public CNPSession() {
		// TODO implement
		chatQueue = Executors.newSingleThreadExecutor();
	}
	
	public boolean addUser(Account userAccount) {
		// TODO implement
		return false;
	}
	
	public boolean removeUser (Account userAccount) {
		// TODO implement
		return false;
	}
	
	public boolean createFile(String filename) {
		// TODO implement
		return false;
	}
	
	public boolean deleteFile(String filename) {
		// TODO implement
		return false;
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
	
}
