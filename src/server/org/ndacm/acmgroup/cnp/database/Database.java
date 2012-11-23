

package org.ndacm.acmgroup.cnp.database;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel;
import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;
import org.ndacm.acmgroup.cnp.CNPPrivateSession;
import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.CNPSession;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;

/**
 * Class:  Database<br>
 * Description:  This is a class for handling our database stuff.  
 * 
 */
public class Database implements IDatabase{

	private static final String DRIVER_CLASS = "org.sqlite.JDBC";
	private static final String ENCRYPTION_ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final String DB_FILE = "jdbc:sqlite:src//sqllite//CoNetPad.db3";

	private Connection dbConnection;
	private Random random;

	/**
	 * Default Constructor
	 * @throws SQLException 
	 * @throws Exception
	 */
	public Database() throws ClassNotFoundException, SQLException
	{
		Class.forName(DRIVER_CLASS);
		dbConnection = DriverManager.getConnection(DB_FILE);
		random = new Random();

	}
	/**
	 * createAccount()
	 * This Creates a new user account and returns an object
	 * @param username - String The string username you wish to use to create new account
	 * @param email - String The password of the new account
	 * @param password - String The RAW password to be given.  Encrpytion is done for you.
	 * @return Returns an new Account Object or throws an FailedAccountException
	 * @throws FailedAccountException 
	 */
	public Account createAccount(String username, String email, String password) throws FailedAccountException {

		Account newAccount = null;

		// salt and hash password
		// http://stackoverflow.com/questions/2860943/suggestions-for-library-to-hash-passwords-in-java
		// http://stackoverflow.com/questions/5499924/convert-java-string-to-byte-array
		String hashString = null, saltString = null;
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		try {
			saltString = new String(salt, "ISO-8859-1");
			hashString = this.encrypt(password, saltString);
			// test if username/email already exists

			// insert user into DB
			PreparedStatement registerUser = null;
			String insertion = "INSERT INTO UserAccount (Username, AccountPassword, AccountSalt, Email) "
					+ "VALUES (? , ?, ?, ?)";

			registerUser = dbConnection.prepareStatement(insertion);
			registerUser.setString(1, username);
			registerUser.setString(2, hashString);
			registerUser.setString(3, saltString);
			registerUser.setString(4, email);

			registerUser.executeUpdate();

			// return the account that was just inserted
			newAccount = retrieveAccount(username, password);

			registerUser.close();

		} catch (NoSuchAlgorithmException ex) {
			System.err.println("Invalid Encrpytion Algorithm: " + ENCRYPTION_ALGORITHM);
			throw new FailedAccountException("Error creating account for " + username);
		} catch (InvalidKeySpecException e) {
			System.err.println("Invalid key spec.");
			throw new FailedAccountException("Error creating account for " + username);
		} catch (UnsupportedEncodingException e) {
			System.err.println("Unsupported encoding.");
			throw new FailedAccountException("Error creating account for " + username);
		} catch (SQLException e) {
			System.err.println("SQL error.");
			throw new FailedAccountException("Error creating account for " + username);
		}
		catch(Exception e)
		{
			System.err.println("Unknown Exception thrown:  " + e.getStackTrace());
			throw new FailedAccountException("Some Exception Thrown.");
		}

		if (newAccount != null) {
			return newAccount;
		} else {
			throw new FailedAccountException("Error creating account for " + username);
		}

	}
	/**
	 * retrieveAccount()
	 * Gets an existing account from the database and returns it into an Account Object
	 * @param username The username you wish to try and get
	 * @param password The password you wish to verify with.  Make sure its RAW and not encrypted.
	 * @return Account The account object of the the user account
	 * @throws FailedAccountException
	 */
	public Account retrieveAccount(String username, String password) throws FailedAccountException {

		PreparedStatement retrieveAccount = null;
		ResultSet rset = null;
		Account accountRetrieved = null;
		String query = "SELECT * "
				+ "FROM UserAccount "
				+ "WHERE username = ?";

		try {
			// retrieve user with given username
			retrieveAccount = dbConnection.prepareStatement(query);
			retrieveAccount.setString(1, username);

			//run the query, return a result set        
			rset = retrieveAccount.executeQuery();
			if(rset.next())
			{
				int idRetrieved = rset.getInt("UserID");
				String nameRetrieved = rset.getString("UserName");
				String emailRetrieved = rset.getString("Email");
				String hashRetrieved = rset.getString("AccountPassword");
				String saltRetrieved = rset.getString("AccountSalt");
				String hashPass = this.encrypt(password, saltRetrieved);
				retrieveAccount.close();
				rset.close();
				if(hashRetrieved.equals(hashPass))
				{
					return new Account(nameRetrieved, emailRetrieved, idRetrieved);
					
				}
				else
				{
					throw new FailedAccountException("Passwords did not match");
				}
				//clean up database classes
			
			}
			else
			{
				throw new FailedAccountException("No User Account was found");
			}

		} 
		catch (SQLException ex) {
			throw new FailedAccountException("Error retrieving account for " + username);
		}
		catch (NoSuchAlgorithmException ex) {
			System.err.println("Invalid Encrpytion Algorithm: " + ENCRYPTION_ALGORITHM);
			throw new FailedAccountException("Error retrieving account for " + username);
		} catch (UnsupportedEncodingException ex) {
			System.err.println("Unsupported encoding.");
			throw new FailedAccountException("Error retrieving account for " + username);
		} catch (InvalidKeySpecException ex) {
			System.err.println("Invalid key spec.");
			throw new FailedAccountException("Error retrieving account for " + username);
		}
		catch(NullPointerException e)
		{
				System.err.println("Some other Error was caught");
			throw new FailedAccountException("Error  " + e.getStackTrace() );
		}


	}


