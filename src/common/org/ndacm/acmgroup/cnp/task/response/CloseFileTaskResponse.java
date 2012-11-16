package org.ndacm.acmgroup.cnp.task.response;

public class CloseFileTaskResponse extends TaskResponse {
	
	private String filename;
	private boolean success;
	
	public CloseFileTaskResponse(String filename, boolean success) {
		this.filename = filename;
		this.success = success;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public String getFilename() {
		return filename;
	}

	public boolean isSuccess() {
		return success;
	}

}
