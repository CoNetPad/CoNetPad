package org.ndacm.acmgroup.cnp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.text.BadLocationException;

import org.ndacm.acmgroup.cnp.file.ClientSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.gui.MainFrame;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.DownloadFileTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;



public class CNPClient implements TaskReceivedEventListener {

	private SSLSocketFactory sslSocketFactory;
	private SSLSocket socket;
	private Writer serverOut;
	private Reader serverIn;

	private String serverURL;
	private String sessionName;
	private ExecutorService clientExecutor;
	private Map<Integer, ClientSourceFile> sourceFiles; // fileID - ClientSourceFile
	
	private int userID; // ID of account logged in as
	private String authToken; // assigned by server after authentication
	private MainFrame sourceFrame;

	public CNPClient(String serverURL){

		this.serverURL = serverURL;
		sourceFiles = new ConcurrentHashMap<Integer, ClientSourceFile>();
		clientExecutor = Executors.newCachedThreadPool();
		sourceFrame = new MainFrame();

		// https://www.securecoding.cert.org/confluence/display/java/MSC00-J.+Use+SSLSocket+rather+than+Socket+for+secure+data+exchange
		sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		
		try {
			
			socket = (SSLSocket) sslSocketFactory.createSocket("localhost", 9999);
			serverOut = new PrintWriter(socket.getOutputStream(), true);
			serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		} catch (IOException e) {
			// try to handle it
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					System.err.println("Error closing client socket.");
					e.printStackTrace();
				}
			}
		}
	}


	public boolean connect(String serverURL, String sessionName) {
		// TODO implement
		return false;
	}

	public boolean compile(List<String> fileNames) {
		// TODO implement
		return false;
	}
	
	public void addSourceFile(int fileID, String filename, SourceType type) {
		sourceFiles.put(fileID, new ClientSourceFile(fileID, filename, type, "", this));
	}

	/**
	 * Convert the SourceFile with the given filename to a File
	 * and return this file.
	 * @param fileName The filename of the SourceFile to return.
	 * @return The specified SourceFile converted to a File.
	 */
	public File getSourceFile(String fileName) {
		File file = sourceFiles.get(fileName).toFile();
		return file;
	}

	/**
	 * Convert all the SourceFiles for the session that the client is
	 * connected to actual Files. Return these Files in a List.
	 * @return A List of Files derived from the session SourceFiles.
	 */
	public List<File> getAllSourceFiles() {
		LinkedList<File> list = new LinkedList<File>();
		for (SourceFile srcFile : sourceFiles.values()) {
			File file = srcFile.toFile();
			list.add(file);
		}
		return list;
	}

	public boolean executeTask(ChatTask task) {
		// TODO implement
		return false;
	}

	public boolean executeTask(EditorTask task) {
		// TODO implement
		return false;
	}

	public boolean executeTask(DownloadFileTask task) {
		// TODO implement
		return false;
	}

	public boolean sendChatMessage(String message) {
		// TODO implement
		return false;
	}
	
	public void editSource(EditorTaskResponse task) throws BadLocationException {
		task.getFile().editSource(task);
		sourceFrame.updateSourceTab(task.getFileID(), task.getKeyPressed(), task.getEditIndex());
	}


	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {
		clientExecutor.submit(evt.getTask());
		
	}


}
