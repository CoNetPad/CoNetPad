package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.file.ServerSourceFile;

/**
 * A task that should be executed by a server source file. Contains a reference to a server
 * source file so that this task can be submitted to the ServerSourceFile's ExecutorService.
 *
 */
public abstract class FileTask extends Task {

	
	protected int sessionID;
	protected int fileID;
	protected ServerSourceFile file;

	public ServerSourceFile getFile() {
		return file;
	}

	public void setFile(ServerSourceFile file) {
		this.file = file;
	}

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	
	public String getFilename() {
		return file.getFilename();
	}
	
}
 