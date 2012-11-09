/**
 * FaiedAccountException
 * This is a new exception used in the Database class for creating new accounts
 */
package org.ndacm.acmgroup.cnp.exceptions;


public class FailedAccountException extends Exception {
	
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
