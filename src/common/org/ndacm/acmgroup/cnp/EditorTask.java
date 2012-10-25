package org.ndacm.acmgroup.cnp;

import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;

/**
 * A task to edit a source file. An EditorTask is issued by a single person and is 
 * represents a key press on a single file in a single session.
 * 
 */
public class EditorTask extends CNPTask implements Runnable{
	
	/**
	 * The permission level that is required to execute this task.
	 */
	public static FilePermissionLevel PERMISSION;

	private String username;
	private char keyEntered;
	private int editIndex;
	private String fileName;
	private CNPSession session;
	
	/**
	 * Fully-specified constructor for EditorTask.
	 * 
	 * @param username The username of the user who created the edit task.
	 * @param keyEntered The char associated with pressed key.
	 * @param editIndex The 0-based index of the edit within the source file.
	 * @param fileName The name of the file that was edited.
	 * @param session The name/identifier for the CoNetPad session.
	 */
	public EditorTask(String username, char keyEntered, int editIndex, String fileName, CNPSession session) {
		
		PERMISSION = FilePermissionLevel.READ_WRITE;
		
		this.username = username;
		this.keyEntered = keyEntered;
		this.editIndex = editIndex;
		this.fileName = fileName;
		this.session = session;
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
