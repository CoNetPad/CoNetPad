package org.ndacm.acmgroup.cnp.task.response;

public class CreateSessionTaskResponse extends TaskResponse {
	
	private int sessionID;
	private boolean success;

	public CreateSessionTaskResponse(int sessionID, boolean success) {
		this.sessionID = sessionID;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);
		
	}

	public int getSessionID() {
		return sessionID;
	}

	public boolean isSuccess() {
		return success;
	}

}
