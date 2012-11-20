/**
 * IDatabase
 * This is the interface class for database classes
 * @author Justin
 * @version 3.0
 */
package org.ndacm.acmgroup.cnp.database;

import java.sql.SQLException;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.CNPSession;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;

public interface IDatabase {
	
	/**
	 * This creates a new account
	 * @param username		The username of the new account
	 * @param email			The email address of the new account
	 * @param password		The un-encrypted password
	 * @return				The newly created account object
	 * @throws SQLException
	 * @throws FailedAccountException
	 */
	Account createAccount(String username, String email, String password)  throws SQLException, FailedAccountException;
	/**
	 * This retrieves an account
	 * @param username		The username of the account
	 * @param password		The un-encrpyted password
	 * @return				The account object
	 * @throws SQLException
	 * @throws FailedAccountException
	 */
	Account retrieveAccount(String username, String password) throws SQLException, FailedAccountException;
	
	/**
	 * This creates a public session
	 * @param sessionLeader			The database Id of the session leader.
	 * @param server				The CNPServer
	 * @return						The Session object
	 * @throws SQLException
	 * @throws FailedSessionException
	 */
	CNPSession createSession(int sessionLeader, CNPServer server) throws SQLException, FailedSessionException;
	
	/**
	 * This creates a private Session
	 * @param sessionLeader			The database ID of the leader
	 * @param server				The CNPServer
	 * @param sessionPassword		The un-encrypted password of the session
	 * @return						The session object
	 * @throws SQLException
	 * @throws FailedSessionException
	 */
	CNPSession createSession(int sessionLeader, CNPServer server, String sessionPassword) throws SQLException, FailedSessionException;
	
	/**
	 * This retrieves a public session
	 * @param sessionName		The unique name of the session
	 * @param server			The CNPServer 
	 * @return					The public session object
	 * @throws SQLException
	 * @throws FailedSessionException
	 * @throws FailedAccountException
	 */
	CNPSession retrieveSession(String sessionName, CNPServer server)throws SQLException, FailedSessionException, FailedAccountException;
		
	/**
	 * This retrieves a private session.
	 * @param sessionName			The unique session name
	 * @param server				The CNPServer 
	 * @param sessionPassword		The Un-encrypted password of the Session
	 * @return						The Private sesion object
	 * @throws SQLException
	 * @throws FailedSessionException
	 * @throws FailedAccountException
	 */
	CNPSession retrieveSession(String sessionName, CNPServer server, String sessionPassword)throws SQLException, FailedSessionException, FailedAccountException;
	/**
	 * This determines if a given session is private or not
	 * @param sessionName	The unique session name
	 * @return				True if it is private, false if its not
	 * @throws SQLException
	 */
	boolean sessionIsPrivate(String sessionName) throws SQLException ;
	/**
	 * This attaches a user to a session
	 * @param session			The session object
	 * @param account			The account object
	 * @param filePermission	The file permission of the user
	 * @param chatPermission	The chat permission of the user
	 * @return					True if it was successful or false it i failed
	 */
	boolean createSessionAccount(CNPSession session, Account account,
			Account.FilePermissionLevel filePermission, Account.ChatPermissionLevel chatPermission) throws SQLException;
	/**
	 * this deletes a public session
	 * @param session		The public session
	 * @return				Either the deletion was successful or not
	 * @throws SQLException
	 */
	boolean deleteSession(CNPSession session)throws SQLException;
	
	
	/**
	 * This deletes a private session
	 * @param session			The session object
	 * @param password			The password string.  Un-Encrypted.
	 * @return					True if the deletion was successful, false otherwise.
	 * @throws SQLException
	 * @throws FailedSessionException
	 */
	boolean deleteSession(CNPSession session, String password) throws SQLException, FailedSessionException;
	
	/**
	 * This deletes the given account
	 * @param account				The account to delete
	 * @return						True if the deletion was successful, false otherwise
	 * @throws SQLException
	 * @throws FailedAccountException
	 */
	boolean deleteAccount(Account account) throws SQLException, FailedAccountException;
	
	
}
