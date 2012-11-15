package org.ndacm.acmgroup.cnp.task;

public class CreateSessionTask extends Task {
	
	protected int sessionLeader;
	protected String userAuthToken;
	
	public CreateSessionTask(int sessionLeader, String userAuthToken) {
		this.sessionLeader = sessionLeader;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public int getSessionLeader() {
		return sessionLeader;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}
