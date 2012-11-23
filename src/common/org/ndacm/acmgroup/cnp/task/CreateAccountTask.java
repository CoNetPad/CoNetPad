package org.ndacm.acmgroup.cnp.task;

public class CreateAccountTask extends TaskRequest {

	protected String username;
	protected String email;
	protected String password;

	public CreateAccountTask(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

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

}
