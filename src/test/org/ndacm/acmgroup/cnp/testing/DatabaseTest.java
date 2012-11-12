/**
 * Database Test Cases
 * This is J-Unit test script that tests the funcionality of the database.
 */

package org.ndacm.acmgroup.cnp.testing;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;

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
					fail("Failed Account Exception Thrown");
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
	 * This tests the funcionality of retrieving an account.
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
					fail("Failed Account Exception Thrown");

					
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

}
