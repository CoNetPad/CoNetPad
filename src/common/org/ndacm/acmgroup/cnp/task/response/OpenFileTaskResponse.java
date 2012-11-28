package org.ndacm.acmgroup.cnp.task.response;

/**
 * @author cesar
 *
 */
public class OpenFileTaskResponse extends TaskResponse {

	/**
	 * 
	 */
	private String filename;
	/**
	 * 
	 */
	private int fileID;
	/**
	 * 
	 */
	private String fileContent;
	/**
	 * 
	 */
	private boolean success;

	/**
	 * @param fileID
	 * @param filename
	 * @param fileContent
	 * @param success
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
	 * @return
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @return
	 */
	public String getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent
	 */
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * @return
	 */
	public int getFileID() {
		return fileID;
	}

	/**
	 * @param fileID
	 */
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	
	
	
	

}
