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
		// TODO Auto-generated method stub
		
	}

}
