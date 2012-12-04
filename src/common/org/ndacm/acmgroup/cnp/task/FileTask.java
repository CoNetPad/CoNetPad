package org.ndacm.acmgroup.cnp.task;

/**
 * A task that should be executed by a server source file. Contains a reference to a server
 * source file so that this task can be submitted to the ServerSourceFile's ExecutorService.
 *
 */
public abstract class FileTask extends Task {

	protected int sessionID;
	protected int fileID;
	protected FileTaskExecutor file;

	/**
	 * Get the executor for this file task.
	 * 
	 * @return the file executor
	 */
	public FileTaskExecutor getFile() {
		return file;
	}

	/**
	 * Set the file task executor.
	 * @param file the task executor
	 */
	public void setFile(FileTaskExecutor file) {
		this.file = file;
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
	 * Set the session ID for the task.
	 * @param sessionID the session ID
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Get the file ID for the task.
	 * @return the file ID
	 */
	public int getFileID() {
		return fileID;
	}

	/**
	 * Set the file ID for the task.
	 * @param fileID the file ID
	 */
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	/**
	 * Get the filename for the task.
	 * 
	 * @return the filename
	 */
	public String getFilename() {
		return file.getFilename();
	}

}
