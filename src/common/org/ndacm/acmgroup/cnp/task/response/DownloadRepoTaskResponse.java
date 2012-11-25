package org.ndacm.acmgroup.cnp.task.response;

import java.io.File;

public class DownloadRepoTaskResponse extends TaskResponse {

	private File repo;
	private boolean success;

	public DownloadRepoTaskResponse(File repo, boolean success) {
		this.repo = repo;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);

	}

	public String getEncodedRepo() {
		// TODO
		return null;
	}

	public boolean isSuccess() {
		return success;
	}
}
