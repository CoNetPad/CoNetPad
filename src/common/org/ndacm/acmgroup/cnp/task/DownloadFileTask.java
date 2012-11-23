package org.ndacm.acmgroup.cnp.task;

import java.util.List;

public class DownloadFileTask extends TaskRequest {

	protected int userID;
	protected List<String> filenames;
	protected DownloadType type;
	protected String userAuthToken;
	
	public DownloadFileTask(int userID, List<String> filenames, DownloadType type, String userAuthToken) {
		this.userID = userID;
		this.filenames = filenames;
		this.type = type;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public enum DownloadType {
		BINARY,
		SOURCE
	}

	public int getUserID() {
		return userID;
	}

	public List<String> getFilenames() {
		return filenames;
	}

	public DownloadType getType() {
		return type;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}