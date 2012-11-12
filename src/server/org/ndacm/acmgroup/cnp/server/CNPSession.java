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
	//private GitRepo gitRepo;
	private Map<String, ServerSourceFile> sourceFiles; // implement with ConcurrentHashMap
	private String gitPath;
	private ExecutorService taskCourier;
	private ExecutorService chatQueue; // single-thread
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
	
	
	public CNPSession() {
		// TODO implement
		chatQueue = Executors.newSingleThreadExecutor();
	}
	public CNPSession(Account leader, String name, SessionType t, String channel, String gPath){
		sessionLeader = leader;
		sessionName = name;
		type = t;
		ircChannel = channel;
		gitPath = gPath;
		encryptedPassword = null;
	}
	public CNPSession(Account leader, String name, SessionType t, String channel, String gPath, String pass){
		sessionLeader = leader;
		sessionName = name;
		type = t;
		ircChannel = channel;
		gitPath = gPath;
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
