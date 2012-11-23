package org.ndacm.acmgroup.cnp.file;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.DownloadFileTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.SendResponseTask;
import org.ndacm.acmgroup.cnp.task.TaskRequest;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

public class ServerSourceFile extends SourceFile {

	private ExecutorService fileTaskCourier;
	private ExecutorService fileTaskQueue;
	private Map<Account, CNPConnection> clientConnections; // clients that have this specific file open


	public ServerSourceFile(int fileID, String filename, SourceType type, String initialText) {
		super(fileID, filename, type, initialText);

		fileTaskCourier = Executors.newCachedThreadPool();
		fileTaskQueue = Executors.newSingleThreadExecutor();
		clientConnections = new ConcurrentHashMap<Account, CNPConnection>();
	}

	public ServerSourceFile(int fileID, String filename, SourceType type) {
		super(fileID, filename, type, "");
	}
	
	public void submitTask(TaskRequest task) {
		fileTaskQueue.submit(task);
	}

	public void executeTask(EditorTask task) {
		
		editSource(task);

		// notify clients of edit
		TaskResponse response = new EditorTaskResponse(task.getUsername(), task.getKeyPressed(),
				task.getEditIndex(), task.getFileID());
		distributeTask(response);
		
	}

	public boolean executeTask(DownloadFileTask task) {
		// TODO implement
		return false;
	}

	public void editSource(EditorTask task) {
		editSource(task.getKeyPressed(), task.getEditIndex());
	}

	public void distributeTask(TaskResponse response) {
		for (CNPConnection client : clientConnections.values()) {
			SendResponseTask fileTask = new SendResponseTask(response, client);
			fileTaskCourier.submit(fileTask);
		}
	}



}
