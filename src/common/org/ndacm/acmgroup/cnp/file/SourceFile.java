package org.ndacm.acmgroup.cnp.file;
import java.awt.event.KeyEvent;
import java.io.File;

import org.ahmadsoft.ropes.Rope;

import org.ndacm.acmgroup.cnp.task.EditorTask;



public class SourceFile {

	protected String filename;
	protected Rope sourceRope;
	protected SourceType type;

	public enum SourceType {
		JAVA,
		CPP,
		GENERAL
	}
	
	public SourceFile(String filename, SourceType type, String initialText) {
		this.filename = filename;
		this.type = type;
		sourceRope = Rope.BUILDER.build(initialText);
	}
	
	public void editSource(EditorTask task) {
		
		int keyPressed = task.getKeyPressed();
		int editIndex = task.getEditIndex();
		
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
	
	@Override
	public String toString() {
		return sourceRope.toString();
	}
}
