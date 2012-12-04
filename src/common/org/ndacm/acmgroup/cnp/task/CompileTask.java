package org.ndacm.acmgroup.cnp.task;

import java.util.List;
/**
 * This is the task for compiling a file.
 * 
 * @author Cesar Ramirez
 */
public class CompileTask extends SessionTask {

	protected int userID;
	protected List<String> sourceFilenames;
	protected String userAuthToken;

	/**
	 * Default Constructor
	 * @param userID				The unique ID of the user who initialized the compile
	 * @param sourceFilenames		The list of source files to compile
	 * @param userAuthToken			The authorized token of the user who initialized 
	 */
	public CompileTask(int userID, List<String> sourceFilenames, String userAuthToken) {
		this.userID = userID;
		this.sourceFilenames = sourceFilenames;
		this.userAuthToken = userAuthToken;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		session.executeTask(this);
	}

	/**
	 * This gets the Unique ID of the user
	 * @return	The unique User ID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * This gets the list of file names that were to be compile
	 * @return		The list of files to be compiled
	 */
	public List<String> getSourceFilenames() {
		return sourceFilenames;
	}

	/**
	 * This gets the user's authorized token
	 * @return	The user authorized token
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

}
