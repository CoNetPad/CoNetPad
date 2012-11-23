package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

public class CreateSessionTask extends ServerTask {
	
	protected int sessionLeader;
	protected String userAuthToken;
	private CNPConnection connection;
	
	public CreateSessionTask(int sessionLeader, String userAuthToken) {
		this.sessionLeader = sessionLeader;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		server.executeTask(this);
		
	}

	public int getSessionLeader() {
		return sessionLeader;
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

}
