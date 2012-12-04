package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;

/**
 * Task response for creating a file.
 * 
 * @author Josh Tan
 *
 */
public class CreateFileTaskResponse extends TaskResponse {

	private int fileID;
	protected int userID;
	private String filename;
	private SourceType type;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param fileID the file ID for the task
	 * @param userID the user ID for the task
	 * @param filename the filename for the task
	 * @param type the file type for the task
	 * @param success the success status for the task
	 */
	public CreateFileTaskResponse(int fileID, int userID, String filename, SourceType type,
			boolean success) {
		this.fileID = fileID;
		this.userID = userID;
		this.filename = filename;
		this.type = type;
		this.success = success;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);
	}

	/**
	 * Get the file ID for the task.
	 * 
	 * @return the file ID
	 */
	public int getFileID() {
		return fileID;
	}

	/**
	 * Get the filename for the task.
	 * 
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Get the file type for the task.
	 * 
	 * @return the file type
	 */
	public SourceType getType() {
		return type;
	}

	/**
	 * Get the success status of the task.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
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
	 * Set the user ID for the task.
	 * 
	 * @param userID the user ID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

}
