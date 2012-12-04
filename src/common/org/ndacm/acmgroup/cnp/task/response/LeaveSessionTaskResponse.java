package org.ndacm.acmgroup.cnp.task.response;

/**
 * The task response for leaving a session.
 * 
 * @author Josh Tan
 *
 */
public class LeaveSessionTaskResponse extends TaskResponse {

	private int userID;
	private String username;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param username the username for the task
	 * @param success whether or not the task was a success
	 */
	public LeaveSessionTaskResponse(int userID, String username, boolean success) {
		this.userID = userID;
		this.username = username;
		this.success = success;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);
	}

	/**
	 * Get the success status of the original task.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Get the user ID for the task.
	 * 
	 * @return the user ID for the task
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Set the user ID for the task.
	 * 
	 * @param userID the user ID for the task
	 */
	public void setUserID(int userID) {
		this.userID = userID;
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
	 * Set the username for the task.
	 * 
	 * @param username the username for the task
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
