package org.ndacm.acmgroup.cnp.task;

/**
 * Task for deleting a file from the session.
 * 
 * @author Josh Tan
 *
 */
public class DeleteFileTask extends SessionTask {

	protected int userID;
	protected String filename;
	protected String userAuthToken;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param filename the file name for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public DeleteFileTask(int userID, String filename, String userAuthToken) {
		this.userID = userID;
		this.filename = filename;
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
	 * Get the filename for the task.
	 * 
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Get the user authentication token for the task.
	 * @return the user authentication token 
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

}
