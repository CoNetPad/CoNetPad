package org.ndacm.acmgroup.cnp.task;

public class LoginTask extends Task {
	
	private String username;
	private String password;

	public LoginTask(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// LoginTaskResult will return auth token
		
	}

}
