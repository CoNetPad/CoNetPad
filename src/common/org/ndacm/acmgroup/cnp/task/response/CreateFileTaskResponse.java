package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;

public class CreateFileTaskResponse extends TaskResponse {

	private int fileID;
	protected int userID;
	private String filename;
	private SourceType type;
	private boolean success;

	public CreateFileTaskResponse(int fileID, int userID, String filename, SourceType type,
			boolean success) {
		this.fileID = fileID;
		this.userID = userID;
		this.filename = filename;
		this.type = type;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);

	}

	public int getFileID() {
		return fileID;
	}

	public String getFilename() {
		return filename;
	}

	public SourceType getType() {
		return type;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
