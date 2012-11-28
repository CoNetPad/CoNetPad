package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;
/**
 * This is a task for closing a file
 * @author Cesar Ramirez, Josh Tan
 *
 */
public class CloseFileTask extends SessionTask {

	protected int userID;
	protected int fileID;
	private int tabIndex;
	protected String userAuthToken;
	protected CNPConnection connection;
	
	/**
	 * Default Constructor
	 * @param userID			The database ID of the user who wishes to close the file
	 * @param fileID			The unique ID of the file that is to be closes
	 * @param tabIndex			The index of the text-cursor
	 * @param userAuthToken		The user authorized token
	 */
	public CloseFileTask(int userID, int fileID, int tabIndex, String userAuthToken) {
		this.userID = userID;
		this.fileID = fileID;
		this.tabIndex = tabIndex;
		this.userAuthToken = userAuthToken;
	}
	
	/**
	 * This runs the task in a thread
	 */
	@Override
	public void run() {
		session.executeTask(this);
		
	}
	
	/**
	 * This returns the User Id of the task initializer
	 * @return		The User ID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * This gets the Fild ID of the file that is to be closes
	 * @return		The unique file id
	 */
	public int getFileID() {
		return fileID;
	}

	/**
	 * This gets the user authorized token of initializer
	 * @return		The User authorized token
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}
	
	/**
	 * The CNP connection of the user 
	 * @return		The CNP Connection of the user
	 */
	public CNPConnection getConnection() {
		return connection;
	}

	/**
	 * This sets the connection of the Task of the user who initialized the task
	 * @param connection		Connection of the user who initialized the task
	 */
	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}
	
	/**
	 * The current location of the text cursor for editing
	 * @return		The Location of the text curosr
	 */
	public int getTabIndex() {
		return tabIndex;
	}

	/**
	 * Set the location of the text cursor
	 * @param tabIndex		The index to move the text cursor
	 */
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	
	
	

}
