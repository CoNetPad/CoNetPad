package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

/**
 * Task for opening a file.
 * 
 * @author Josh Tan
 *
 */
public class OpenFileTask extends SessionTask {

	protected int userID;
	protected int fileID;
	protected String userAuthToken;
	protected CNPConnection connection;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param sessionID the session ID for the task
	 * @param fileID the file ID for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public OpenFileTask(int userID, int sessionID, int fileID, String userAuthToken) {
		this.userID = userID;
		this.fileID = fileID;
		this.userAuthToken = userAuthToken;
		this.sessionID = sessionID;
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
	 * Get the file ID for the task.
	 * 
	 * @return the file ID
	 */
	public int getFileID() {
		return fileID;
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
	 * Get the connection for the task.
	 * 
	 * @return the connection
	 */
	public CNPConnection getConnection() {
		return connection;
	}

	/**
	 * Set the connection for the task.
	 * 
	 * @param connection the connection
	 */
	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}

}
