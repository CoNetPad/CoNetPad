package org.ndacm.acmgroup.cnp.task;

public class OpenFileTask extends Task {
	
	private int userID;
	private String filename;
	private String userAuthToken;

	public OpenFileTask(int userID, String filename, String userAuthToken) {
		this.userID = userID;
		this.filename = filename;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
