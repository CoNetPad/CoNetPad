package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.Account.ChatPermissionLevel;

/**
 * A task to send a chat message. An ChatTask is a message sent by a connected user to
 * one ore more other session users.
 * 
 */
public class ChatTask extends Task implements Runnable {
	
	/**
	 * The permission level that is required to execute this task.
	 */
	public static ChatPermissionLevel PERMISSION = ChatPermissionLevel.VOICE;

	private String username;
	private String message;
	
	/** 
	 * Constructor that creates a ChatTask which is sent to all other session
	 * users. Will require the sending account to have the VOICE permission level.
	 * 
	 * @param username The username of user that sent the chat message.
	 * @param message The chat message being sent.
	 */
	public ChatTask(String username, String message) {

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
	public String toString()
	{
		return this.username + " " + this.message;
	}

}
