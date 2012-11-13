/**
 * Class:  Database<br>
 * Description:  This is a class for handling our database stuff.
 * See IDatabase for comments  
 * @author Justin
 * @version 3.0
 */
package org.ndacm.acmgroup.cnp.database;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;
import org.ndacm.acmgroup.cnp.server.CNPServer;
import org.ndacm.acmgroup.cnp.server.CNPSession;
import org.ndacm.acmgroup.cnp.server.CNPSession.SessionType;


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
				query = "select last_insert_rowid();";
				ResultSet rs = stmt.executeQuery(query);
				int id = rs.getInt(1);
				return new Account(username, email, id);
			
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
			query = "SELECT UserID, Username, Email FROM UserAccount WHERE Username='" + username + "' AND  AccountPassword='"
						+ encryptPass + "';";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				String uname = rs.getString("Username");
				String email = rs.getString("Email");
				int id = rs.getInt("UserID");
				return new Account(uname, email, id);
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
	
	/**
	 * createSession()
	 * This will create a new CNP Session
	 * @param sessionLeader	The session leader of the new session
	 * @param name 			The user-friendly name of the session
	 * @param channel		The IRC channel
	 * @param gpath			The path to the GIT
	 */
	public CNPSession createSession(Account sessionLeader, String sessionName, CNPServer server) throws SQLException, FailedSessionException{
		// TODO implement
		String query = null;
		try
		{
			query = "INSERT INTO Session (SessionLeader, SessionName, SessionType) " +
					"VALUES(" + sessionLeader.getUserID() + ", '" + sessionName + "', '" + SessionType.PUBLIC + "');";
			int result =stmt.executeUpdate(query);
			if(result > 0)
			{
				return new CNPSession(sessionLeader, sessionName, SessionType.PUBLIC);
			}
			else
			{
				
				throw new FailedSessionException();
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
		
	}
	
	@Override
	public CNPSession createSession(Account sessionLeader, String sessionName, CNPServer server, String sessionPassword) throws SQLException, FailedSessionException {
		String query = null;
		try
		{
			query = "INSERT INTO Session (SessionLeader, SessionName, SessionType) " +
					"VALUES(" + sessionLeader.getUserID() + ", '" + sessionName + "', '" + SessionType.PRIVATE.intValue() + "');";
			int result =stmt.executeUpdate(query);
			if(result > 0)
			{
				try
				{
					query = "select last_insert_rowid();";
					ResultSet rs = stmt.executeQuery(query);
					int id = rs.getInt(1);
					if(id > 0)
					{
						String ePass = Database.sha1(sessionPassword);
						query = "INSERT INTO SessionPassword (SessionID, SessionPassword) VALUES (" + id + ", '"+ ePass + "');";
						result =stmt.executeUpdate(query);
						if(result > 0)
						{
							return new CNPSession(sessionLeader, sessionName, SessionType.PRIVATE, ePass);
						}
						else
						{
							throw new FailedSessionException("Could not insert sesison password");
						}
					}
					else
					{
						throw new FailedSessionException("Error with creating private password");
					}
				}
				catch(SQLException se)
				{
					throw se;
				}
				catch(NoSuchAlgorithmException e)
				{
					throw new FailedSessionException("Password failed");
				}
			}
			else
			{
				
				throw new FailedSessionException();
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
	public CNPSession retrieveSession(String sessionName)throws SQLException, FailedSessionException, FailedAccountException {
		String query = null;
		try
		{
			query = "SELECT SessionLeader, SessionName, SessionType, IrcChannel, GitPath FROM Session WHERE SessionName ='" + sessionName + "';";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				int sessionLeader = rs.getInt("SessionLeader");
				String name = rs.getString("SessionName");
				SessionType type = SessionType.getType( rs.getInt("SessionType") );
				String channel = rs.getString("IrcChannel");
				String gitPath = rs.getString("GitPath");
				Account act = getAccountById(sessionLeader);
				return new  CNPSession(act, name, type);
			}
			throw new FailedSessionException("No Session found");
		}
		catch(FailedAccountException e)
		{
			throw e;
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
	
	public CNPSession retrieveSession(String sessionName, String sessionPassword)throws SQLException, FailedSessionException, FailedAccountException {
		String query = null;
		try
		{		
			query = "SELECT SessionID, SessionLeader, SessionName, SessionType, IrcChannel, GitPath FROM Session WHERE SessionName ='" + sessionName + "';";
			ResultSet rs = stmt.executeQuery(query);
			String ePass = sha1(sessionPassword);
			while(rs.next())
			{
				int sessionID = rs.getInt("SessionID");
				int sessionLeader = rs.getInt("SessionLeader");
				String name = rs.getString("SessionName");
				SessionType type = SessionType.getType( rs.getInt("SessionType") );
				String channel = rs.getString("IrcChannel");
				String gitPath = rs.getString("GitPath");
				Account act = getAccountById(sessionLeader);
				query = "SELECT SessionPassword FROM SessionPassword WHERE SessionID=" + sessionID + " LIMIT 1;";
				rs = stmt.executeQuery(query);
				while(rs.next())
				{
					String pass = rs.getString("SessionPassword");
					if(ePass.equals(pass))
					{	return new CNPSession(act, name, type, channel);	}
					else
					{	throw new FailedSessionException("Passwords for private session did not match");	}
				}
				throw new FailedSessionException("No Session found");
			}
			throw new FailedSessionException("No Session found");
		}
		catch(FailedAccountException e)
		{
			throw e;
		}
		catch(NoSuchAlgorithmException e)
		{
			throw new FailedSessionException("Error with encrpyting password");
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
	
	public boolean sessionIsPrivate(String sessionName) {
		// TODO implement
		try{
			CNPSession session = this.retrieveSession(sessionName);
			if(session.getType() == SessionType.PRIVATE)
			{
				return true;
			}
				
		}
		catch(Exception e)
		{
			return false;
		}
		return false;
	}
	
//	public boolean createSessionAccount(CNPSession session, Account account,
//			Account.FilePermissionLevel filePermission, Account.ChatPermissionLevel chatPermission) {
//		// TODO implement
//		return false;
//	}
	
	public Account getAccountById(int id) throws SQLException, FailedAccountException
	{
		String query = null;
		try
		{
			query = "SELECT UserID, UserName, Email FROM UserAccount WHERE UserID =" + id + ";";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				int userID = rs.getInt("UserID");
				String userName = rs.getString("UserName");
				String email = rs.getString("Email");
				return new Account(userName, email, userID);
				
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
		throw new FailedAccountException("Unable to retrive Account by database id.");
		
	}
	
	/**
	 * sha1()
	 * This returns the string version of the SHA1 Encryption
	 * @param input This is the string you wish to get the SHA1 Hash
	 * @return The encrypted value
	 * @throws NoSuchAlgorithmException
	 */
   public static String sha1(String input) throws NoSuchAlgorithmException
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


