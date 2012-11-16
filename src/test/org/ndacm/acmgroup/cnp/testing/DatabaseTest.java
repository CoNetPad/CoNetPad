/**
 * Database Test Cases
 * This is J-Unit test script that tests the functionality of the database.
 */

package org.ndacm.acmgroup.cnp.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.junit.Test;
import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.CNPSession;
import org.ndacm.acmgroup.cnp.database.Database;
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
					Account result = new Account("John", "Jdoe@gmail.com", 11);
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
					Account result = new Account("John", "Jdoe@gmail.com", 17);
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
				catch(SQLException e)
				{
					fail("SQL Exception thrown");
					e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
//	/**
//	 * This tests the functionality of creating account
//	 */
//	@Test
//	public void testCreateSession()
//	{
//		CNPServer server = new CNPServer("basedir");
//		try{
//			Database db = new Database();
//				try
//				{
//					Account act = db.retrieveAccount("John", "test");
//					CNPSession test = db.createSession(act, "SessionName1", server);
//		
//					CNPSession result = new CNPSession(act, "SessionName1");
//					assertTrue(result.equals(test));
//				}
//				catch(FailedSessionException e)
//				{
//					
//					fail("Failed Session Exception Thrown:  " + e.toString());	
//				}
//				catch(SQLException se)
//				{
//					se.printStackTrace();
//					fail("SQL Exception thrown");
//				}
//				catch(AssertionError e)
//				{
//					fail("Sessions did not equal");
//				}
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//	}
//	/**
//	 * This tests the functionality of creating a private session
//	 */
//	@Test
//	public void testCreatePrivateSession()
//	{
//		try{
//			Database db = new Database();
//			CNPServer server = new CNPServer("basedir");
//				try
//				{
//					String ePass = Database.sha1("test");
//					Account act = db.retrieveAccount("John", "test");
//					CNPSession test = db.createSession(act, "SessionName", server);
//		
//					CNPSession result = new CNPSession(act, "SessionName", ePass);
//					assertTrue(result.equals(test));
//				}
//				catch(FailedSessionException e)
//				{
//					
//					fail("Failed Session Exception Thrown:  " + e.toString());	
//				}
//				catch(SQLException se)
//				{
//					se.printStackTrace();
//					fail("SQL Exception thrown");
//				}
//				catch(AssertionError e)
//				{
//					fail("Sessions did not equal");
//				}
//				catch(NoSuchAlgorithmException e)
//				{
//					fail("No such algorithm exception thrown");
//				}
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//	}
//	/**
//	 * This tests the functionality of retrieving a session
//	 */
//	@Test
//	public void testretrieveSession()
//	{
//		try{
//			Database db = new Database();
//				try
//				{
//					CNPSession test = db.retrieveSession("SessionName1");
//					Account act = db.getAccountById(17);
//					CNPSession result = new CNPSession(act, "SessionName1");
//					assertTrue(result.equals(test));
//					
//				}
//				catch(FailedSessionException e)
//				{
//					
//					fail("Failed Session Exception Thrown:  " + e.toString());	
//				}
//				catch(SQLException se)
//				{
//					se.printStackTrace();
//					fail("SQL Exception thrown");
//				}
//				catch(AssertionError e)
//				{
//					fail("Sessions did not equal");
//				}
//		
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//	}
//	
//	
//	/**
//	 * This tests the functionality of retrieving a session by making sure it wrks
//	 */
//	@Test
//	public void testretrieveSession2()
//	{
//		try{
//			Database db = new Database();
//				try
//				{
//					CNPSession test = db.retrieveSession("SessionName");
//					Account act = db.getAccountById(13);
//					CNPSession result = new CNPSession(act, "SessionName2");
//					assertTrue(!result.equals(test));
//					
//				}
//				catch(FailedSessionException e)
//				{
//					
//					fail("Failed Session Exception Thrown:  " + e.toString());	
//				}
//				catch(SQLException se)
//				{
//					se.printStackTrace();
//					fail("SQL Exception thrown");
//				}
//				catch(AssertionError e)
//				{
//					fail("Sessions equal function broken");
//				}
//		
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//	}
//	
//	/**
//	 * This tests the functionality of retrieving a session
//	 */
//	@Test
//	public void testretrievePrivateSession()
//	{
//		try{
//			Database db = new Database();
//				try
//				{
//					String epass = Database.sha1("test");
//					CNPSession test = db.retrieveSession("SessionName", "test");
//					Account act = db.getAccountById(17);
//					
//					CNPSession result = new CNPSession(act, "SessionName", epass);
//			
//					assertTrue(result.equals(test));
//					
//				}
//				catch(FailedAccountException e)
//				{
//					fail ("Could not get account by ID");
//				}
//				catch(FailedSessionException e)
//				{
//					
//					fail("Failed Session Exception Thrown:  " + e.toString());	
//				}
//				catch(SQLException se)
//				{
//					se.printStackTrace();
//					fail("SQL Exception thrown");
//				}
//				catch(AssertionError e)
//				{
//					fail("Sessions did not equal");
//				}	
//				catch(Exception e)
//				{
//					e.printStackTrace();
//					fail("Some Exception was thrown");
//				}
//		
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//	}
	//END OF CLASS
}