	public CNPSession createSession(int sessionLeader, CNPServer server) throws FailedSessionException {

		// create session and store in database			
		CNPSession newSession = null;

		// TODO test if session already exists
		
		// insert session into DB
		PreparedStatement createSession = null, retrieveSession = null;
	
		String query = "SELECT * "
				+ "FROM UserAccount "
				+ "WHERE UserID = ?";
		try{
			retrieveSession = dbConnection.prepareStatement(query);
			retrieveSession.setInt(1, sessionLeader);
			ResultSet rset = retrieveSession.executeQuery();
			if(rset.next())
			{
				
				String sessionName = CNPSession.generateString();
				String insertion = "INSERT INTO Session (SessionLeader, SessionName, IsPublic) "
						+ "VALUES (? , ?, ?)";
		
				createSession = dbConnection.prepareStatement(insertion);
				createSession.setInt(1, sessionLeader);
				createSession.setString(2, sessionName);
				createSession.setBoolean(3, true);
		
				createSession.executeUpdate();
		
				// return the session that was just inserted
				newSession = retrieveSession(sessionName, server);
		
				createSession.close();
				return newSession;
			}
			else
			{
				System.err.println("The SessionLeader was not found.");
				throw new FailedAccountException("Could not find Session Leader.");
			}
	
		}
		catch(SQLException e)
		{
			throw new FailedSessionException("SQL Error.");
		}

	}

	public CNPPrivateSession createSession(int sessionLeader, CNPServer server, String sessionPassword) 
			throws FailedSessionException {

		// create session and store in database
		CNPPrivateSession newSession = null;

		// TODO test if session already exists

		// salt and hash password
		// http://stackoverflow.com/questions/2860943/suggestions-for-library-to-hash-passwords-in-java
		// http://stackoverflow.com/questions/5499924/convert-java-string-to-byte-array
		String hashString = null, saltString = null;
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		try {
			saltString = new String(salt, "ISO-8859-1");
			hashString = this.encrypt(sessionPassword, saltString);
			// insert session into DB
			PreparedStatement createSession = null;
			String sessionName = CNPSession.generateString();
			String insertion = "INSERT INTO Session (SessionLeader, SessionName, IsPublic) "
					+ "VALUES (? , ?, ?)";

			createSession = dbConnection.prepareStatement(insertion);
			createSession.setInt(1, sessionLeader);
			createSession.setString(2, sessionName);
			createSession.setBoolean(3, false);
			createSession.executeUpdate();
			
			String query = "Select last_insert_rowid()";
			PreparedStatement retrieveSession  = dbConnection.prepareStatement(query);
			ResultSet rset = retrieveSession.executeQuery();
			if(rset.next())
			{
				int sessionID = rset.getInt(1);
				//, SessionPassword, SessionSalt
				//Select last_insert_rowid();
				insertion = "INSERT INTO SessionPassword (SessionID, SessionPassword, SessionSalt) "
						+ "VALUES (? , ?, ?)";
				createSession = dbConnection.prepareStatement(insertion);
				createSession.setInt(1, sessionID);
				createSession.setString(2, hashString);
				createSession.setString(3, saltString);
				createSession.executeUpdate();
				// return the account that was just inserted
				newSession = retrieveSession(sessionName, server, sessionPassword);

				createSession.close();
			}



		} catch (NoSuchAlgorithmException ex) {
			System.err.println("Invalid Encrpytion Algorithm: " + ENCRYPTION_ALGORITHM);
			throw new FailedSessionException("Error creating session.");
		} catch (InvalidKeySpecException e) {
			System.err.println("Invalid key spec.");
			throw new FailedSessionException("Error creating session.");
		} catch (UnsupportedEncodingException e) {
			System.err.println("Unsupported encoding.");
			throw new FailedSessionException("Error creating session.");
		}
		catch(SQLException ex) {
			throw new FailedSessionException("Error creating session.");
		}

		return newSession;
	}

