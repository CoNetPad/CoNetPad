package org.ndacm.acmgroup.cnp.task.response;

public class EditorTaskResponse {

	private int keyPressed;
	private int editIndex;
	private String sourceFilename;
	
	
	public EditorTaskResponse(int keyPressed, int editIndex, String sourceFilename) {
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.sourceFilename = sourceFilename;
	}
}
