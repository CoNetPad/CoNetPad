/**
 * Private Session
 * This is a class that inherits from CNP Session.  It is used for private sessions or sessions with password
 * @author Josh Tan
 * @version 2.0
 */
package org.ndacm.acmgroup.cnp;



public class CNPPrivateSession extends CNPSession {
	

	/**
	 * This is the default constructor
	 * @param sessionID			The Database ID of the CNP Session
	 * @param sessionName		The unique name of the CNP Session
	 * @param server			The CNP Server the session belongs to
	 * @param sessionLeader		The databse ID of the leader o the session
	 */
	public CNPPrivateSession(int sessionID, String sessionName, CNPServer server, int sessionLeader) {
		super(sessionID, sessionName, server, sessionLeader);
		
	}

	/**
	 * This checks if two sessions are equal
	 * @param session		The session you wish to test to be equal
	 * @return				True if the two sessions are equal, false otherwise
	 */
	public boolean equals(CNPPrivateSession session)
	{
		//int sessionID, String sessionName, CNPServer server, int sessionLeader
		if( (this.getSessionID() == session.getSessionID()) && (this.getSessionName().equals(session.getSessionName())) && (this.getSessionLeader() == session.getSessionLeader()) )
		{
			return true;
		}
		return false;
	}
}
