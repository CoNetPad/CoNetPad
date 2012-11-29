package org.ndacm.acmgroup.cnp;

/**
 * A user account.
 * 
 * @author Justin, Cesar, Josh
 */
public class Account {

	private String username; // username of account
	private String email; // email of Account
	private int userID; // database ID of the account

	/**
	 * Default constructor
	 */
	public Account() {
		username = "guest";
		email = "none";
		userID = 0;
	}

	/**
	 * Overloaded constructor.
	 * @param uname The username for the account.
	 * @param eml The email address of the account.
	 * @param id The account ID.
	 */
	public Account(String uname, String eml, int id) {
		username = uname;
		email = eml;
		userID = id;
	}

	/**
	 * Get the account username.
	 * @return the account username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * Get the account email.
	 * @return the account email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Get the account user ID.
	 * @return the account user ID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Set the account user ID.
	 * @param id the account ID to set
	 */
	public void setUserID(int id) {
		userID = id;
	}


	/**
	 * Set the account username.
	 * @param name the username to set
	 */
	public void setUserName(String name) {
		username = name;
	}

	/**
	 * Enumeration for the different file permission levels that
	 * an account can have.
	 */
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

	/**
	 * Enumeration for the different chat permission levels that
	 * an account can have.
	 */
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
	 * Checks the equality of the account to another specified account.
	 * @param a the account to compare against
	 * @return true, if the accounts are equal
	 */
	public boolean equals(Account a) {
		if ((a.getUsername().equals(username)) && (a.getEmail().equals(email))) {
			return true;
		}
		return false;

	}

}
