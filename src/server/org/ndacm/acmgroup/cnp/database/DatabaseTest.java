package org.ndacm.acmgroup.cnp.database;

import junit.framework.TestCase;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.CNPSession;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;

public class DatabaseTest extends TestCase {
	
	public static CNPServer server;
	public static Database database;
	public static Account account1;
	
	protected static void setUpBeforeClass() throws Exception {
		server = new CNPServer("");
		database = new Database(server);
		account1 = database.createAccount("account1", "acc1@test.com", "testme");
	}

	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateAccount() throws FailedAccountException {
		Account account = database.createAccount("john", "john@test.me", "monkey");
		assertEquals(account.getUsername(),"john");
		assertEquals(account.getEmail(),"john@test.me");
	}

	public void testRetrieveAccount() throws FailedAccountException {
		Account retrieved = database.retrieveAccount("account1", "testme");
		assertEquals(retrieved, account1);
	}

	public void testCreateSessionIntCNPServer() throws FailedSessionException {
		CNPSession session = database.createSession(100, server);
		assertEquals(session.getSessionLeader(),100);
	}

	public void testCreateSessionIntCNPServerString() {
		fail("Not yet implemented");
	}

	public void testRetrieveSessionStringCNPServer() {
		fail("Not yet implemented");
	}

	public void testRetrieveSessionStringCNPServerString() {
		fail("Not yet implemented");
	}

	public void testSessionIsPrivate() {
		fail("Not yet implemented");
	}

	public void testCreateSessionAccountCNPSessionAccountFilePermissionLevelChatPermissionLevel() {
		fail("Not yet implemented");
	}

	public void testDeleteSession() {
		fail("Not yet implemented");
	}

	public void testDeleteAccount() {
		fail("Not yet implemented");
	}

	public void testCreateSessionAccountCNPSessionAccountStringFilePermissionLevelChatPermissionLevel() {
		fail("Not yet implemented");
	}

	public void testSessionExists() {
		fail("Not yet implemented");
	}

	public void testGetSessionID() {
		fail("Not yet implemented");
	}

}
