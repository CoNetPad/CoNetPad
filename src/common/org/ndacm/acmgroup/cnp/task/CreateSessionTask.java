package org.ndacm.acmgroup.cnp.task;

public class CreateSessionTask extends Task {
	
	private int sessionLeader;
	private String userAuthToken;
	
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

}
