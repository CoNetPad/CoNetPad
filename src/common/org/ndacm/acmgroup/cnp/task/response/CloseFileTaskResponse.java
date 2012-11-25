package org.ndacm.acmgroup.cnp.task.response;

public class CloseFileTaskResponse extends TaskResponse {
	
	private int fileID;
	private int tabIndex;
	private boolean success;
	
	public CloseFileTaskResponse(int fileID, int tabIndex, boolean success) {
		this.fileID = fileID;
		this.tabIndex = tabIndex;
		this.success = success;
	}

	@Override
	public void run() {
		client.executeTask(this);
		
	}

	public int getFileID() {
		return fileID;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	 

}
