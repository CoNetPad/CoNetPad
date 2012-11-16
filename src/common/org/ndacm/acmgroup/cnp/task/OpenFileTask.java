package org.ndacm.acmgroup.cnp.task;

public class OpenFileTask extends Task {
	
	protected int userID;
	protected int fileID;
	protected String userAuthToken;

	public OpenFileTask(int userID, int fileID, String userAuthToken) {
		this.userID = userID;
		this.fileID = fileID;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// register use as listener of ServerSourceFile
		
	}

	public int getUserID() {
		return userID;
	}

	public int getFileID() {
		return fileID;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}
