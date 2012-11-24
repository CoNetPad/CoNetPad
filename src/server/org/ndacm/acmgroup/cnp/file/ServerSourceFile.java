package org.ndacm.acmgroup.cnp.file;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.DownloadFileTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.FileTask;
import org.ndacm.acmgroup.cnp.task.SendResponseTask;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

public class ServerSourceFile extends SourceFile {

	private CNPServer server;
	private ExecutorService fileTaskCourier;
	private ExecutorService fileTaskQueue;

	// clients that have this specific file open; maps userID to CNPConneciton
	private Map<Integer, CNPConnection> clientConnections;


	public ServerSourceFile(int fileID, String filename, SourceType type, String initialText, CNPServer server) {
		super(fileID, filename, type, initialText);

		fileTaskCourier = Executors.newCachedThreadPool();
		fileTaskQueue = Executors.newSingleThreadExecutor();
		clientConnections = new ConcurrentHashMap<Integer, CNPConnection>();
		this.server = server;
	}

	public ServerSourceFile(int fileID, String filename, SourceType type) {
		super(fileID, filename, type, "");
	}

	public void submitTask(FileTask task) {
		fileTaskQueue.submit(task);
	}

	public void executeTask(EditorTask task) {

		TaskResponse response = null;
		if (server.userIsAuth(task.getUserID(), task.getUserAuthToken())){
			editSource(task);

			// notify clients of edit
			response = new EditorTaskResponse(task.getUsername(), task.getKeyPressed(),
					task.getEditIndex(), task.getFileID(), true);
		} else {
			// user authentication failed
			response = new EditorTaskResponse("", -1, -1, -1, false);
		}
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

	public void addFileTaskEventListener(int userID, CNPConnection connection) {
		clientConnections.put(userID, connection);
	}

	public void removeFileTaskEventListener(int userID) {
		clientConnections.remove(userID);
	}




}
