package org.ndacm.acmgroup.cnp.task;

public class CreatePrivateSessionTask extends CreateSessionTask {

	protected String sessionPassword;

	public CreatePrivateSessionTask(int sessionLeader, String sessionPassword, String userAuthToken) {
		super(sessionLeader, userAuthToken);
		this.sessionPassword = sessionPassword;
	}

	public String getSessionPassword() {
		return sessionPassword;
	}

}
