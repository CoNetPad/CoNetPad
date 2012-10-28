package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.server.CNPSession;

public class EditorTaskResponse extends TaskResponse {

	private int keyPressed;
	private int editIndex;
	private String sourceFilename;

	public EditorTaskResponse(int keyPressed, int editIndex, String sourceFilename, CNPSession session) {
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.sourceFilename = sourceFilename;
		this.session = session;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// convert to JSON and send through sockets found in session CNPConnections
		// - collection of session CNPConnections maintained in CNPSession
		
	}

	@Override
	public void execute() {
		run();
		
	}
}
