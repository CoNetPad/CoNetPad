package org.ndacm.acmgroup.cnp.task.response;

public class CreateSessionTaskResponse extends TaskResponse {

	private int sessionID;
	private boolean success;
	private String sessionName;

	public CreateSessionTaskResponse(int sessionID, boolean success,
			String sessionName) {
		this.sessionID = sessionID;
		this.success = success;
		this.sessionName = sessionName;
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

	public String getSessionName() {
		return sessionName;
	}
}
