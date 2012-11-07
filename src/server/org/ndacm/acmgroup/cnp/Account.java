/**
 * Class:  Account
 * Descrption:  This is a java object class to deal with user-accounts.
 */
package org.ndacm.acmgroup.cnp;

public class Account {
	
	private String username;		//Username of account
	private String email;			//Email of Accout
	
	public Account()
	{
		username = "guest";
		email = "none";
	}
	public Account(String uname, String eml)
	{
		username = uname;
		email = eml;
	}
	/**
	 * getUsername()
	 * This gets the username of the account
	 * @return The username of the account
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * getEmail()
	 * This gets the email of the account
	 * @return
	 */
	public String getEmail()
	{
		return email;
	}
	public enum FilePermissionLevel {
		READ,
		READ_WRITE,
		UNRESTRICTED
	}
	
	public enum ChatPermissionLevel {
		MUTE,
		TO_LEADER,
		VOICE
	}
	/**
	 * equals()
	 * This determines if two accounts are equal
	 * @param a	Account object you wish to test against
	 * @return True if they are equal or alse otherwise
	 */
	public boolean equals(Account a)
	{
		if( (a.getUsername().equals(username) ) && (a.getEmail().equals(email)) )
		{
			return true;
		}
		return false;
		
	}

}
