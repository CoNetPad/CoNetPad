package org.ndacm.acmgroup.cnp.task.response;

public class OpenFileTaskResponse extends TaskResponse {

	private String filename;
	private int fileID;
	private String fileContent;
	private boolean success;

	public OpenFileTaskResponse(int fileID, String filename, String fileContent, boolean success) {
		this.fileID = fileID;
		this.filename = filename;
		this.fileContent = fileContent;
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

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	
	
	
	

}
