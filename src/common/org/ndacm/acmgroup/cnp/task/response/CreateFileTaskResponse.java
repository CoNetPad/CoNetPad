package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;

public class CreateFileTaskResponse extends TaskResponse {

	private int fileID;
	private String filename;
	private SourceType type;
	private boolean success;

	public CreateFileTaskResponse(int fileID, String filename, SourceType type,
			boolean success) {
		this.fileID = fileID;
		this.filename = filename;
		this.type = type;
		this.success = success;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

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
}
