package org.ndacm.acmgroup.cnp.task;

import java.util.List;

public class DownloadFileTask extends Task {

	private int userID;
	private List<String> filenames;
	private DownloadType type;
	private String userAuthToken;
	
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

}