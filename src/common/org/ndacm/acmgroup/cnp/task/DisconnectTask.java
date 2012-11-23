package org.ndacm.acmgroup.cnp.task;

public class DisconnectTask extends TaskRequest {
	
	protected int userID;
	protected String userAuthToken;

	public DisconnectTask(int userID, String userAuthToken) {
		this.userID = userID;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public int getUserID() {
		return userID;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}
