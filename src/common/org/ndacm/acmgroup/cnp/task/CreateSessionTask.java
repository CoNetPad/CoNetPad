package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

/**
 * Task for creating a session on the server.
 * 
 * @author Josh Tan
 *
 */
public class CreateSessionTask extends ServerTask {

	protected int sessionLeader;
	protected String userAuthToken;
	private CNPConnection connection;

	/**
	 * Default constructor.
	 * 
	 * @param sessionLeader the ID of the session leader
	 * @param userAuthToken the user authentication token
	 */
	public CreateSessionTask(int sessionLeader, String userAuthToken) {
		this.sessionLeader = sessionLeader;
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
	 * Get the ID of the session leader for this task.
	 * 
	 * @return the ID of the session leader
	 */
	public int getSessionLeader() {
		return sessionLeader;
	}

	/**
	 * Get the user authentication token for this task.
	 * 
	 * @return the user authentication token
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

	/**
	 * Get the connection for this task.
	 * 
	 * @return the connection
	 */
	public CNPConnection getConnection() {
		return connection;
	}

	/**
	 * Set the connection for this task.
	 * 
	 * @param connection the connection
	 */
	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}

}
