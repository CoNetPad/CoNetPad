package org.ndacm.acmgroup.cnp.task;

public class JoinPrivateSessionTask extends JoinSessionTask {

	protected String sessionPassword;
	
	public JoinPrivateSessionTask(int userID, String username, String sessionName,
			String sessionPassword, String userAuthToken) {
		super(userID, username, sessionName, userAuthToken);
		this.sessionPassword =sessionPassword;
	}

	public String getSessionPassword() {
		return sessionPassword;
	}
}
