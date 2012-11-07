package org.ndacm.acmgroup.cnp.server;

public class CNPPrivateSession extends CNPSession {
	
	private String sessionPassword; // hash

	public boolean passwordIsCorrect(String attempt) {
		// TODO implement
		return false;
	}
}
