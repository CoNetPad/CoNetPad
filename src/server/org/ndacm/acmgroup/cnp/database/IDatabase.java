/**
 * IDatabase
 * This is the interface class for database classes
 * @author Justin
 * @version 1.5
 */
package org.ndacm.acmgroup.cnp.database;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.server.CNPPrivateSession;
import org.ndacm.acmgroup.cnp.server.CNPSession;



import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
	
//	Account retrieveAccount(String username, String password);
//	
//	CNPSession createSession(Account sessionLeader);
//	
//	CNPPrivateSession createSession(Account sessionLeader, String sessionPassword);
//	
//	CNPSession retrieveSession(String sessionName);
//	
//	CNPPrivateSession retrieveSession(String sessionName, String sessionPassword);
//	
//	boolean sessionIsPrivate(String sessionName);
//	
//	boolean createSessionAccount(CNPSession session, Account account,
//			Account.FilePermissionLevel filePermission, Account.ChatPermissionLevel chatPermission);
}
