package org.ndacm.acmgroup.cnp.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ndacm.acmgroup.cnp.Account;

/**
 * JUnit test for Account.
 * 
 * @author Justin
 *
 */
public class AccountTest {

	public static Account account1;
	public static Account account2;
	public static Account account3;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		account1 = new Account("account1", "acc1@test.com", 1);
		account2 = new Account("account2", "acc2@test.com", 2);
		account3 = new Account();
	}
	
	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.Account#Account()}.
	 */
	@Test
	public final void testAccount() {
		assertNotNull(account3);
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.Account#Account(java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public final void testAccountStringStringInt() {
		assertNotNull(account1);
		
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.Account#getUsername()}.
	 */
	@Test
	public final void testGetUsername() {
		assertEquals(account1.getUsername(), "account1");

	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.Account#getEmail()}.
	 */
	@Test
	public final void testGetEmail() {
		assertEquals(account1.getEmail(), "acc1@test.com");
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.Account#getUserID()}.
	 */
	@Test
	public final void testGetUserID() {
		assertEquals(account1.getUserID(), 1);
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.Account#setUserID(int)}.
	 */
	@Test
	public final void testSetUserID() {
		account2.setUserID(3);
		assertEquals(account2.getUserID(), 3);
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.Account#setUserName(java.lang.String)}.
	 */
	@Test
	public final void testSetUserName() {
		account2.setUserName("test");
		assertEquals(account2.getUsername(), "test");
		
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.Account#equals(org.ndacm.acmgroup.cnp.Account)}.
	 */
	@Test
	public final void testEqualsAccount() {
		assertTrue(!account1.equals(account2) );
		assertTrue(account1.equals(account1));
	}

}
