package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for deleting a file.
 * 
 * @author Josh Tan
 *
 */
public class DeleteFileTaskResponse extends TaskResponse {

	private String filename;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param filename the filename for the task
	 * @param success the success status of the task
	 */
	public DeleteFileTaskResponse(String filename, boolean success) {
		this.filename = filename;
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
	 * Get the filename for the task.
	 * 
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Get the success status for the task.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
	}
}
