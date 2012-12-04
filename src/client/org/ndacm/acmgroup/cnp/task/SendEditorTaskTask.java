package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.CNPClient;

/**
 * A task for the client to send an editor task to the server.
 * 
 * @author Josh Tan
 *
 */
public class SendEditorTaskTask extends Task {
	
	private int userID;
	private int sessionID;
	private int keyPressed;
	private int editIndex;
	private int fileID;
	private String authToken;
	private final CNPClient client;
	
	/**
	 * Default constructor.
	 * 
	 * @param userID The ID of the user that made the edit.
	 * @param sessionID The ID of the session that the edited file reside in.
	 * @param keyPressed The key (as an int) that was pressed.
	 * @param fileID The ID of the file edited.
	 * @param authToken The authentication token of the user that made the edit.
	 * @param client The client for the user.
	 */
	public SendEditorTaskTask(int userID, int sessionID, int keyPressed,
			int fileID, String authToken, CNPClient client) {
		this.userID = userID;
		this.sessionID = sessionID;
		this.keyPressed = keyPressed;
		this.fileID = fileID;
		this.authToken = authToken;
		this.client = client;
	}

	/**
	 * Send an editor task to the server. Lock the client object until the editor response is received.
	 */
	@Override
	public void run() {
		Task task = new EditorTask(userID, sessionID, keyPressed, client.getEditorTextArea(fileID).getCaretPosition(),
				fileID, authToken);
		client.getNetwork().sendTask(task);
		synchronized(client) {
			client.setWaiting(true);
			if (client.isWaiting()) {
				try {
					client.wait();
				} catch (InterruptedException ex) {
					// do something
				}
			}
		}

	}

	/**
	 * Get the user ID of the task.
	 * 
	 * @return
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Set the user ID of the task.
	 * 
	 * @param userID the user ID of the task
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * Get the session ID of the task.
	 * 
	 * @return the session ID of the task
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * Set the session ID of the task.
	 * 
	 * @param sessionID the session ID of the task
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Get the key pressed associated with this task.
	 * 
	 * @return they key pressed by the user
	 */
	public int getKeyPressed() {
		return keyPressed;
	}

	/**
	 * Set the key pressed for this task.
	 * 
	 * @param keyPressed the key pressed by the user
	 */
	public void setKeyPressed(int keyPressed) {
		this.keyPressed = keyPressed;
	}

	
	/**
	 * Get the index of the source edit in the file.
	 * 
	 * @return the edit index
	 */
	public int getEditIndex() {
		return editIndex;
	}

	/**
	 * Set the index of the source edit in the file.
	 * 
	 * @param editIndex the edit index
	 */
	public void setEditIndex(int editIndex) {
		this.editIndex = editIndex;
	}

	/**
	 * Get the file ID of the file edited.
	 * 
	 * @return the file ID
	 */
	public int getFileID() {
		return fileID;
	}

	/**
	 * Set the file ID of the file edited.
	 * 
	 * @param fileID the filed ID
	 */
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	/**
	 * Set the authorization token for the user performing the edit.
	 * 
	 * @return the authorization token
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * Get the authorization token for the user performing the edit.
	 * 
	 * @param authToken the authorization token
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

}
