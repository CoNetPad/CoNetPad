package org.ndacm.acmgroup.cnp.task;

public class DownloadRepoTask extends Task {

	protected int userID;
	protected String userAuthToken;
		
	public DownloadRepoTask(int userID, String userAuthToken) {
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
