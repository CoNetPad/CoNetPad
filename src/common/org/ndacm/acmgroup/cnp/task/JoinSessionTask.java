package org.ndacm.acmgroup.cnp.task;



public class JoinSessionTask extends ServerTask {
	
	protected int userID;
	private String username;
	protected String sessionName;
	protected String userAuthToken;

	public JoinSessionTask(int userID, String username, String sessionName, String userAuthToken) {
		this.userID = userID;
		this.username = username;
		this.sessionName = sessionName;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		server.executeTask(this);
		
	}
	
	public String getSessionName() {
		return sessionName;
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
	
	


}
