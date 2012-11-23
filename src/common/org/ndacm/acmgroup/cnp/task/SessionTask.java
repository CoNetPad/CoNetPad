package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.CNPSession;


/**
 * A task that should be executed by a session. Contains a reference to a session
 *  so that this task can be submitted to the CNPSessoion's ExecutorService.
 *
 */
public abstract class SessionTask extends Task {
	
	protected CNPSession session;
	protected int sessionID;

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public CNPSession getSession() {
		return session;
	}

	public void setSession(CNPSession session) {
		this.session = session;
	}
	

}