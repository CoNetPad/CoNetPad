package org.ndacm.acmgroup.cnp.task;

public class DownloadRepoTask extends SessionTask {

	protected int userID;
	protected String userAuthToken;
		
	public DownloadRepoTask(int userID, String userAuthToken) {
		this.userID = userID;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		session.executeTask(this);
		
	}

	public int getUserID() {
		return userID;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}
