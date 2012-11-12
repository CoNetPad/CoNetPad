/**
 * Class:  Account
 * Descrption:  This is a java object class to deal with user-accounts.
 */
package org.ndacm.acmgroup.cnp;

public class Account {
	
	private String username;		//Username of account
	private String email;			//Email of Accout
	private int userid;				//Database ID of the account
	public Account()
	{
		username = "guest";
		email = "none";
		userid = 0;
	}
	
	/**
	 * Constructor
	 * @param uname	The username of the account
	 * @param eml	The email of the account
	 * @param id	The database ID of the account
	 */
	public Account(String uname, String eml, int id)
	{
		username = uname;
		email = eml;
		userid = id;
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
	
	/**
	 * getUserID()
	 * This gets the Database ID of the user.
	 * @return	The database ID of the user
	 */
	public int getUserID()
	{
		return userid;
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
		if( (a.getUsername().equals(username) ) && (a.getEmail().equals(email)) && (a.getUserID() == userid) )
		{
			return true;
		}
		return false;
		
	}

}
