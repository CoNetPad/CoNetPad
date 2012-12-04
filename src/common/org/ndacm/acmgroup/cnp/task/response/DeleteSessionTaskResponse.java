package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for deleting a session.
 * 
 * @author Josh Tan
 *
 */
public class DeleteSessionTaskResponse extends TaskResponse {

	private String sessionName;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param sessionName the session name for the task
	 * @param success the success status for the task
	 */
	public DeleteSessionTaskResponse(String sessionName, boolean success) {
		this.sessionName = sessionName;
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
	 * Get the session name for the task.
	 * 
	 * @return the session name
	 */
	public String getSessionName() {
		return sessionName;
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
