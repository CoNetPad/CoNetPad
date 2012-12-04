package org.ndacm.acmgroup.cnp.task.response;

/**
 * The task response for opening files.
 * 
 * @author Josh Tan
 *
 */
public class OpenFileTaskResponse extends TaskResponse {


	private String filename;
	private int fileID;
	private String fileContent;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param fileID the ID of the file to open
	 * @param filename the filename of the file to open
	 * @param fileContent the String content of the file to open
	 * @param success whether the original task was a success or not
	 */
	public OpenFileTaskResponse(int fileID, String filename, String fileContent, boolean success) {
		this.fileID = fileID;
		this.filename = filename;
		this.fileContent = fileContent;
		this.success = success;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		client.executeTask(this);

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
	 * Get the success status for the task.
	 * 
	 * @return whether or not the original task was a success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Get the file content for the task.
	 * 
	 * @return the file content
	 */
	public String getFileContent() {
		return fileContent;
	}

	/**
	 * Set the file content for the task.
	 * 
	 * @param fileContent the file content
	 */
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
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
	 * Set the file ID for the task.
	 * 
	 * @param fileID the file ID
	 */
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

}
