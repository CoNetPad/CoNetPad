package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for sending a chat message.
 * 
 * @author Josh Tan
 *
 */
public class ChatTaskResponse extends TaskResponse {

	private String username;
	private String message;

	/**
	 * Default constructor.
	 * 
	 * @param username the username for the task
	 * @param message the chat message for the task
	 */
	public ChatTaskResponse(String username, String message) {
		this.username = username;
		this.message = message;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);
	}

	/**
	 * Get the username for the task.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the chat message for the task.
	 * 
	 * @return the chat message
	 */
	public String getMessage() {
		return message;
	}

}
