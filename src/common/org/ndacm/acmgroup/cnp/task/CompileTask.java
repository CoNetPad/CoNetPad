package org.ndacm.acmgroup.cnp.task;

import java.util.List;

public class CompileTask extends SessionTask {

	protected int userID;
	protected List<String> sourceFilenames;
	protected String userAuthToken;
	
	public CompileTask(int userID, List<String> sourceFilenames, String userAuthToken) {
		this.userID = userID;
		this.sourceFilenames = sourceFilenames;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public int getUserID() {
		return userID;
	}

	public List<String> getSourceFilenames() {
		return sourceFilenames;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

}
