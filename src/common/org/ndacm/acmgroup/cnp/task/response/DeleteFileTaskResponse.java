package org.ndacm.acmgroup.cnp.task.response;

public class DeleteFileTaskResponse extends TaskResponse {
	
	private String filename;
	private boolean success;
	
	public DeleteFileTaskResponse(String filename, boolean success) {
		this.filename = filename;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);
		
	}

	public String getFilename() {
		return filename;
	}

	public boolean isSuccess() {
		return success;
	}
}
