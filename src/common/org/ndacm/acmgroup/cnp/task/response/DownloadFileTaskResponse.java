package org.ndacm.acmgroup.cnp.task.response;

import java.io.File;

import org.ndacm.acmgroup.cnp.task.DownloadFileTask.DownloadType;

public class DownloadFileTaskResponse extends TaskResponse {

	private File file;
	private DownloadType type;
	private boolean success;
	
	public DownloadFileTaskResponse(File file, DownloadType type,
			boolean success) {
		this.file = file;
		this.type = type;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);
		
	}

	public String getEncodedFile() {
		// TODO
		return null;
	}

	public DownloadType getType() {
		return type;
	}

	public boolean isSuccess() {
		return success;
	}

}
