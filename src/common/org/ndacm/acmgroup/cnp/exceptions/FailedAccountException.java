/**
 * This is a new exception used for creating and retrieving sessions
 * @author Justin Anderson
 * @version 1.0
 */
package org.ndacm.acmgroup.cnp.exceptions;

import java.sql.SQLException;


public class FailedAccountException extends SQLException {
	
	private static final long serialVersionUID = 1L;
	
	private String msg;	//The Error message
	/**
	 * Default Constructor
	 * This creats a new FailedAccountException using the default message
	 */
	public FailedAccountException()
	{
		msg ="Unable to create new account";
	}
	/**
	 * This creates a new FailedAccountException using a custom message
	 * @param m			The message of the new exception
	 */
	public FailedAccountException(String m)
	{
		msg = m;
	}
	/**
	 * This returns the message of the exception
	 */
	public String toString()
	{
		return msg;
	}
}
