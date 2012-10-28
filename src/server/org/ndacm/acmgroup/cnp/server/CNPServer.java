package org.ndacm.acmgroup.cnp.server;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLServerSocket;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.Task;

public class CNPServer {
	
	private SSLServerSocket socket;
	private Map<Account, CNPConnection> clientConnections; // implement with ConcurrentHashMap
	private ExecutorService taskCourier;
	private ExecutorService chatQueue; // single-thread
	
	private Database database;
	private Compiler compiler;
	
	private SecretKey key;
	private Cipher cipher;
	
	public CNPServer() {
		// TODO implement
		
		chatQueue = Executors.newSingleThreadExecutor();
	}
	
	public Account createAccount(String username, String password) {
		// TODO implement
		return new Account();
	}
	
	public Account retrieveAccount(String username, String password) {
		// TODO implement
		return new Account();
	}
	
	public CNPConnection connect(String username, String sessionName) {
		// TODO implement
		return new CNPConnection();
	}
	
	public CNPSession createCNPSession(String sessionName) {
		// TODO implement
		return new CNPSession();
	}
	
	public CNPSession createCNPSession(String sessionName, String password) {
		// TODO implement
		return new CNPSession();
	}
	
	public CNPSession retrieveCNPSession(String sessionName) {
		// TODO implement
		return new CNPSession();
	}
	
	public void executeTask(EditorTask task) {
		
		task.getSourceFile().addTask(task);
		
	}
	
	public void executeTask(ChatTask task) { // have throw TaskExecutionException
		// TODO implement
	}
	
	public void distributeTask(Task task) { // have throw TaskExecutionException
		// TODO implement
	}
	
	public File compile(List<String> fileNames, CNPSession session) {
		// TODO implement
		return new File(""); // return tar or something
	}
	
	
	
	

}
