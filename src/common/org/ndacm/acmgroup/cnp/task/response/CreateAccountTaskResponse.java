package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for creating an account.
 * 
 * @author Josh Tan
 *
 */
public class CreateAccountTaskResponse extends TaskResponse {

	private int userID;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param success the success status of the task
	 */
	public CreateAccountTaskResponse(int userID, boolean success) {
		this.userID = userID;
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
	 * Get the user ID for the task.
	 * 
	 * @return the user ID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Set the success status for the task.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
	}

}
