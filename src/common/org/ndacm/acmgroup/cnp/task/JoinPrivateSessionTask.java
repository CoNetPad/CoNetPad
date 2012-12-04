package org.ndacm.acmgroup.cnp.task;

/**
 * Task for joining a private session.
 * 
 * @author Josh Tan
 *
 */
public class JoinPrivateSessionTask extends JoinSessionTask {

	protected String sessionPassword;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param username the username for the task
	 * @param sessionName the session name for the task
	 * @param sessionPassword the session password for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public JoinPrivateSessionTask(int userID, String username, String sessionName,
			String sessionPassword, String userAuthToken) {
		super(userID, username, sessionName, userAuthToken);
		this.sessionPassword =sessionPassword;
	}

	/**
	 * Get the session password for the task.
	 * 
	 * @return the session password
	 */
	public String getSessionPassword() {
		return sessionPassword;
	}
}
