package org.ndacm.acmgroup.cnp.task.response;

public class DeleteSessionTaskResponse extends TaskResponse {
	
	private String sessionName;
	private boolean success;
	
	public DeleteSessionTaskResponse(String sessionName, boolean success) {
		this.sessionName = sessionName;
		this.success = success;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