	public CNPSession retrieveSession(String sessionName, CNPServer server) throws SQLException, FailedSessionException {

		PreparedStatement retrieveSession = null;
		ResultSet rset = null;
		CNPSession sessionRetrieved = null;
		
		String query = "SELECT * "
				+ "FROM Session "
				+ "WHERE SessionName = ?";


		// retrieve user with given username
		retrieveSession = dbConnection.prepareStatement(query);
		retrieveSession.setString(1, sessionName);

		//run the query, return a result set        
		rset = retrieveSession.executeQuery();
		if(rset.next())
		{
			int idRetrieved = rset.getInt("SessionID");
			String nameRetrieved = rset.getString("SessionName");
			int sessionLeader = rset.getInt("SessionLeader");
			sessionRetrieved = new CNPSession(idRetrieved, nameRetrieved, server, sessionLeader);
	
			//clean up database classes
			retrieveSession.close();
			rset.close();
	
			return sessionRetrieved;
		}
		else
		{
			throw new FailedSessionException("No Sesison was found");
		}
		
	}

	public CNPPrivateSession retrieveSession(String sessionName, CNPServer server, String sessionPassword) throws FailedSessionException, FailedAccountException 
	{
		PreparedStatement retrieveSession = null;
		ResultSet rset = null;

		String query = "SELECT * "
				+ "FROM Session "
				+ "WHERE SessionName = ?";

		try{
			// retrieve user with given username
			retrieveSession = dbConnection.prepareStatement(query);
			retrieveSession.setString(1, sessionName);
	
			//run the query, return a result set        
			rset = retrieveSession.executeQuery();
			if(rset.next())
			{
				int idRetrieved = rset.getInt("SessionID");
				String nameRetrieved = rset.getString("SessionName");
				int sessionLeader = rset.getInt("SessionLeader");
				//Verify Password
				query = "SELECT * "
						+ "FROM SessionPassword "
						+ "WHERE SessionID = ?";
				retrieveSession = dbConnection.prepareStatement(query);
				retrieveSession.setInt(1, idRetrieved );
				//run the query, return a result set        
				rset = retrieveSession.executeQuery();
				if(rset.next())
				{
			
					String salt = rset.getString("SessionSalt");
					String sessionPassword2 = rset.getString("SessionPassword");
					String sessionPasswordHash = this.encrypt(sessionPassword, salt);
					retrieveSession.close();
					rset.close();
					if(sessionPassword2.equals(sessionPasswordHash))
					{
						return new CNPPrivateSession(idRetrieved, nameRetrieved, server, sessionLeader);
					}
					else
					{
						System.err.println("Passwords Did not Match.");
						throw new FailedSessionException("Passwords did not match");
					}
					
				}
				else
				{
					System.err.println("No SessionPassword Found");
					throw new FailedSessionException("Session Password was given, but no correspodning session password found.");
				}	
			}
			else
			{
				System.err.println("No Session Found");
				throw new FailedSessionException("No Sesison was found");
			}
		}
		catch (NoSuchAlgorithmException ex) 
		{
			System.err.println("Invalid Encrpytion Algorithm: " + ENCRYPTION_ALGORITHM);
			throw new FailedAccountException("Error creating session.");
		} 
		catch (InvalidKeySpecException e) 
		{
			System.err.println("Invalid key spec.");
			throw new FailedAccountException("Error creating session.");
		} 
		catch(SQLException e)
		{
			System.err.println("SQL Error" + e.toString());
			throw new FailedSessionException("SQL Error.");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("Password Encoding error");
			throw new FailedSessionException("Encoding error");
		}
		

	}

	
	
