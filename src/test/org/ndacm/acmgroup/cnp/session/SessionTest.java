package org.ndacm.acmgroup.cnp.session;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ndacm.acmgroup.cnp.CNPPrivateSession;
import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.CNPSession;

public class SessionTest {

	/**
	 * This tests the functionality of .equals() for a public CNPSession
	 */
	@Test
	public void testPublicEquals() {
		try{
		CNPServer server = new CNPServer("");
		CNPSession session = new CNPSession(1, "SessionTest", server, 2);
		CNPSession session2 = new CNPSession(1, "SessionTest", server, 2);
		assertTrue(session.equals(session2));
		}
		catch(AssertionError e)
		{
			fail("Sessions did not match.");
		}
	}
	/**
	 * This tests the functionality of .equals() for a private CNPSession
	 */
	@Test
	public void testPrivateEquals() {
		try{
		CNPServer server = new CNPServer("");
		CNPPrivateSession session = new CNPPrivateSession(1, "SessionTest", server, 2);
		CNPPrivateSession session2 = new CNPPrivateSession(1, "SessionTest", server, 2);
		assertTrue(session.equals(session2));
		}
		catch(AssertionError e)
		{
			fail("Sessions did not match.");
		}
	}
	
	
	

}
