package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

public class CommitTask extends ServerTask {

	protected int userID;
	protected String userAuthToken;
	private int sessionID;
	private String sessionName;
	private String message;
	private CNPConnection connection;
	
	public CommitTask(int userID, String userAuthToken, int sessionID, String sessionName, String message) {
		this.userID = userID;
		this.userAuthToken = userAuthToken;
		this.sessionID = sessionID;
		this.sessionName = sessionName;
		this.message = message;
	}

	@Override
	public void run() {
		server.executeTask(this);
		
	}

	public int getUserID() {
		return userID;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CNPConnection getConnection() {
		return connection;
	}

	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}
	
	
	
	

}
