package org.ndacm.acmgroup.cnp.task.response;

public class EditorTaskResponse extends TaskResponse {
	
	private String userName;
	private int keyPressed;
	private int editIndex;
	private String filename;
	
	public EditorTaskResponse(String userName, int keyPressed, int editIndex,
			String filename) {
		this.userName = userName;
		this.keyPressed = keyPressed;
		this.editIndex = editIndex;
		this.filename = filename;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public String getUserName() {
		return userName;
	}

	public int getKeyPressed() {
		return keyPressed;
	}

	public int getEditIndex() {
		return editIndex;
	}

	public String getFilename() {
		return filename;
	}

}
