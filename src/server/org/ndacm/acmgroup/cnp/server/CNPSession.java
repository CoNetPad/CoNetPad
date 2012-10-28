package org.ndacm.acmgroup.cnp.server;

import java.io.File;
import java.util.Map;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.git.GitRepo;
import org.ndacm.acmgroup.cnp.task.EditorTask;

public class CNPSession {
	
	private String sessionName;
	private CNPServer server;
	private GitRepo gitRepo;
	private Map<String, ServerSourceFile> sourceFiles; // implement with ConcurrentHashMap
	
	private Account sessionLeader;
	private Map<Account, Account.FilePermissionLevel> filePermissions; // implement with CHM ^
	private Map<Account, Account.ChatPermissionLevel> chatPermissions;
	
	public CNPSession() {
		// TODO implement
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
	
}
