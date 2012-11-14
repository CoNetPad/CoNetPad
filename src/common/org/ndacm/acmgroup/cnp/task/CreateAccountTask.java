package org.ndacm.acmgroup.cnp.task;


public class CreateAccountTask extends Task {
	
	
	public CreateAccountTask(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	private String username;
	private String email;
	private String password;

	
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
