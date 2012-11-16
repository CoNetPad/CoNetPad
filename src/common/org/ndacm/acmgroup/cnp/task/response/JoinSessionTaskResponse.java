package org.ndacm.acmgroup.cnp.task.response;

public class JoinSessionTaskResponse extends TaskResponse {
	
	private String sessionName;
	private boolean success;

	public JoinSessionTaskResponse(String sessionName, boolean success) {
		this.sessionName = sessionName;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);
		
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
