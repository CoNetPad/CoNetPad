package org.ndacm.acmgroup.cnp.task;

/**
 * A task that should be executed by a session. Contains a reference to a session
 *  so that this task can be submitted to the CNPSessoion's ExecutorService.
 *
 */
public abstract class SessionTask extends Task {

	protected SessionTaskExecutor session;
	protected int sessionID;

	/**
	 * Get the session ID for the task.
	 * 
	 * @return the session ID
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * Set the session ID for the task.
	 * 
	 * @param sessionID the session ID
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Get the task executor for the task.
	 * 
	 * @return the task executor
	 */
	public SessionTaskExecutor getSession() {
		return session;
	}

	/**
	 * Set the task executor the task.
	 * 
	 * @param session the task executor
	 */
	public void setSession(SessionTaskExecutor session) {
		this.session = session;
	}


}