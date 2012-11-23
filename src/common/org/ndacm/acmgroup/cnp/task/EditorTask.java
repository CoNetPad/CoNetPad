package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;

/**
 * A task to edit a source file. An EditorTask is issued by a single person and
 * is represents a key press on a single file in a single session.
 * 
 */
public class EditorTask extends FileTask {

	/**
	 * The permission level that is required to execute this task.
	 */
	public static FilePermissionLevel PERMISSION = FilePermissionLevel.READ_WRITE;

	protected int userID;
	private String username;
	private int sessionID;
	protected int keyPressed;
	protected int editIndex;
	protected int fileID;
	protected String userAuthToken;

	public EditorTask(int userID, int sessionID, int keyPressed, int editIndex,
			int fileID, String userAuthToken) {

		this.userID = userID;
		this.sessionID = sessionID;
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.fileID = fileID;
		this.userAuthToken = userAuthToken;

	}

	@Override
	public void run() {

		file.executeTask(this);

	}

	public int getKeyPressed() {
		return keyPressed;
	}

	public int getEditIndex() {
		return editIndex;
	}

	public int getUserID() {
		return userID;
	}

	public int getSessionID() {
		return sessionID;
	}

	public String getUsername() {
		return username;
	}

	public static FilePermissionLevel getPERMISSION() {
		return PERMISSION;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

	public int getFileID() {
		return fileID;
	}

}
