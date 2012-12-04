package org.ndacm.acmgroup.cnp.task.response;

import java.util.Collection;
import java.util.List;

/**
 * Task response for joining a session.
 */
public class JoinSessionTaskResponse extends TaskResponse {

	private int userID;
	private String username;
	private String sessionName;
	private int sessionID;
	private boolean success;
	private List<String> sessionFiles;
	private List<Integer> fileIDs;
	private Collection<String> connectedUsers;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param username the username for the task
	 * @param sessionName the session name for the task
	 * @param sessionID the session ID for the task
	 * @param success the success status for the task
	 * @param sessionFiles the list of session filenames for the task
	 * @param fileIDs the list of session file IDs for the task
	 * @param connectedUsers the list of connected session users
	 */
	public JoinSessionTaskResponse(int userID, String username, String sessionName, 
			int sessionID, boolean success, List<String> sessionFiles, List<Integer> fileIDs,
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

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);

	}

	/**
	 * Get the session name for the task.
	 * 
	 * @return the session name
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * Set the session name for the task.
	 * 
	 * @param sessionName the session name
	 */
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	/**
	 * Get whether the original task was a success or not.
	 * 
	 * @return the success status of the original task
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Set the success status of the task.
	 * 
	 * @param success the success status
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * Get the session ID for the task.
	 * 
	 * @return the session ID
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * Set the session ID for the task.
	 * 
	 * @param sessionID the session ID
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Get the list of session filenames for the task.
	 * 
	 * @return the list of session filenames
	 */
	public List<String> getSessionFiles() {
		return sessionFiles;
	}

	/**
	 * Set the list of session filenames for the task.
	 * 
	 * @param sessionFiles the list of session filenames
	 */
	public void setSessionFiles(List<String> sessionFiles) {
		this.sessionFiles = sessionFiles;
	}

	/**
	 * Get the user ID for the task.
	 * 
	 * @return the user ID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Set the user ID for the task.
	 * 
	 * @param userID the user ID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * Get the username for the task.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username for the task.
	 * 
	 * @param username the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the list of session file IDs for the task.
	 * 
	 * @return the list of session file IDs
	 */
	public List<Integer> getFileIDs() {
		return fileIDs;
	}

	/**
	 *  Set the list of session file IDs for the task.
	 * 
	 * @param fileIDs the list of session file IDs
	 */
	public void setFileIDs(List<Integer> fileIDs) {
		this.fileIDs = fileIDs;
	}

	/**
	 * Get a list of connected users for the joined session.
	 * 
	 * @return the list of connected usernames
	 */
	public Collection<String> getConnectedUsers() {
		return connectedUsers;
	}

	/**
	 * Set the list of connected users for the joined session.
	 * 
	 * @param connectedUsers the list of connected usernames
	 */
	public void setConnectedUsers(List<String> connectedUsers) {
		this.connectedUsers = connectedUsers;
	}

}
