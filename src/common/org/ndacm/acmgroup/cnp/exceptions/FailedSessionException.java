package org.ndacm.acmgroup.cnp.exceptions;

public class FailedSessionException extends Exception{

	private String msg;	//The Error message
	public FailedSessionException()
	{
		msg ="Unable to create new Session";
	}
	public FailedSessionException(String m)
	{
		msg = m;
	}
	public String toString()
	{
		return msg;
	}
}
