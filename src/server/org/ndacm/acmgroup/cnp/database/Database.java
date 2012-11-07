package org.ndacm.acmgroup.cnp.database;


import java.sql.Connection;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.server.CNPPrivateSession;
import org.ndacm.acmgroup.cnp.server.CNPSession;


public class Database implements IDatabase{
	
	private static final String driverClass = "org.sqlite.JDBC";
	private Connection dbConnection;
	
	public Database() {
		// load the sqlite-JDBC driver using the current class loader
	    // Class.forName(driverClass);
	}
	
	public Account createAccount(String username, String email, String password) {
		// TODO implement
		// also store in DB
		return new Account();
	}
	
	public Account retrieveAccount(String username, String password) {
		// TODO implement
		return new Account();
	}
	
	public CNPSession createSession(Account sessionLeader) {
		// TODO implement
		return new CNPSession();
	}
	
	public CNPPrivateSession createSession(Account sessionLeader, String sessionPassword) {
		// TODO implement
		return new CNPPrivateSession();
	}
	
	public CNPSession retrieveSession(String sessionName) {
		// TODO implement
		return new CNPSession();
	}
	
	public CNPPrivateSession retrieveSession(String sessionName, String sessionPassword) {
		// TODO implement
		return new CNPPrivateSession();
	}
	
	public boolean sessionIsPrivate(String sessionName) {
		// TODO implement
		return false;
	}
	
	public boolean createSessionAccount(CNPSession session, Account account,
			Account.FilePermissionLevel filePermission, Account.ChatPermissionLevel chatPermission) {
		// TODO implement
		return false;
	}

}
