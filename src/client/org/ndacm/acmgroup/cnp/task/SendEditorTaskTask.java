package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.CNPClient;

public class SendEditorTaskTask extends Task {
	
	private int userID;
	private int sessionID;
	private int keyPressed;
	private int editIndex;
	private int fileID;
	private String authToken;
	private final CNPClient client;
	
	public SendEditorTaskTask(int userID, int sessionID, int keyPressed,
			int fileID, String authToken, CNPClient client) {
		this.userID = userID;
		this.sessionID = sessionID;
		this.keyPressed = keyPressed;
		this.fileID = fileID;
		this.authToken = authToken;
		this.client = client;
	}

	@Override
	public void run() {
		Task task = new EditorTask(userID, sessionID, keyPressed, client.getEditorTextArea(fileID).getCaretPosition(),
				fileID, authToken);
		client.getNetwork().sendTask(task);
		synchronized(client) {
			client.setWaiting(true);
			if (client.isWaiting()) {
				try {
					client.wait();
				} catch (InterruptedException ex) {
					// do something
				}
			}
		}

	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public int getKeyPressed() {
		return keyPressed;
	}

	public void setKeyPressed(int keyPressed) {
		this.keyPressed = keyPressed;
	}

	public int getEditIndex() {
		return editIndex;
	}

	public void setEditIndex(int editIndex) {
		this.editIndex = editIndex;
	}

	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	
}
