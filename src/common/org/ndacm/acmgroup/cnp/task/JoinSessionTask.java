package org.ndacm.acmgroup.cnp.task;


public class JoinSessionTask extends Task {
	
	private int userID;
	private String sessionName;
	private String userAuthToken;

	public JoinSessionTask(int userID, String sessionName, String userAuthToken) {
		this.userID = userID;
		this.sessionName = sessionName;
		this.userAuthToken = userAuthToken;
	}

	public String getSessionName() {
		return sessionName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
