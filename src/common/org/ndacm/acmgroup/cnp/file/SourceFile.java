package org.ndacm.acmgroup.cnp.file;
import java.awt.event.KeyEvent;
import java.io.File;

import org.ahmadsoft.ropes.Rope;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;



public abstract class SourceFile {

	private int fileID;
	protected String filename;
	protected Rope sourceRope;
	protected SourceType type;

	public enum SourceType {
		JAVA,
		CPP,
		GENERAL
	}
	
	public SourceFile(int fileID, String filename, SourceType type, String initialText) {
		this.fileID = fileID;
		this.filename = filename;
		this.type = type;
		sourceRope = Rope.BUILDER.build(initialText);
	}
	
	public void editSource(int keyPressed, int editIndex) {

		if (keyPressed == KeyEvent.VK_BACK_SPACE) {
			sourceRope = sourceRope.delete(editIndex, editIndex + 1);
		} else {
			sourceRope = sourceRope.insert(editIndex, Character.toString((char) keyPressed));
		}	

	}

	
	public File toFile() {
		// TODO implement
		return new File("");
	}
	
	public int getFileID() {
		return fileID;
	}
	
	public String getFilename() {
		return filename;
	}
	
	@Override
	public String toString() {
		return sourceRope.toString();
	}
}
