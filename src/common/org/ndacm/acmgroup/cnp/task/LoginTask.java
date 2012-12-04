package org.ndacm.acmgroup.cnp.task;

/**
 * Task for logging in to the server.
 * 
 * @author Josh Tan
 *
 */
public class LoginTask extends ServerTask {

	protected String username;
	protected String password;

	/**
	 * Default constructor.
	 * 
	 * @param username the username for the task
	 * @param password the password for the task
	 */
	public LoginTask(String username, String password) {
		this.username = username;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the password for the task.
	 * 
	 *@return the password
	 */
	public String getPassword() {
		return password;
	}

}
