package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;



public class CreateAccountTask extends ServerTask {

	protected String username;
	protected String email;
	protected String password;
	private CNPConnection connection = null;

	public CreateAccountTask(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Override
	public void run() {
		server.executeTask(this);
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public CNPConnection getConnection() {
		return connection;
	}
	
	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}


}
