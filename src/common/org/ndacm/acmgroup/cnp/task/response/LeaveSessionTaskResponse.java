package org.ndacm.acmgroup.cnp.task.response;

public class LeaveSessionTaskResponse extends TaskResponse {
	
	private int userID;
	private String username;
	private boolean success;

	public LeaveSessionTaskResponse(int userID, String username, boolean success) {
		this.userID = userID;
		this.username = username;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);
		
	}

	public boolean isSuccess() {
		return success;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}
