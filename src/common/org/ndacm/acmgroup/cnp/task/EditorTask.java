package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

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

	protected int userID;
	private String username;
	private int sessionID;
	protected int keyPressed;
	protected int editIndex;
	protected SourceFile file;
	protected String userAuthToken;

	public EditorTask(int userID, String username, int sessionID, int keyPressed, 
			int editIndex, SourceFile file, String userAuthToken) {
		this.userID = userID;
		this.username = username;
		this.sessionID = sessionID;
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.file = file;
		this.userAuthToken = userAuthToken;
	}

	public EditorTask(int userID, int sessionID, int keyPressed, 
			int editIndex, SourceFile file, String userAuthToken) {
		this(userID, "", sessionID, keyPressed, editIndex, file, userAuthToken);

	}

	@Override
	public void run() {
		// edit source file
		if (file instanceof ServerSourceFile) {
			ServerSourceFile serverFile = (ServerSourceFile) file;
			serverFile.editSource(this);

			// notify clients of edit
			TaskResponse response = new EditorTaskResponse(username, keyPressed, editIndex, file.getFileID());
			serverFile.distributeTask(response);
		}

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



	public SourceFile getFile() {
		return file;
	}

	public String getFilename() {
		return file.getFilename();
	}

	public static FilePermissionLevel getPERMISSION() {
		return PERMISSION;
	}


	public String getUserAuthToken() {
		return userAuthToken;
	}


}
