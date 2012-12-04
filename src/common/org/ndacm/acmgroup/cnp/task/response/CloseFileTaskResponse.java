package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for closing a session source file.
 * 
 * @author Josh Tan
 *
 */
public class CloseFileTaskResponse extends TaskResponse {
	
	private int fileID;
	private int tabIndex;
	private boolean success;
	
	/**
	 * Default constructor.
	 * @param fileID the file ID for the task
	 * @param tabIndex the index of the tab for the task
	 * @param success the success status of the task
	 */
	public CloseFileTaskResponse(int fileID, int tabIndex, boolean success) {
		this.fileID = fileID;
		this.tabIndex = tabIndex;
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
	 * Get the success status for the task.
	 * 
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Get the tab index for the task.
	 * 
	 * @return the tab index
	 */
	public int getTabIndex() {
		return tabIndex;
	}

	/**
	 * Set the tab index for the task.
	 * 
	 * @param tabIndex the tab index
	 */
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	} 

}
