package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for committing to the session Git repository.
 *
 * @author Josh Tan
 *
 */
public class CommitTaskResponse extends TaskResponse {

	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param success the success status for the task
	 */
	public CommitTaskResponse(boolean success) {
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
	 * Get the success status for the task.
	 * @return the success status
	 */
	public boolean isSuccess(){
		return this.success;
	}
}
