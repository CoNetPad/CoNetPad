package org.ndacm.acmgroup.cnp.task.response;

public class LoginTaskResponse extends TaskResponse {
	
	private int userID;
	private boolean success;
	private String userAuthToken;

	public LoginTaskResponse(int userID, boolean success, String userAuthToken) {
		this.userID = userID;
		this.success = success;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public int getUserID() {
		return userID;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}
