package org.ndacm.acmgroup.cnp.task;



public class JoinSessionTask extends ServerTask {
	
	protected int userID;
	protected String sessionName;
	protected String userAuthToken;

	public JoinSessionTask(int userID, String sessionName, String userAuthToken) {
		this.userID = userID;
		this.sessionName = sessionName;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
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


}
