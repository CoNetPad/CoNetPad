package org.ndacm.acmgroup.cnp.task;

/**
 * A task to send a chat message. An ChatTask is a message sent by a connected user to
 * one ore more other session users.
 * 
 */
public class ChatTask extends Task {
	
	private int userID;
	private String message;
	private String userAuthToken;
	
	/** 
	 * Constructor that creates a ChatTask which is sent to all other session
	 * users.
	 * 
	 * @param userID The userID of user that sent the chat message.
	 * @param message The chat message being sent.
	 * @param userAuthToken The authentication token for the user attempting to chat.
	 */
	public ChatTask(int userID, String message, String userAuthToken) {

		this.userID = userID;
		this.message = message;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public String toString()
	{
		return this.userID + " " + this.message;
	}

}
