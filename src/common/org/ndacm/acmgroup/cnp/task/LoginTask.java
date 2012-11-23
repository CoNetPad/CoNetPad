package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

public class LoginTask extends TaskRequest {
	
	protected String username;
	protected String password;
	private CNPConnection connection;

	public LoginTask(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		server.executeTask(this);
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}
	
	public CNPConnection getConnection() {
		return connection;
	}

}
