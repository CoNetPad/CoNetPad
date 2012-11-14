package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;

public class CreateFileTask extends Task {
	
	private int userID;
	private String filename;
	private SourceType type;
	private String userAuthToken;

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

}
