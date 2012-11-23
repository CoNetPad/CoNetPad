package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.CNPSession;

/**
 * A task to send a chat message. An ChatTask is a message sent by a connected user to
 * one ore more other session users.
 * 
 */
public class ChatTask extends TaskRequest {
	
	protected int userID;
	private String username;
	protected String message;
	protected String userAuthToken;
	private int sessionID;
	private CNPSession session;
	
	/** 
	 * Constructor that creates a ChatTask which is sent to all other session
	 * users.
	 * 
	 * @param userID The userID of user that sent the chat message.
	 * @param message The chat message being sent.
	 * @param userAuthToken The authentication token for the user attempting to chat.
	 */
	public ChatTask(int userID, String username, int sessionID, String message, String userAuthToken) {

		this.userID = userID;
		this.sessionID = sessionID;
		this.message = message;
		this.userAuthToken = userAuthToken;
	}
	
	@Override
	public void run() {
		session.executeTask(this);
		
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}

	public String toString()
	{
		return this.userID + " " + this.message;
	}

	public int getUserID() {
		return userID;
	}

	public String getMessage() {
		return message;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}
	
	public void setSession(CNPSession session) {
		this.session = session;
	}
	
	public CNPSession getSession() {
		return session;
	}
	
	public int getSessionID() {
		return sessionID;
	}
	
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	
	public String getUsername() {
		return username;
	}

}
