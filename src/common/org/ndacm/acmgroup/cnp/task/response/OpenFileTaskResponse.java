package org.ndacm.acmgroup.cnp.task.response;

public class OpenFileTaskResponse extends TaskResponse {
	
	private String filename;
	private boolean success;

	public OpenFileTaskResponse(String filename, boolean success) {
		this.filename = filename;
		this.success = success;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
