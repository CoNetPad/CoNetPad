/**
 * Class:  Database<br>
 * Description:  This is a class for handling our database stuff.  
 * @author Justin
 */
package org.ndacm.acmgroup.cnp.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.*;


import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.server.CNPPrivateSession;
import org.ndacm.acmgroup.cnp.server.CNPSession;


/**
 * @author Justin
 *
 */
public class Database implements IDatabase{
	
	private static final String driverClass = "org.sqlite.JDBC";
	private Connection dbConnection;
	private String dbFile = "jdbc:sqlite:src//sqllite//CoNetPad.db3";
	private Statement stmt;
	
	/**
	 * Default Constructor
	 * @throws Exception
	 */
	public Database() throws Exception
	{
	     Class.forName(driverClass);
	     dbConnection = DriverManager.getConnection(dbFile);
	     stmt = dbConnection.createStatement();

	}
	/**
	 * createAccount()
	 * This Creates a new user account and returns an object
	 * @param username - String The string username you wish to use to create new account
	 * @param email - String The password of the new account
	 * @param password - String The RAW password to be given.  Encrpytion is done for you.
	 * @return Returns an new Account Object or throws an FailedAccountException
	 * @throws SQLException, FailedAccountException
	 */
	public Account createAccount(String username, String email, String password) throws SQLException, FailedAccountException 
	{
		// TODO implement
		// also store in DB
		try
		{
		    String encryptPass = sha1(password);
		 
			String query = "INSERT INTO UserAccount (Username, AccountPassword, Email) VALUES ('" + username + "', '"
						+ encryptPass + "', '" + email + "');";
			int result =stmt.executeUpdate(query);
			if(result > 0)
			{
				return new Account(username, email);
			}
			else
			{
				
				throw new FailedAccountException();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new FailedAccountException("Encrpytion failed");
		}
	}
	/**
	 * retrieveAccount()
	 * Gets an existing account from the database and returns it into an Account Object
	 * @param username The username you wish to try and get
	 * @param password The password you wish to verify with.  Make sure its RAW and not encrpypted.
	 * @return Account The account object of the the user account
	 * @throws SQLException
	 * @throws FailedAccountException
	 */
	public Account retrieveAccount(String username, String password) throws SQLException, FailedAccountException {
		// TODO implement
		String query = null;
		try
		{
		    String encryptPass = sha1(password);
			query = "SELECT Username, Email FROM UserAccount WHERE Username='" + username + "' AND  AccountPassword='"
						+ encryptPass + "';";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				String uname = rs.getString("Username");
				String email = rs.getString("Email");
				return new Account(uname, email);
			}
			throw new FailedAccountException("No Account Found");
			
		}
		catch(NoSuchAlgorithmException e)
		{
			throw new FailedAccountException("Encrpytion failed");
		}
		catch(SQLException e)
		{
			throw new FailedAccountException("SQL Error");
		}
	}
	
//	public CNPSession createSession(Account sessionLeader) {
//		// TODO implement
//		return new CNPSession();
//	}
//	
//	public CNPPrivateSession createSession(Account sessionLeader, String sessionPassword) {
//		// TODO implement
//		return new CNPPrivateSession();
//	}
//	
//	public CNPSession retrieveSession(String sessionName) {
//		// TODO implement
//		return new CNPSession();
//	}
//	
//	public CNPPrivateSession retrieveSession(String sessionName, String sessionPassword) {
//		// TODO implement
//		return new CNPPrivateSession();
//	}
//	
//	public boolean sessionIsPrivate(String sessionName) {
//		// TODO implement
//		return false;
//	}
//	
//	public boolean createSessionAccount(CNPSession session, Account account,
//			Account.FilePermissionLevel filePermission, Account.ChatPermissionLevel chatPermission) {
//		// TODO implement
//		return false;
//	}
	
	/**
	 * sha1()
	 * This returns the string version of the SHA1 Encryption
	 * @param input This is the string you wish to get the SHA1 Hash
	 * @return The encrypted value
	 * @throws NoSuchAlgorithmException
	 */
   private static String sha1(String input) throws NoSuchAlgorithmException
   {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(input.getBytes()); 
		byte[] b = md.digest();
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
		 '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		StringBuffer buf = new StringBuffer();
		for (int j=0; j<b.length; j++) 
		{
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		  return buf.toString();
	}

}


