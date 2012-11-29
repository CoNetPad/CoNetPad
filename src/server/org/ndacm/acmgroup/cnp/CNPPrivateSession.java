package org.ndacm.acmgroup.cnp;


/**
 * A private CoNetPad session.
 * This is a class that inherits from CNP Session.  It is used for private 
 * sessions (sessions with password).
 */
public class CNPPrivateSession extends CNPSession {
	
	/**
	 * Default constructor.
	 * @param sessionID			The database ID of the CNP Session
	 * @param sessionName		The unique name of the CNP Session
	 * @param server			The CNP Server the session belongs to
	 * @param sessionLeader		The database ID of the leader of the session
	 */
	public CNPPrivateSession(int sessionID, String sessionName, CNPServer server, int sessionLeader) {
		super(sessionID, sessionName, server, sessionLeader);
		
	}

	/**
	 * Checks if two sessions are equal.
	 * @param session		The session you wish to test against for equality.
	 * @return				True if the two sessions are equal; false otherwise.
	 */
	public boolean equals(CNPPrivateSession session)
	{
		if( (this.getSessionID() == session.getSessionID()) && 
				(this.getSessionName().equals(session.getSessionName())) && 
				(this.getSessionLeader() == session.getSessionLeader()) )
		{
			return true;
		}
		return false;
	}
}
