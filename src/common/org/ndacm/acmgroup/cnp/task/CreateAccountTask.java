package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

/**
 * Task for creating an account on the server.
 * 
 * @author Josh Tan
 *
 */
public class CreateAccountTask extends ServerTask {

	protected String username;
	protected String email;
	protected String password;
	private CNPConnection connection = null;

	/**
	 * Default constructor.
	 * 
	 * @param username the username for the task
	 * @param email the email address for the task
	 * @param password the user password for the task
	 */
	public CreateAccountTask(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		server.executeTask(this);
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
	 * Get the email address for the task.
	 * 
	 * @return the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Get the user password for the task.
	 * 
	 * @return the user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *  Get the connection for the task.
	 *  
	 *  @return the connection
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
