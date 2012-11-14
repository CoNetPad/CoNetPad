package org.ndacm.acmgroup.cnp.task.response;

public class CloseFileResponse extends TaskResponse {
	
	private String filename;
	private boolean success;
	
	public CloseFileResponse(String filename, boolean success) {
		this.filename = filename;
		this.success = success;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
