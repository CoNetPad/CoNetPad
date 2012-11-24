package org.ndacm.acmgroup.cnp.task.response;

import javax.swing.text.BadLocationException;

public class EditorTaskResponse extends TaskResponse {

	private String username;
	private int keyPressed;
	private int editIndex;
	private int fileID;
	private boolean isSuccess;

	public EditorTaskResponse(String username, int keyPressed, int editIndex,
			int fileID, boolean isSuccess) {
		this.username = username;
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.fileID = fileID;
		this.isSuccess = isSuccess;
	}

	public int getKeyPressed() {
		return keyPressed;
	}

	public int getEditIndex() {
		return editIndex;
	}

	public String getUsername() {
		return username;
	}

	public int getFileID() {
		return fileID;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Override
	public void run() {
		try {
			client.executeTask(this);
		} catch (BadLocationException e) {
			// do something
		}

	}

}
