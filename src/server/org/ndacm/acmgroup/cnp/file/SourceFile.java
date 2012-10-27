package org.ndacm.acmgroup.cnp.file;

import java.io.File;
import org.ahmadsoft.ropes.Rope;
import org.ndacm.acmgroup.cnp.task.EditorTask;

public class SourceFile {

	private Rope sourceRope;
	private SourceFile.SourceType type;

	public enum SourceType {
		JAVA,
		CPP,
		GENERAL
	}
	
	public boolean editSource(EditorTask task) {
		// TODO implement
		return false;
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
