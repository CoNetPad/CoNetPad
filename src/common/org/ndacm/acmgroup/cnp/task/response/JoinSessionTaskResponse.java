package org.ndacm.acmgroup.cnp.task.response;

import java.util.Collection;
import java.util.List;
import java.util.Set;
/**
 * A class.
 *
 */
public class JoinSessionTaskResponse extends TaskResponse {

	private int userID;
	private String username;
	private String sessionName;
	private int sessionID;
	private boolean success;
	private List<String> sessionFiles;
	private Set<Integer> fileIDs;
	private Collection<String> connectedUsers;

	public JoinSessionTaskResponse(int userID, String username, String sessionName, 
			int sessionID, boolean success, List<String> sessionFiles, Set<Integer> fileIDs,
			Collection<String> connectedUsers) {
		this.userID = userID;
		this.username = username;
		this.sessionName = sessionName;
		this.sessionID = sessionID;
		this.success = success;
		this.sessionFiles = sessionFiles;
		this.fileIDs = fileIDs;
		this.connectedUsers = connectedUsers;
	}

	@Override
	public void run() {
		client.executeTask(this);

	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public List<String> getSessionFiles() {
		return sessionFiles;
	}

	public void setSessionFiles(List<String> sessionFiles) {
		this.sessionFiles = sessionFiles;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Integer> getFileIDs() {
		return fileIDs;
	}

	public void setFileIDs(Set<Integer> fileIDs) {
		this.fileIDs = fileIDs;
	}

	public Collection<String> getConnectedUsers() {
		return connectedUsers;
	}

	public void setConnectedUsers(List<String> connectedUsers) {
		this.connectedUsers = connectedUsers;
	}


	
	
	

}
