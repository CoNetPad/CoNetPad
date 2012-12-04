package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.network.CNPConnection;

/**
 * Task for creating a file within a session.
 * 
 * @author Josh Tan
 *
 */
public class CreateFileTask extends SessionTask {

	protected int userID;
	protected String filename;
	protected SourceType type;
	protected String userAuthToken;
	protected CNPConnection connection;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param sessionID the session ID for the task
	 * @param filename the filename for the task
	 * @param type the file type for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public CreateFileTask(int userID, int sessionID, String filename, SourceType type, String userAuthToken) {
		this.userID = userID;
		this.sessionID = sessionID;
		this.filename = filename;
		this.type = type;
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
	 * Get the file type for the task.
	 * 
	 * @return the file type
	 */
	public SourceType getType() {
		return type;
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
	 * @param connection the connection
	 */
	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}

	/**
	 * Set the user ID for the task.
	 * 
	 * @param userID the user ID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

}
