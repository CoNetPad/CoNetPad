package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

public class ConnectToSessionTask extends Task {
	
	private String username;
	private String password;
	private String sessionName;
	private CNPConnection connection;

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSessionName() {
		return sessionName;
	}
	
	public CNPConnection getConnection() {
		return connection;
	}

}
