package org.ndacm.acmgroup.cnp.file;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.ahmadsoft.ropes.Rope;

/**
 * This is used for handing server and client based source file
 * 
 * @author Josh Tan
 * @version 1.0
 */
public abstract class SourceFile {

	private int fileID; // The unique file ID
	protected String filename; // the unique file name
	protected Rope sourceRope; // The Rope class for handing edits
	protected SourceType type; // The file type of the file
	protected File file;

	/**
	 * This is an enumeration for file types.
	 * 
	 * @author Josh Tan
	 * 
	 */
	public enum SourceType {
		JAVA, CPP, GENERAL
	}

	/**
	 * This creates a new instance of the SourceFile
	 * 
	 * @param fileID
	 *            A unique ID for the file
	 * @param filename
	 *            A unique name for the file
	 * @param type
	 *            The type of file
	 * @param initialText
	 *            The initial text or cntent
	 */
	public SourceFile(int fileID, String filename, SourceType type,
			String initialText) {
		this.fileID = fileID;
		this.type = type;
		this.sourceRope = Rope.BUILDER.build(initialText);
		this.file = new File(filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Error creating file " + filename);
				this.file = null;
			}
		}
		this.filename = filename;
	}

	/**
	 * This creates a new instance of the SourceFile
	 * 
	 * @param fileID
	 *            A unique ID for the file
	 * @param filename
	 *            A unique name for the file
	 * @param type
	 *            The type of file
	 */
	public SourceFile(int fileID, String filename, SourceType type) {
		this.fileID = fileID;
		this.filename = filename;
		this.type = type;

		this.file = new File(filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Error creating file " + filename);
				this.file = null;
			}
		}

		StringBuffer content = new StringBuffer();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(
					file.getAbsolutePath());
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				content.append(strLine);
			}
			// Close the input stream
			in.close();
			this.sourceRope = Rope.BUILDER.build(content);
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			this.sourceRope = Rope.BUILDER.build("");
		}

	}

	/**
	 * This edits the source file via a key stroke
	 * 
	 * @param keyPressed
	 *            The integer value of the key that is pressed
	 * @param editIndex
	 *            The index of the character or space that is being edited
	 */
	public boolean editSource(int keyPressed, int editIndex) {

		try {
			if (keyPressed == KeyEvent.VK_BACK_SPACE) {
				sourceRope = sourceRope.delete(editIndex - 1, editIndex);
			} else if (keyPressed == Event.DELETE) {
				sourceRope = sourceRope.delete(editIndex, editIndex + 1);
			} else {
				Character tmp = (char) keyPressed;
				sourceRope = sourceRope.insert(editIndex,
						Character.toString(tmp));
				if (keyPressed == 10) {
					sourceRope = sourceRope.insert(editIndex,
							Character.toString('\r'));
				}
			}
			return true;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This returns the sourceFile as a file object
	 * 
	 * @return The file object of the source file
	 */
	public File toFile() {
		return file;
	}

	/**
	 * This returns the Unique ID of the file
	 * 
	 * @return The unique ID of the file
	 */
	public int getFileID() {
		return fileID;
	}

	/**
	 * This gets the name of the FIle.
	 * 
	 * @return The name of the file
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

	public void save() {
		file.delete();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(sourceRope.toString());
			out.close();
		} catch (IOException e) {
		}
	}
}
