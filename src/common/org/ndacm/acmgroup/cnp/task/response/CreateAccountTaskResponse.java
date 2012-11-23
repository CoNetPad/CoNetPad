package org.ndacm.acmgroup.cnp.task.response;

public class CreateAccountTaskResponse extends TaskResponse {
	
	private int userID;
	private boolean success;
	
	public CreateAccountTaskResponse(int userID, boolean success) {
		this.userID = userID;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);
		
	}

	public int getUserID() {
		return userID;
	}

	public boolean isSuccess() {
		return success;
	}

}
