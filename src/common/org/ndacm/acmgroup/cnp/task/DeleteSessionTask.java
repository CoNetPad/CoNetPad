package org.ndacm.acmgroup.cnp.task;

public class DeleteSessionTask extends Task {
	
	protected int userID;
	protected int sessionID;
	protected String userAuthToken;
	
	public DeleteSessionTask(int userID, int sessionID, String userAuthToken) {
		this.userID = userID;
		this.sessionID = sessionID;
		this.userAuthToken = userAuthToken;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// make sure userID = sessionLeaderID
		
	}
	public int getUserID() {
		return userID;
	}
	public int getSessionID() {
		return sessionID;
	}
	public String getUserAuthToken() {
		return userAuthToken;
	}


}
