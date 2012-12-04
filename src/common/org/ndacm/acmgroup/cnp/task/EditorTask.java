package org.ndacm.acmgroup.cnp.task;

/**
 * A task to edit a source file. An EditorTask is issued by a single person and
 * is represents a key press on a single file in a single session.
 * 
 */
public class EditorTask extends FileTask {

	protected int userID;
	private String username;
	private int sessionID;
	protected int keyPressed;
	protected int editIndex;
	protected int fileID;
	protected String userAuthToken;

	/**
	 * Default constructor.
	 * 
	 * @param userID the user ID for the task
	 * @param sessionID the session ID for the task
	 * @param keyPressed the int of the key pressed
	 * @param editIndex the edit index for the task
	 * @param fileID the file ID for the task
	 * @param userAuthToken the user authentication token for the task
	 */
	public EditorTask(int userID, int sessionID, int keyPressed, int editIndex,
			int fileID, String userAuthToken) {
		this.userID = userID;
		this.sessionID = sessionID;
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.fileID = fileID;
		this.userAuthToken = userAuthToken;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {

		file.executeTask(this);
	}

	/**
	 * Get the key pressed for the task.
	 * 
	 * @return the key pressed
	 */
	public int getKeyPressed() {
		return keyPressed;
	}

	/**
	 * Get the edit index for the task.
	 * 
	 * @return the edit index
	 */
	public int getEditIndex() {
		return editIndex;
	}

	/**
	 * Get the user ID for the task.
	 * 
	 * @return the user ID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Get the session ID for the task.
	 * 
	 * @return the session ID
	 */
	public int getSessionID() {
		return sessionID;
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
	 * Get the user authentication token for the task.
	 * 
	 * @return the user authentication token
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

	/**
	 * Get the file ID for the task.
	 *  
	 * @return the file ID
	 */
	public int getFileID() {
		return fileID;
	}
}
