package org.ndacm.acmgroup.cnp.exceptions;

/**
 * This is an exception used for failed create and retrieve sessions.
 * 
 * @author Justin Anderson
 * @version 1.0
 */
public class FailedSessionException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String msg;	// The error message
	
	/**
	 * This creates a new FailedSessionException using the default message.
	 */
	public FailedSessionException()
	{
		msg ="Unable to create new Session";
	}
	
	/**
	 * This creates a new FailedSessoinException using a custom message.
	 * 
	 * @param m			The message of the FailedSessonException
	 */
	public FailedSessionException(String m)
	{
		msg = m;
	}
	
	/**
	 * This returns the message.
	 */
	public String toString()
	{
		return msg;
	}
}
