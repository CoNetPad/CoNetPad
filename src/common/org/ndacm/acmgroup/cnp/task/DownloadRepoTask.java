package org.ndacm.acmgroup.cnp.task;

public class DownloadRepoTask extends Task {

	private int userID;
	private String userAuthToken;
		
	public DownloadRepoTask(int userID, String userAuthToken) {
		this.userID = userID;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
