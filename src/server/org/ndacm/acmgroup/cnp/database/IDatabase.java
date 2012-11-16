/**
 * IDatabase
 * This is the interface class for database classes
 * @author Justin
 * @version 1.5
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
	 * createAccount()
	 * This creates a new account in the database and object
	 * @param username	The username you want to use for the new account
	 * @param email		The email you want to use for the new account
	 * @param password	The password you want to use for the new account.  Let it be RAW as the method will encrypt it.
	 * @return			Return a new account Object
	 * @throws SQLException
	 * @throws FailedAccountException
	 */
	Account createAccount(String username, String email, String password)  throws SQLException, FailedAccountException;
	/**
	 * This retrieves an account
	 * @param username	The username of the account
	 * @param password	The password of the account (unencrypted)
	 * @return			The account object .
	 * @throws SQLException
	 * @throws FailedAccountException
	 */
	Account retrieveAccount(String username, String password) throws SQLException, FailedAccountException;
	
	/**
	 * This creates a new session.
	 * @param sessionLeader	The account object of the leader
	 * @param name			The name of the session
	 * @param channel		The IRC channel
	 * @param gPath			The path to the GIt
	 * @return				The CNP session
	 * @throws SQLException
	 * @throws FailedSessionException
	 */
	CNPSession createSession(int sessionLeader, CNPServer server) throws SQLException, FailedSessionException;
	
	/**
	 * This creates a new session that is private
	 * @param sessionLeader		The account object of the leader
	 * @param name				The name of the session
	 * @param channel			The irc channel
	 * @param gPath				The git path
	 * @param sessionPassword	The unecrypted password
	 * @return					The CNP session
	 * @throws SQLException
	 * @throws FailedSessionException
	 */
	CNPSession createSession(int sessionLeader, CNPServer server, String sessionPassword) throws SQLException, FailedSessionException;
	
	/**
	 * This retrieves the session
	 * @param sessionName	The name of the session
	 * @return				The session object
	 * @throws SQLException
	 * @throws FailedSessionException
	 * @throws FailedAccountException
	 */
	CNPSession retrieveSession(String sessionName, CNPServer server)throws SQLException, FailedSessionException, FailedAccountException;
	
	/**
	 * This retrieves a private session
	 * @param sessionName		The Session name
	 * @param sessionPassword	The session Password unecrpyted
	 * @return					The CNPSession
	 * @throws SQLException
	 * @throws FailedSessionException
	 * @throws FailedAccountException
	 */
	CNPSession retrieveSession(String sessionName, CNPServer server, String sessionPassword)throws SQLException, FailedSessionException, FailedAccountException;
	
	boolean sessionIsPrivate(String sessionName);
	
//	boolean createSessionAccount(CNPSession session, Account account,
//			Account.FilePermissionLevel filePermission, Account.ChatPermissionLevel chatPermission);
}
