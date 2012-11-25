package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

public class CloseFileTask extends SessionTask {

	protected int userID;
	protected int fileID;
	private int tabIndex;
	protected String userAuthToken;
	protected CNPConnection connection;
	
	
	public CloseFileTask(int userID, int fileID, int tabIndex, String userAuthToken) {
		this.userID = userID;
		this.fileID = fileID;
		this.tabIndex = tabIndex;
		this.userAuthToken = userAuthToken;
	}
	
	@Override
	public void run() {
		session.executeTask(this);
		
	}

	public int getUserID() {
		return userID;
	}

	public int getFileID() {
		return fileID;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

	public CNPConnection getConnection() {
		return connection;
	}

	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	
	
	

}
