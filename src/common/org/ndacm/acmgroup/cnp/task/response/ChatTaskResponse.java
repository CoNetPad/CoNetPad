package org.ndacm.acmgroup.cnp.task.response;

public class ChatTaskResponse extends TaskResponse {
	
	private String username;
	private String message;
	
	public ChatTaskResponse(String username, String message) {
		this.username = username;
		this.message = message;
	}
	
	@Override
	public void run() {
		client.executeTask(this);
		
	}

	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}

}
