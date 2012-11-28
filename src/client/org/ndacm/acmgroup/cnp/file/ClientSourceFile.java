package org.ndacm.acmgroup.cnp.file;

import org.ndacm.acmgroup.cnp.CNPClient;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;


/**
 * This is a class for handing a source file for the clients
 * @author Josh Tan, Cesar Ramirez
 * @version 1.0
 */
public class ClientSourceFile extends SourceFile{
	
	private CNPClient client;			//The CNP Client object of the client who is working on the file
	
	
	
	/**
	 * This creates a new ClientSourceFile
	 * @param fileID				A unique file ID for the file
	 * @param filename				A unique name for the file to be used - Dont have file type
	 * @param type					The type of the new file
	 * @param initialText			The initial text or content o the file
	 * @param client				The client object of the user editing the file
	 */
	public ClientSourceFile(int fileID, String filename, SourceType type,
			String initialText, CNPClient client) {
		super(fileID, filename, type, initialText);
		this.client = client;
	}
	/**
	 * This edits the source file via EditorTaskResponse
	 * @param task			The EditorTaskResponse used to edit the file
	 */
	public boolean editSource(EditorTaskResponse task) {
		return editSource(task.getKeyPressed(),task.getEditIndex());
	}
	
	/**
	 * This gets the client object of the client editing the file
	 * @return		The Client object of the file
	 */
	public CNPClient getClient() {
		return client;
	}

}
