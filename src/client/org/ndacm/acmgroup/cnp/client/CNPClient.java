package org.ndacm.acmgroup.cnp.client;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.net.ssl.SSLSocket;

import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.DownloadTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;

public class CNPClient {

	SSLSocket socket;
	String serverURL;
	String sessionName;
	ExecutorService clientExecutor;
	Map<String, SourceFile> sourceFiles; // implemented with ConcurrentHashMap
	
	public CNPClient(){
		// TODO implement
	}
	
	public boolean connect(String serverURL, String sessionName) {
		// TODO implement
		return false;
	}
	
	public boolean compile(List<String> fileNames) {
		// TODO implement
		return false;
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
	
	public boolean executeTask(DownloadTask task) {
		// TODO implement
		return false;
	}
	
	public boolean sendChatMessage(String message) {
		// TODO implement
		return false;
	}
	
}
