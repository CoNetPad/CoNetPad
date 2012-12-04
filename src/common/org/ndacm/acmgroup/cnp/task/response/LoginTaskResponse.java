package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for logging in a user.
 * 
 * @author Josh Tan
 *
 */
public class LoginTaskResponse extends TaskResponse {

	private int userID;
	private String username;
	private boolean success;
	private String userAuthToken;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param username the username for the task
	 * @param success the success status of the original task
	 * @param userAuthToken the user authentication token for the task
	 */
	public LoginTaskResponse(int userID, String username, boolean success,
			String userAuthToken) {
		this.userID = userID;
		this.username = username;
		this.success = success;
		this.userAuthToken = userAuthToken;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);

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
	 * Get whether the original task was a success or not.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Set whether the original task was a success or not.
	 * 
	 * @param success the success status of the original task
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * Get the user authentication token for the task.
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

	/**
	 * Get the username for the task.
	 * 
	 * @return the username for the task
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username for the task
	 *  
	 * @param username the username for the task
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Set the user authentication token for the task.
	 * 
	 * @param userAuthToken the user authentication token
	 */
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}

}
