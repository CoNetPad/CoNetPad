package org.ndacm.acmgroup.cnp.file;

import org.ndacm.acmgroup.cnp.CNPClient;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;

public class ClientSourceFile extends SourceFile{
	
	private CNPClient client;
	
	public ClientSourceFile(int fileID, String filename, SourceType type,
			String initialText, CNPClient client) {
		super(fileID, filename, type, initialText);
		this.client = client;
	}

	public void editSource(EditorTaskResponse task) {
		editSource(task.getKeyPressed(),task.getEditIndex());
	}
	
	public CNPClient getClient() {
		return client;
	}

}
