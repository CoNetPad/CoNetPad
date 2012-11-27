package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.network.CNPConnection;

public class CreateFileTask extends SessionTask {
	
	protected int userID;
	protected String filename;
	protected SourceType type;
	protected String userAuthToken;
	protected CNPConnection connection;

	public CreateFileTask(int userID, int sessionID, String filename, SourceType type, String userAuthToken) {
		this.userID = userID;
		this.sessionID = sessionID;
		this.filename = filename;
		this.type = type;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		session.executeTask(this);
		
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

	public CNPConnection getConnection() {
		return connection;
	}

	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	

}
