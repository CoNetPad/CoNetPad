package org.ndacm.acmgroup.cnp.file;

import java.util.concurrent.ExecutorService;
import org.ndacm.acmgroup.cnp.server.CNPSession;
import org.ndacm.acmgroup.cnp.task.DownloadTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;

public class ServerSourceFile extends SourceFile {

	private CNPSession session;
	private ExecutorService taskQueue;
	
	public boolean addTask(EditorTask task) {
		// TODO implement
		return false;
	}
	
	public boolean addTask(DownloadTask task) {
		// TODO implement
		return false;
	}
	
}
