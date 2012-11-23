package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;

public class CreateFileTask extends SessionTask {
	
	protected int userID;
	protected String filename;
	protected SourceType type;
	protected String userAuthToken;

	public CreateFileTask(int userID, String filename, SourceType type, String userAuthToken) {
		this.userID = userID;
		this.filename = filename;
		this.type = type;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// make sure userID = sessionLeaderID
		
	}

	public int getUserID() {
		return userID;
	}

	public String getFilename() {
		return filename;
	}

	public SourceType getType() {
		return type;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}
