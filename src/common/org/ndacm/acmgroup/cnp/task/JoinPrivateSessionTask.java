package org.ndacm.acmgroup.cnp.task;

public class JoinPrivateSessionTask extends JoinSessionTask {

	protected String sessionPassword;
	
	public JoinPrivateSessionTask(int userID, String sessionName,
			String sessionPassword, String userAuthToken) {
		super(userID, sessionName, userAuthToken);
		this.sessionPassword =sessionPassword;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public String getSessionPassword() {
		return sessionPassword;
	}
}
