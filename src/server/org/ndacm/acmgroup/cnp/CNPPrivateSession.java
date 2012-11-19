package org.ndacm.acmgroup.cnp;



public class CNPPrivateSession extends CNPSession {
	
	private String sessionPassword; // hash
	private String sessionSalt;
	
	public CNPPrivateSession(int sessionID, String sessionName, CNPServer server, int sessionLeader) {
		super(sessionID, sessionName, server, sessionLeader);
		
	}

	public boolean passwordIsCorrect(String attempt) {
		// TODO implement
		return false;
	}
	
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
