package org.ndacm.acmgroup.cnp.task;


public class LoginTask extends ServerTask {
	
	protected String username;
	protected String password;

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

}
