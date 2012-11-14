package org.ndacm.acmgroup.cnp.task;

import java.util.List;

public class CompileTask extends Task {

	private int userID;
	private List<String> sourceFilenames;
	private String userAuthToken;
	
	public CompileTask(int userID, List<String> sourceFilenames, String userAuthToken) {
		this.userID = userID;
		this.sourceFilenames = sourceFilenames;
		this.userAuthToken = userAuthToken;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
