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
		client.executeTask(this);
		
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	
}
