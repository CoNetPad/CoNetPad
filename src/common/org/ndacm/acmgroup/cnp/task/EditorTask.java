package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.Account.FilePermissionLevel;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.server.CNPSession;

/**
 * A task to edit a source file. An EditorTask is issued by a single person and is 
 * represents a key press on a single file in a single session.
 * 
 */
public class EditorTask extends Task implements Runnable{
	
	/**
	 * The permission level that is required to execute this task.
	 */
	public static FilePermissionLevel PERMISSION;

	private Account userAccount;
	private int keyPressed;
	private int editIndex;
	private ServerSourceFile sourceFile;
	
	/**
	 * Fully-specified constructor for EditorTask.
	 * 
	 * @param userAccount The account of the user who created the edit task.
	 * @param keyEntered The char associated with pressed key.
	 * @param editIndex The 0-based index of the edit within the source file.
	 * @param fileName The name of the file that was edited.
	 * @param session The name/identifier for the CoNetPad session.
	 */
	public EditorTask(Account userAccount, int keyPressed, int editIndex, ServerSourceFile sourceFile) {
		
		PERMISSION = FilePermissionLevel.READ_WRITE;
		
		this.userAccount = userAccount;
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.sourceFile = sourceFile;
	}
	
	public Account getUserAccount() {
		return userAccount;
	}
	
	public ServerSourceFile getSourceFile() {
		return sourceFile;
	}
	
	public int getKeyPressed() {
		return keyPressed;
	}
	
	public int getEditIndex() {
		return editIndex;
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
		
		sourceFile.editSource(this);
	}

}
