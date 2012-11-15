package org.ndacm.acmgroup.cnp.task;

public class LoginTask extends Task {
	
	protected String username;
	protected String password;

	public LoginTask(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// LoginTaskResult will return auth token
		
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
