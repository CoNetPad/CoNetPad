package org.ndacm.acmgroup.cnp.task;

public class DisconnectTask extends Task {
	
	private int userID;
	private String userAuthToken;

	public DisconnectTask(int userID, String userAuthToken) {
		this.userID = userID;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
