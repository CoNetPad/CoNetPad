/**
 * Database Test Cases
 * This is J-Unit test script that tests the functionality of the database.
 */

package org.ndacm.acmgroup.cnp.database;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Test;
import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.CNPPrivateSession;
import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.CNPSession;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;

public class DatabaseTest {

	/**
	 * This tests to see if the new database object works
	 */
	@Test
	public void testDatabase() {
		try{
		Database db = new Database();
		}
		catch(Exception e)
		{
			fail("Not yet implemented");
		}
	}
	/**
	 *  
	 */
	@Test
	public void testVariables()
	{
//		System.out.println("PUBLIC:  " + SessionType.PUBLIC.intValue() );
//		System.out.println("PRIVATE:  " + SessionType.PRIVATE.intValue() );
	//	System.out.println("PRIVATE:  " + (int)SessionType.PRIVATE);
		try{
//			assertEquals(SessionType.PUBLIC.intValue(), 0);
		}
		catch(AssertionError e)
		{
			fail("Public is not equal to 0.");
		}

	}
	
	/**
	 * This tests the functionality of creating a new account
	 */
	@Test
	public void testCreateAccount() {

			try{
			Database db = new Database();
				try
				{
					Account test = db.createAccount("John", "Jdoe@gmail.com", "test");
					Account result = new Account("John", "Jdoe@gmail.com", 0);
					assertTrue(result.equals(test));
				}
				catch(FailedAccountException e)
				{
					fail("Failed Account Exception Thrown:  " + e.toString());
					e.printStackTrace();
	
				}
				catch(AssertionError e)
				{
					fail("Accouts did not equal");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	/**
	 * This tests the functionality of retrieving an account.
	 */
	@Test
	public void testRetrieveAccount()
	{
		try{
			Database db = new Database();
				try
				{
					Account test = db.retrieveAccount("John", "test");
					Account result = new Account("John", "Jdoe@gmail.com", 262);
					assertTrue(result.equals(test));
				}
				catch(FailedAccountException e)
				{
					e.printStackTrace();
					fail("Failed Account Exception Thrown:  " + e.toString());

					
				}
				catch(AssertionError e)
				{
					fail("Accouts did not equal");
				}
				catch(Exception e)
				{
					fail("Some Random Error");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	/**
	 * This tests the functionality of creating a new Session
	 */
	@Test
	public void testCreateSession() {

			try{
			Database db = new Database();
				try
				{
					CNPServer server = new CNPServer(""); 
					CNPSession test = db.createSession(32, server) ;
					//CNPSession result = new CNPSession(int sessionID, String sessionName, CNPServer server, int sessionLeader);
					//assertTrue(result.equals(test));
				}
				catch(FailedAccountException e)
				{
					fail("Failed Account Exception Thrown:  " + e.toString());
					e.printStackTrace();
	
				}
				catch(AssertionError e)
				{
					fail("Accouts did not equal");
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					fail("SQL Exception Thrown");
				
					
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	/**
	 * This tests the functionality of creating a new Session
	 */
	@Test
	public void testRetrieveSession() {

			try{
			Database db = new Database();
				try
				{
					CNPServer server = new CNPServer(""); 
					CNPSession test = db.retrieveSession("jpfam", server);
					CNPSession result = new CNPSession(1, "jpfam",  server, 32);
					assertTrue(result.equals(test));

				}
				catch(FailedSessionException e)
				{
					fail("Failed Session Exception Thrown:  " + e.toString());
					e.printStackTrace();
				}
				catch(AssertionError e)
				{
					fail("Sessions did not equal");
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					fail("SQL Exception Thrown");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					fail("Some Exception was thrown");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	/**
	 * This tests the functionality of creating a new Private Session
	 */
	@Test
	public void testCreatePrivateSession() {

			try{
			Database db = new Database();
				try
				{
					CNPServer server = new CNPServer(""); 
					CNPPrivateSession test = db.createSession(32, server, "test"); 
					//CNPSession result = new CNPSession(int sessionID, String sessionName, CNPServer server, int sessionLeader);
					//assertTrue(result.equals(test));
				}
				catch(FailedAccountException e)
				{
					fail("Failed Account Exception Thrown:  " + e.toString());
					e.printStackTrace();
	
				}
				catch(AssertionError e)
				{
					fail("Accouts did not equal");
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					fail("SQL Exception Thrown");
				
					
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	/**
	 * This tests the functionality of creating a new Private Session
	 */
	@Test
	public void testRetrievePrivateSession() {

			try{
			Database db = new Database();
				try
				{
					CNPServer server = new CNPServer(""); 
					CNPPrivateSession test = db.retrieveSession("cdeef", server, "test"); 
					//CNPSession result = new CNPSession(int sessionID, String sessionName, CNPServer server, int sessionLeader);
					//assertTrue(result.equals(test));
				}
				catch(FailedAccountException e)
				{
					fail("Failed Account Exception Thrown:  " + e.toString());
					e.printStackTrace();
	
				}
				catch(AssertionError e)
				{
					fail("Accouts did not equal");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	/**
	 * This tests the functionality of creating a is Session Private
	 */
	@Test
	public void testSessionIsPrivate() {

			try{
			Database db = new Database();
				try
				{
					CNPServer server = new CNPServer(""); 
					boolean test = db.sessionIsPrivate("cdeef");
					boolean result = true;
					//CNPSession result = new CNPSession(int sessionID, String sessionName, CNPServer server, int sessionLeader);
					//assertTrue(result.equals(test));
					assertTrue(test);
				}
				catch(SQLException e)
				{
					System.out.println("Error : " + e.toString());
					fail("ERrror with SQL");
				}
				catch(AssertionError e)
				{
					fail("Session is not private");
				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	
	/**
	 * This tests the functionality of SessionAccount
	 */
	@Test
	public void testCreateSessionAccount() {

			try{
			Database db = new Database();
				try
				{
					CNPServer server = new CNPServer(""); 
					CNPSession session = new CNPSession(1, "beer", server,3);
					Account act = new Account("John", "john@gmail.com", 32);
					boolean test = db.createSessionAccount(session, act,Account.FilePermissionLevel.READ_WRITE, Account.ChatPermissionLevel.VOICE); 
					boolean result = true;
					//CNPSession result = new CNPSession(int sessionID, String sessionName, CNPServer server, int sessionLeader);
					//assertTrue(result.equals(test));
					assertTrue(test);
				}
				catch(SQLException e)
				{
					System.out.println("Error : " + e.toString());
					fail("ERrror with SQL");
				}
				catch(AssertionError e)
				{
					fail("Could not create SessionAccount");
				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	
	/**
	 * This tests the functionality of Deleting an account
	 */
	@Test
	public void testDeleteAccount() {
		try{
			//Database db = new Database();
			//Account act = new Account()
			//db.deleteAccount(act);
			
		}
		catch(Exception e)
		{
			
		}
	}
	
}
