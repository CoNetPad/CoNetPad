package org.ndacm.acmgroup.cnp;

/**
 * Account class This is for handling each of the user accounts
 * 
 * @version 2.0
 * @author Justin Andres, Josh Tan
 */
public class Account {

	private String username; // Username of account
	private String email; // Email of Accout
	private int userID; // Database ID of the account

	public Account() {
		username = "guest";
		email = "none";
		userID = 0;
	}

	/**
	 * Constructor
	 * 
	 * @param uname
	 *            The username of the account
	 * @param eml
	 *            The email of the account
	 * @param id
	 *            The database ID of the account
	 */
	public Account(String uname, String eml, int id) {
		username = uname;
		email = eml;
		userID = id;
	}

	/**
	 * getUsername() This gets the username of the account
	 * 
	 * @return The username of the account
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * getEmail() This gets the email of the account
	 * 
	 * @return The email address of the Account
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * getUserID() This gets the Database ID of the user.
	 * 
	 * @return The database ID of the user
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param id
	 *            to be set for the current account.
	 */
	public void setUserID(int id) {
		userID = id;
	}

	/**
	 * @param name
	 *            to be set for the current account.
	 */
	public void setUserName(String name) {
		username = name;
	}

	public enum FilePermissionLevel {
		READ(0), READ_WRITE(2), UNRESTRICTED(3);
		private int value;

		FilePermissionLevel(int val) {
			this.value = val;
		}

		public int toInt() {
			return this.value;
		}
	}

	public enum ChatPermissionLevel {
		MUTE(0), TO_LEADER(1), VOICE(2);
		private int value;

		ChatPermissionLevel(int val) {
			this.value = val;
		}

		public int toInt() {
			return this.value;
		}
	}

	/**
	 * equals() This determines if two accounts are equal
	 * 
	 * @param a
	 *            Account object you wish to test against
	 * @return True if they are equal or alse otherwise
	 */
	public boolean equals(Account a) {
		if ((a.getUsername().equals(username)) && (a.getEmail().equals(email))) {
			return true;
		}
		return false;

	}

}
