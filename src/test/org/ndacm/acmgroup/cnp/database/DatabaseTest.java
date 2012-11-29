package org.ndacm.acmgroup.cnp.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel;
import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;
import org.ndacm.acmgroup.cnp.CNPPrivateSession;
import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.CNPSession;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;

/**
 * JUnit test case for Database.
 *
 */
public class DatabaseTest {

	public static CNPServer server;
	public static CNPSession session1;
	public static CNPPrivateSession session2;
	public static Database database;
	public static Account account1;
	public static Account account2;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		server = new CNPServer(".");
		database = new Database(server);
		database.clearTables();
		account1 = database.createAccount("account1", "acc1@test.com", "testme");
		account2 = database.createAccount("account2", "acc2@test.com", "testmemore");
		session1 = database.createSession(103, server);
		session2 = database.createSession(104, server, "canthackthis");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		database.clearTables();
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#createAccount(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws FailedAccountException 
	 */
	@Test
	public final void testCreateAccount() throws FailedAccountException {
		Account account = database.createAccount("john", "john@test.me", "monkey");
		assertEquals(account.getUsername(),"john");
		assertEquals(account.getEmail(),"john@test.me");
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#retrieveAccount(java.lang.String, java.lang.String)}.
	 * @throws FailedAccountException 
	 */
	@Test
	public final void testRetrieveAccount() throws FailedAccountException {
		Account retrieved = database.retrieveAccount("account1", "testme");
		assertEquals(retrieved.getUserID(), account1.getUserID());
		assertEquals(retrieved.getUsername(), account1.getUsername());
		assertEquals(retrieved.getEmail(), account1.getEmail());
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#createSession(int, org.ndacm.acmgroup.cnp.CNPServer)}.
	 * @throws FailedSessionException 
	 */
	@Test
	public final void testCreateSessionIntCNPServer() throws FailedSessionException {
		CNPSession session = database.createSession(100, server);
		assertEquals(session.getSessionLeader(),100);
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#createSession(int, org.ndacm.acmgroup.cnp.CNPServer, java.lang.String)}.
	 * @throws FailedSessionException 
	 */
	@Test
	public final void testCreateSessionIntCNPServerString() throws FailedSessionException {
		CNPPrivateSession session = database.createSession(101, server, "monkey");
		assertEquals(session.getSessionLeader(), 101);
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#retrieveSession(java.lang.String, org.ndacm.acmgroup.cnp.CNPServer)}.
	 * @throws FailedSessionException 
	 */
	@Test
	public final void testRetrieveSessionStringCNPServer() throws FailedSessionException {
		CNPSession sess = database.retrieveSession(session1.getSessionName(), server);
		assertEquals(sess.getClientConnections(),(session1.getClientConnections()));
		assertEquals(sess.getClientIdToName(), session1.getClientIdToName());
		assertEquals(sess.getSessionID(), session1.getSessionID());
		assertEquals(sess.getSessionLeader(), session1.getSessionLeader());
		assertEquals(sess.getSessionName(), session1.getSessionName());
		assertEquals(sess.getSourceFiles(), session1.getSourceFiles());
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#retrieveSession(java.lang.String, org.ndacm.acmgroup.cnp.CNPServer, java.lang.String)}.
	 * @throws FailedSessionException 
	 */
	@Test
	public final void testRetrieveSessionStringCNPServerString() throws FailedSessionException {
		CNPPrivateSession sess = database.retrieveSession(session2.getSessionName(), server, "canthackthis");
		assertEquals(sess.getClientConnections(),(session2.getClientConnections()));
		assertEquals(sess.getClientIdToName(), session2.getClientIdToName());
		assertEquals(sess.getSessionID(), session2.getSessionID());
		assertEquals(sess.getSessionLeader(), session2.getSessionLeader());
		assertEquals(sess.getSessionName(), session2.getSessionName());
		assertEquals(sess.getSourceFiles(), session2.getSourceFiles());
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#sessionIsPrivate(java.lang.String)}.
	 * @throws SQLException 
	 */
	@Test
	public final void testSessionIsPrivate() throws SQLException {
		assertTrue(database.sessionIsPrivate(session2.getSessionName()));
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#createSessionAccount(org.ndacm.acmgroup.cnp.CNPSession, org.ndacm.acmgroup.cnp.Account, org.ndacm.acmgroup.cnp.Account.FilePermissionLevel, org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel)}.
	 * @throws SQLException 
	 */
	@Test
	public final void testCreateSessionAccountCNPSessionAccountFilePermissionLevelChatPermissionLevel() throws SQLException {
		try {
			database.createSessionAccount(session1, account1, FilePermissionLevel.READ, ChatPermissionLevel.MUTE);
		} catch (SQLException ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#deleteSession(org.ndacm.acmgroup.cnp.CNPSession)}.
	 * @throws FailedSessionException 
	 * @throws SQLException 
	 */
	@Test
	public final void testDeleteSession() throws FailedSessionException, SQLException {
		CNPSession sessionToDelete = database.createSession(23, server);
		database.deleteSession(sessionToDelete);
		assert(!database.sessionExists(sessionToDelete.getSessionName()));
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#deleteAccount(org.ndacm.acmgroup.cnp.Account)}.
	 * @throws SQLException 
	 */
	@Test
	public final void testDeleteAccount() throws SQLException {

		try {
			Account accountToDelete = database.createAccount("badman", "go@away.com", "icup");
			database.deleteAccount(accountToDelete);
		} catch (FailedAccountException ex) {
			throw new SQLException("Test failed before could actually test account deletion.");
		}

		try {
			database.retrieveAccount("badman", "icup");
			fail("Delete ws not successful.");
		} catch (FailedAccountException ex) {

		}

	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#createSessionAccount(org.ndacm.acmgroup.cnp.CNPSession, org.ndacm.acmgroup.cnp.Account, java.lang.String, org.ndacm.acmgroup.cnp.Account.FilePermissionLevel, org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel)}.
	 */
	@Test
	public final void testCreateSessionAccountCNPSessionAccountStringFilePermissionLevelChatPermissionLevel() {
		try {
			database.createSessionAccount(session2, account2, "canthackthis", FilePermissionLevel.UNRESTRICTED, ChatPermissionLevel.VOICE);
		} catch (SQLException ex) {
			fail(ex.getMessage());
		} catch (FailedSessionException ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#sessionExists(java.lang.String)}.
	 */
	@Test
	public final void testSessionExists() {
		try {
			assertTrue(database.sessionExists(session1.getSessionName()));
			assertFalse(database.sessionExists("23df2$%$#*3"));
		} catch (Exception ex) {
			fail();
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#getSessionID(java.lang.String)}.
	 */
	@Test
	public final void testGetSessionID() {
		try {
			assertEquals(database.getSessionID(session1.getSessionName()), session1.getSessionID());
		} catch (Exception ex) {
			fail();
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.database.Database#clearTables()}.
	 */
	@Test
	public final void testClearTables() {
		try {
			database.clearTables();
		} catch (SQLException ex) {
			fail("Fails to work.");
		}
		try {
			database.sessionExists(session1.getSessionName());
			database.retrieveAccount(account1.getUsername(), "testme");
			fail("Fails to work correctly.");
		} catch (Exception ex) {

		}
	}

}