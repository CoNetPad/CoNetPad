package org.ndacm.acmgroup.cnp.task;

public class CreatePrivateSessionTask extends CreateSessionTask {
	
	public CreatePrivateSessionTask(int sessionLeader, String sessionPassword, String userAuthToken) {
		super(sessionLeader, userAuthToken);
		this.sessionPassword = sessionPassword;
	}

	private String sessionPassword;
	
	public String getSessionPassword() {
		return sessionPassword;
	}

}
