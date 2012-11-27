package org.ndacm.acmgroup.cnp.file;
import java.awt.event.KeyEvent;
import java.io.File;

import org.ahmadsoft.ropes.Rope;


/**
 * This is used for handing server and client based source file
 * @author Josh Tan
 * @version 1.0
 */
public abstract class SourceFile {

	private int fileID;					//The unique file ID
	protected String filename;			//the unique file name
	protected Rope sourceRope;			//The Rope class for handing edits
	protected SourceType type;			//The file type of the file
	protected File file;
	
	/**
	 * This is an enumeration for file types.
	 * @author Josh Tan
	 *
	 */
	public enum SourceType {
		JAVA,
		CPP,
		GENERAL
	}

	/**
	 * This creates a new instance of the SourceFile
	 * @param fileID				A unique ID for the file
	 * @param filename				A unique name for the file
	 * @param type					The type of file
	 * @param initialText			The initial text or cntent
	 */
	public SourceFile(int fileID, String filename, SourceType type, String initialText) {
		this.fileID = fileID;
		this.filename = filename;
		this.type = type;
		sourceRope = Rope.BUILDER.build(initialText);
	}

	/**
	 * This edits the source file via a key stroke
	 * @param keyPressed			The integer value of the key that is pressed
	 * @param editIndex				The index of the character or space that is being edited
	 */
	public void editSource(int keyPressed, int editIndex) {

		if (keyPressed == KeyEvent.VK_BACK_SPACE) {
			sourceRope = sourceRope.delete(editIndex, editIndex + 1);
		} else {
			sourceRope = sourceRope.insert(editIndex, Character.toString((char) keyPressed));
		}	

	}

	/**
	 * This returns the sourceFile as a file object
	 * @return		The file object of the source file
	 */
	public File toFile() {
		// TODO implement
		return new File("");
	}

	/**
	 * This returns the Unique ID of the file
	 * @return			The unique ID of the file
	 */
	public int getFileID() {
		return fileID;
	}


	/**
	 * This gets the name of the FIle.
	 * @return		The name of the file
	 */
	public String getFilename() {
		return filename;
	}
	
	public SourceType getType() {
		return type;
	}

	public void setType(SourceType type) {
		this.type = type;
	}
	
	/**
	 * This gets the file's content as a string
	 */
	@Override
	public String toString() {
		return sourceRope.toString();
	}
}
