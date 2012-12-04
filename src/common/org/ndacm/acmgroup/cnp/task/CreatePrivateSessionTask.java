package org.ndacm.acmgroup.cnp.task;

/**
 * Task for creating a private session on the server.
 * 
 * @author Josh Tan
 *
 */
public class CreatePrivateSessionTask extends CreateSessionTask {

	protected String sessionPassword;

	/**
	 * Default constructor.
	 * 
	 * @param sessionLeader the ID of the session leader for the task
	 * @param sessionPassword the password for the session
	 * @param userAuthToken the user authentication token for the task
	 */
	public CreatePrivateSessionTask(int sessionLeader, String sessionPassword, String userAuthToken) {
		super(sessionLeader, userAuthToken);
		this.sessionPassword = sessionPassword;
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
