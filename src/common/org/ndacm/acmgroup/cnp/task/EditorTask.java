package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;

/**
 * A task to edit a source file. An EditorTask is issued by a single person and is 
 * represents a key press on a single file in a single session.
 * 
 */
public class EditorTask extends Task {
	
	/**
	 * The permission level that is required to execute this task.
	 */
	public static FilePermissionLevel PERMISSION = FilePermissionLevel.READ_WRITE;

	private int userID;
	private int keyPressed;
	private int editIndex;
	private String filename;
	private String userAuthToken;
		
	public EditorTask(int userID, int keyPressed, int editIndex, String filename, String userAuthToken) {
		this.userID = userID;
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.filename = filename;
		this.userAuthToken = userAuthToken;
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
	
	public String getFilename() {
		return filename;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


}
