/**
 * This is an exception used for failed create and retrieve sessions
 * @author Justin Anderson
 * @version 1.0
 */
package org.ndacm.acmgroup.cnp.exceptions;

public class FailedSessionException extends Exception{

	private String msg;	//The Error message
	/**
	 * This creates a new FailedSessionException using the default message
	 */
	public FailedSessionException()
	{
		msg ="Unable to create new Session";
	}
	/**
	 * This creates a new FailedSessoinException using a custom message
	 * @param m			The message of the FailedSessonException
	 */
	public FailedSessionException(String m)
	{
		msg = m;
	}
	/**
	 * This returns the message
	 */
	public String toString()
	{
		return msg;
	}
}
