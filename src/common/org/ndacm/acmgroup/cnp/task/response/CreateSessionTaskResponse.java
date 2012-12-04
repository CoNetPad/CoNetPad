package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for creating a session.
 * 
 * @author Josh Tan
 *
 */
public class CreateSessionTaskResponse extends TaskResponse {

	private int sessionID;
	private String sessionName;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param sessionID the session ID for the task
	 * @param sessionName the session name for the task
	 * @param success the success status for the task
	 */
	public CreateSessionTaskResponse(int sessionID, String sessionName,
			boolean success) {
		this.sessionID = sessionID;
		this.sessionName = sessionName;
		this.success = success;
		this.sessionName = sessionName;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);
	}

	/**
	 * Get the session ID for the task.
	 * 
	 * @return the session ID
	 */
	public int getSessionID() {
		return sessionID;
	}

	/** 
	 * Get the success status for the task.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
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
	 * Set the session name for the task.
	 * 
	 * @param sessionName the session name
	 */
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

}