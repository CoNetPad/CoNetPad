/**
 * Database Test Cases
 * This is J-Unit test script that tests the functionality of the database.
 */

package org.ndacm.acmgroup.cnp.testing;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.junit.Test;
import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;
import org.ndacm.acmgroup.cnp.server.CNPSession;
import org.ndacm.acmgroup.cnp.server.CNPSession.SessionType;

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
					Account result = new Account("John", "Jdoe@gmail.com", 13);
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
	
	/**
	 * This tests the functionality of creating account
	 */
	@Test
	public void testCreateSession()
	{
		try{
			Database db = new Database();
				try
				{
					Account act = db.retrieveAccount("John", "test");
					CNPSession test = db.createSession(act, "SessionName", "testChannel", "somePath");
		
					CNPSession result = new CNPSession(act, "SessionName", SessionType.PUBLIC, "testChannel", "somePath");
					assertTrue(result.equals(test));
				}
				catch(FailedSessionException e)
				{
					
					fail("Failed Session Exception Thrown:  " + e.toString());	
				}
				catch(SQLException se)
				{
					se.printStackTrace();
					fail("SQL Exception thrown");
				}
				catch(AssertionError e)
				{
					fail("Sessions did not equal");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	/**
	 * This tests the functionality of creating a private session
	 */
	@Test
	public void testCreatePrivateSession()
	{
		try{
			Database db = new Database();
				try
				{
					String ePass = Database.sha1("test");
					Account act = db.retrieveAccount("John", "test");
					CNPSession test = db.createSession(act, "SessionName", "testChannel", "somePath", "test");
		
					CNPSession result = new CNPSession(act, "SessionName", SessionType.PRIVATE, "testChannel", "somePath", ePass);
					assertTrue(result.equals(test));
				}
				catch(FailedSessionException e)
				{
					
					fail("Failed Session Exception Thrown:  " + e.toString());	
				}
				catch(SQLException se)
				{
					se.printStackTrace();
					fail("SQL Exception thrown");
				}
				catch(AssertionError e)
				{
					fail("Sessions did not equal");
				}
				catch(NoSuchAlgorithmException e)
				{
					fail("No such algorithm exception thrown");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	//END OF CLASS
}
