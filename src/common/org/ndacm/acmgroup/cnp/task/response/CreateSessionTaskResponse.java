package org.ndacm.acmgroup.cnp.task.response;

public class CreateSessionTaskResponse extends TaskResponse {

	private int sessionID;
	private String sessionName;
	private boolean success;

	public CreateSessionTaskResponse(int sessionID, String sessionName,
			boolean success) {
		this.sessionID = sessionID;
		this.sessionName = sessionName;
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

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

}