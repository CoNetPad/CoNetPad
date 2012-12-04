package org.ndacm.acmgroup.cnp.task.response;

import java.io.File;

/**
 * Task response for downloading a Git repository.
 * 
 * @author Josh Tan
 *
 */
public class DownloadRepoTaskResponse extends TaskResponse {

	private File repo;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param repo the GIt repository downloaded
	 * @param success the success of the task
	 */
	public DownloadRepoTaskResponse(File repo, boolean success) {
		this.repo = repo;
		this.success = success;
	} 

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);
	}

	/**
	 * Get the encoded repository.
	 * 
	 * @return the encoded repository
	 */
	public String getEncodedRepo() {
		// TODO
		return null;
	}

	/**
	 * Get the success status of the task.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
	}
}
