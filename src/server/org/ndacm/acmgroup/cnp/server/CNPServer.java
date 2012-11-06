package server.org.ndacm.acmgroup.cnp.server;
import java.io.File;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLServerSocket;

import server.org.ndacm.acmgroup.cnp.*;
import server.org.ndacm.acmgroup.cnp.database.*;

public class CNPServer {
	
	private SSLServerSocket socket;


	
	private Database database;
	private Compiler compiler;
	
	private SecretKey key;
	private Cipher cipher;
	
	public CNPServer() {
		// TODO implement
		
		
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
	

	
	public File compile(List<String> fileNames, CNPSession session) {
		// TODO implement
		return new File(""); // return tar or something
	}
	
	
	
	

}
