package org.ndacm.acmgroup.cnp.task;

/**
 * Task for disconnecting from a session.
 *
 */
public class LeaveSessionTask extends ServerTask {
	
	protected int userID;
	private String username;
	private int sessionID;
	protected String userAuthToken;

	public LeaveSessionTask(int userID, String username, int sessionID, String userAuthToken) {
		this.userID = userID;
		this.username = username;
		this.sessionID = sessionID;
		this.userAuthToken = userAuthToken;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	

	
}
