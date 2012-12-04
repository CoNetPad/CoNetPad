package org.ndacm.acmgroup.cnp.task;

/**
 * Task for joining a session on the server.
 * 
 * @author Josh Tan
 *
 */
public class JoinSessionTask extends ServerTask {

	protected int userID;
	private String username;
	protected String sessionName;
	protected String userAuthToken;

	/**
	 * Default constructor.
	 * 
	 * @param userID the userID for the task
	 * @param username the username for the task
	 * @param sessionName the session name for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public JoinSessionTask(int userID, String username, String sessionName, String userAuthToken) {
		this.userID = userID;
		this.username = username;
		this.sessionName = sessionName;
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
	 * Get the session name for the task.
	 * @return the session name
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * Get the user ID for the task.
	 * @return the user ID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Get the user authentication token for the task.
	 *  
	 * @return the user authentication token
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

}
