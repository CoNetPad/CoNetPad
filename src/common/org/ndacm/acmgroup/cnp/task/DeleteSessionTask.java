package org.ndacm.acmgroup.cnp.task;

public class DeleteSessionTask extends Task {
	
	private int userID;
	private int sessionID;
	private String userAuthToken;
	
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


}
