package org.ndacm.acmgroup.cnp.task;

/**
 * A task to send a chat message. An ChatTask is a message sent by a connected
 * user to one ore more other session users.
 * 
 */
public class ChatTask extends SessionTask {

	protected int userID;
	private String username;
	protected String message;
	protected String userAuthToken;

	/**
	 * Constructor that creates a ChatTask which is sent to all other session
	 * users.
	 * 
	 * @param userID
	 *            The userID of user that sent the chat message.
	 * @param message
	 *            The chat message being sent.
	 * @param userAuthToken
	 *            The authentication token for the user attempting to chat.
	 */
	public ChatTask(int userID, String username, int sessionID, String message,
			String userAuthToken) {
		this.userID = userID;
		this.sessionID = sessionID;
		this.username = username;
		this.message = message;
		this.userAuthToken = userAuthToken;
	}
	
	/**
	 * Run the task in a thread
	 */
	@Override
	public void run() {
		session.executeTask(this);

	}

	/**
	 * Set the userID of the chat message's sender
	 * @param userID		The user ID of the sender
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * Set the content of the chat message
	 * @param message		The message to be sent
	 */	
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets the Use authrized token
	 * @param userAuthToken		The valid User autherized token
	 */
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}

	/**
	 * This returns a string of the chat userID and the mssage
	 * @return The User ID followed by a space,a nd chat message
	 */
	public String toString() {
		return this.userID + " " + this.message;
	}
	
	/**
	 * This returns the UserID of the sender
	 * @return		User ID of the sender
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * This gets the content of the chat message
	 * @return		The chat message contents
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * This gets the user authorized token
	 * @return		The authorized user token
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}
	
	/**
	 * This gets the Username of the sender
	 * @return		The username of the user
	 */
	public String getUsername() {
		return username;
	}

}
