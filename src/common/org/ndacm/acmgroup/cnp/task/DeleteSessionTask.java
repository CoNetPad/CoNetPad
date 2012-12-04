package org.ndacm.acmgroup.cnp.task;

/**
 * Task for deleting a session from the server.
 * 
 * @author Josh Tan
 *
 */
public class DeleteSessionTask extends ServerTask {

	protected int userID;
	protected int sessionID;
	protected String userAuthToken;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param sessionID the session ID for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public DeleteSessionTask(int userID, int sessionID, String userAuthToken) {
		this.userID = userID;
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
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * Get the user authentication token for the task.
	 * 
	 * @return the user authentication token
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

}
