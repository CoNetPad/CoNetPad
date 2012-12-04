package org.ndacm.acmgroup.cnp.task;

/**
 * Task for disconnecting from a session.
 * 
 * @author Josh Tan
 */
public class LeaveSessionTask extends ServerTask {

	protected int userID;
	private String username;
	private int sessionID;
	protected String userAuthToken;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param username the username for the task
	 * @param sessionID the session ID for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public LeaveSessionTask(int userID, String username, int sessionID, String userAuthToken) {
		this.userID = userID;
		this.username = username;
		this.sessionID = sessionID;
		this.userAuthToken = userAuthToken;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		server.executeTask(this);
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
	 * @return the user ID
	 */
	public String getUserAuthToken() {
		return userAuthToken;
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

}
