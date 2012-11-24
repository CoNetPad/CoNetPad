package org.ndacm.acmgroup.cnp.task;



/**
 * A task that should be executed by a session. Contains a reference to a session
 *  so that this task can be submitted to the CNPSessoion's ExecutorService.
 *
 */
public abstract class SessionTask extends Task {
	
	protected SessionTaskExecutor session;
	protected int sessionID;

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public SessionTaskExecutor getSession() {
		return session;
	}

	public void setSession(SessionTaskExecutor session) {
		this.session = session;
	}
	

}