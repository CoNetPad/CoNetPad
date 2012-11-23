package org.ndacm.acmgroup.cnp.task;

public class DeleteFileTask extends SessionTask {
	
	protected int userID;
	protected String filename;
	protected String userAuthToken;
	
	public DeleteFileTask(int userID, String filename, String userAuthToken) {
		this.userID = userID;
		this.filename = filename;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public int getUserID() {
		return userID;
	}

	public String getFilename() {
		return filename;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}
