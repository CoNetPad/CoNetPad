package org.ndacm.acmgroup.cnp.task;

public class CloseFileTask extends TaskRequest {

	protected int userID;
	protected String filename;
	protected String userAuthToken;
	
	public CloseFileTask(int userID, String filename, String userAuthToken) {
		this.userID = userID;
		this.filename = filename;
		this.userAuthToken = userAuthToken;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// make sure userID corresponds to sessionLeaderID
		
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
