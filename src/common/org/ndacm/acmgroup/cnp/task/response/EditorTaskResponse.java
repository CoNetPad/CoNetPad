package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for editing a source file.
 * 
 * @author Josh Tan
 *
 */
public class EditorTaskResponse extends TaskResponse {

	private String username;
	private int keyPressed;
	private int editIndex;
	private int fileID;
	private boolean isSuccess;

	/**
	 * Default constructor.
	 * 
	 * @param username the username for the task
	 * @param keyPressed the int of the key pressed
	 * @param editIndex the index of the source edit
	 * @param fileID the ID of the file edited
	 * @param isSuccess the success status of the task
	 */
	public EditorTaskResponse(String username, int keyPressed, int editIndex,
			int fileID, boolean isSuccess) {
		this.username = username;
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.fileID = fileID;
		this.isSuccess = isSuccess;
	}

	/**
	 * Get the key pressed.
	 * 
	 * @return the key pressed
	 */
	public int getKeyPressed() {
		return keyPressed;
	}

	/**
	 * Get the index of the edit.
	 * 
	 * @return the index of the edit
	 */
	public int getEditIndex() {
		return editIndex;
	}

	/**
	 * Get the username of the editor.
	 * 
	 * @return the username of the editor
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the ID of the edited file.
	 * 
	 * @return the ID of the edited file
	 */
	public int getFileID() {
		return fileID;
	}

	/**
	 * Get the success status of the edit task.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * Set the success status of the edit task.
	 * 
	 * @param isSuccess the success status
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);
	}

}
