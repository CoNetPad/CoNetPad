/**
 * FaiedAccountException
 * This is a new exception used in the Database class for creating new accounts
 */
package org.ndacm.acmgroup.cnp.exceptions;

import java.sql.SQLException;


public class FailedAccountException extends SQLException {
	
	private static final long serialVersionUID = 1L;
	
	private String msg;	//The Error message
	public FailedAccountException()
	{
		msg ="Unable to create new account";
	}
	public FailedAccountException(String m)
	{
		msg = m;
	}
	public String toString()
	{
		return msg;
	}
}
