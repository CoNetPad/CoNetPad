package org.ndacm.acmgroup.cnp.task;

/**
 * Task for downloading a session repository from the server.
 * 
 * @author Josh Tan
 *
 */
public class DownloadRepoTask extends SessionTask {

	protected int userID;
	protected String userAuthToken;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public DownloadRepoTask(int userID, String userAuthToken) {
		this.userID = userID;
		this.userAuthToken = userAuthToken;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		session.executeTask(this);
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
	 * Get the user authentication token for the task.
	 * 
	 * @return the user authentication token.
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

}