	public boolean sessionIsPrivate(String sessionName) throws SQLException {
		// TODO implement
		
		String query = "SELECT Session.SessionID, Session.SessionName, SessionPassword.SessionPassword, SessionPassword.SessionSalt"
					+ " FROM Session, SessionPassword"
					+ " WHERE Session.SessionID = SessionPassword.SessionID AND Session.SessionName = ?";
		try
		{
			PreparedStatement retrieveSession = dbConnection.prepareStatement(query);
			retrieveSession.setString(1, sessionName);
			ResultSet rs = retrieveSession.executeQuery();
			
			if(rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
		
	}

	public boolean createSessionAccount(CNPSession session, Account account,Account.FilePermissionLevel filePermission, Account.ChatPermissionLevel chatPermission) throws SQLException 
	{
		// TODO implement
		PreparedStatement createSA= null;
		String insertion = "INSERT INTO SessionUser (SessionID, UserID, FilePermissionLevel, ChatPermissionLevel) "
				+ "VALUES (? , ?, ?, ?)";
		try{
			createSA = dbConnection.prepareStatement(insertion);
			createSA.setInt(1, session.getSessionID() );
			createSA.setInt(2, account.getUserID());
			createSA.setInt(3, filePermission.toInt());
			createSA.setInt(4, chatPermission.toInt());
			int rows = createSA.executeUpdate();
			if(rows > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(SQLException e)
		{
			throw e;
		}
		
	}
	
	
	private String encrypt(String input, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException
	{
		byte[] salt2 = salt.getBytes("ISO-8859-1");
		KeySpec spec = new PBEKeySpec(input.toCharArray(), salt2, 2048, 160);
		SecretKeyFactory f = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM);
		return new String(f.generateSecret(spec).getEncoded());
	}
	
	
	
	@Override
	public boolean deleteSession(CNPSession session) throws SQLException {
		// TODO Auto-generated method stub
		String query = "DELETE FROM Session WHERE SessionID = ?";
		String query2 = "DELETE FROM SessionPassword WHERE SessionID = ?";
		PreparedStatement deleteSA= null;
		try{
			deleteSA = dbConnection.prepareStatement(query);
			deleteSA.setInt(1, session.getSessionID() );
			
			int rows1 = deleteSA.executeUpdate();
			deleteSA = dbConnection.prepareStatement(query2);
			deleteSA.setInt(1, session.getSessionID() );
			int rows2 = deleteSA.executeUpdate();
			int rows = rows1 + rows2;
			if(rows > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
		
	}
	
	@Override
	public boolean deleteAccount(Account account) throws SQLException, FailedAccountException {
		String query = "DELETE FROM UserAccount WHERE UserID = ?";
		String query1 = "DELETE FROM SessionUser WHERE UserId = ?";
		PreparedStatement deleteUser= null;
		try{
			deleteUser = dbConnection.prepareStatement(query);
			deleteUser.setInt(1, account.getUserID() );
			int rows1 = deleteUser.executeUpdate();
			deleteUser = dbConnection.prepareStatement(query1);
			deleteUser.setInt(1, account.getUserID() );
			int rows2 = deleteUser.executeUpdate();
			
			int rows = rows1 + rows2;
			if(rows > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
		
	}
	@Override
	public boolean createSessionAccount(CNPSession session, Account account,
			String password, FilePermissionLevel filePermission,
			ChatPermissionLevel chatPermission) throws SQLException {
		
		PreparedStatement createSA= null, retrieveSession=null;
		String insertion = "INSERT INTO SessionUser (SessionID, UserID, FilePermissionLevel, ChatPermissionLevel) "
				+ "VALUES (? , ?, ?, ?)";
		String query = "SELECT * FROM SessionPassword WHERE SessionID = ?";
		try{
			retrieveSession  = dbConnection.prepareStatement(query);
			retrieveSession.setInt(1, session.getSessionID());
			ResultSet rs = retrieveSession.executeQuery();
			if(rs.next() )
			{
				String password1 = rs.getString("SessionPassword");
				String salt = rs.getString("SessionSal");
				String password2 = this.encrypt(password, salt);
				if(password1.equals(password2))
				{
					createSA = dbConnection.prepareStatement(insertion);
					createSA.setInt(1, session.getSessionID() );
					createSA.setInt(2, account.getUserID());
					createSA.setInt(3, filePermission.toInt());
					createSA.setInt(4, chatPermission.toInt());
					int rows = createSA.executeUpdate();
					if(rows > 0)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			
		}
		catch(SQLException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			return false;
		}
		return false;
		
		// TODO Auto-generated method stub
	}
	
	public static boolean sessionExists(String sessionName) {
		// TODO implement
		return false;
	}
	

}


