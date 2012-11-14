package org.ndacm.acmgroup.cnp.task;

public class DeleteFileTask extends Task {
	
	private int userID;
	private String filename;
	private String userAuthToken;
	
	public DeleteFileTask(int userID, String filename, String userAuthToken) {
		this.userID = userID;
		this.filename = filename;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
