package org.ndacm.acmgroup.cnp;

import org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel;

/**
 * A task to send a chat message. An ChatTask is a message sent by a connected user to all
 * other connected users.
 * 
 */
public class ChatTask extends CNPTask implements Runnable {
	
	/**
	 * The permission level that is required to execute this task.
	 */
	public static ChatPermissionLevel PERMISSION;

	private String username;
	private String message;
	
	/**
	 * Fully-specified constructor for ChatTask.
	 * 
	 * @param username The username of user that sent the chat message.
	 * @param message The chat message being sent.
	 */
	public ChatTask(String username, String message) {
		
		PERMISSION = ChatPermissionLevel.VOICE;
		
		this.username = username;
		this.message = message;
	}
	
	/**
	 * Execute the task associated with this CNPTask.
	 */
	@Override
	public void execute() {
		run();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
