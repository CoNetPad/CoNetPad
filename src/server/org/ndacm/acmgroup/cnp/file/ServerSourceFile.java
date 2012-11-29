package org.ndacm.acmgroup.cnp.file;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.FileTask;
import org.ndacm.acmgroup.cnp.task.FileTaskExecutor;
import org.ndacm.acmgroup.cnp.task.SendResponseTask;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

/**
 * A source file on the server end of the application.
 * 
 * @author Cesar Ramirez, Josh Tan
 * @version 1.5
 */
public class ServerSourceFile extends SourceFile implements FileTaskExecutor {

	/**
	 * The executor for tasks that are to be distributed to clients registered
	 * as listeners on the file. Multithreaded to allow for maximum efficiency
	 * in distribution.
	 */
	private ExecutorService fileTaskCourier;
						
	/**
	 * The executor for tasks to be executed. Single threaded so that tasks
	 * are executed in the order they are received.
	 */
	private ExecutorService fileTaskQueue;
	
	private Map<Integer, CNPConnection> clientConnections;
	private CNPServer server;

	/**
	 * Default constructor.
	 * 
	 * @param fileID the unique ID of the file
	 * @param filename the name of the source file
	 * @param type the type of the source file (e.g. Java, C++, etc.)
	 */
	public ServerSourceFile(int fileID, String filename, SourceType type,
			CNPServer server) {
		super(fileID, filename, type);
		fileTaskCourier = Executors.newCachedThreadPool();
		fileTaskQueue = Executors.newSingleThreadExecutor();
		clientConnections = new ConcurrentHashMap<Integer, CNPConnection>();
		this.server = server;
	}

	/**
	 * Submit a task to the task queue.
	 * 
	 * @param task the FileTask to be enqueued.
	 */
	public void submitTask(FileTask task) {
		fileTaskQueue.submit(task);
	}

	/**
	 * Execute a task for editing a source file.
	 * 
	 * @param task the editor task to execute
	 */
	public void executeTask(EditorTask task) {

		TaskResponse response = null;
		
		// authenticate user using token
		if (server.userIsAuth(task.getUserID(), task.getUserAuthToken())) {
			// edit the Rope underlying the file
			editSource(task);
			// notify clients of edit
			response = new EditorTaskResponse(task.getUsername(),
					task.getKeyPressed(), task.getEditIndex(),
					task.getFileID(), true);
		} else {
			// user authentication failed
			response = new EditorTaskResponse("n/a", -1, -1, -1, false);
		}
		distributeTask(response);
	}

	/**
	 * Edit the source code using an EditorTask.
	 * 
	 * @param task the EditorTask that determines what needs to be edited
	 */
	public void editSource(EditorTask task) {
		editSource(task.getKeyPressed(), task.getEditIndex());
	}

	/**
	 * Distribute a task to those clients registered as listeners on this file.
	 * 
	 * @param response the task response to distribute to the clients
	 */
	public void distributeTask(TaskResponse response) {
		for (CNPConnection client : clientConnections.values()) {
			SendResponseTask fileTask = new SendResponseTask(response, client);
			fileTaskCourier.submit(fileTask);
		}
	}

	/**
	 * Add a user to the lists of file task event listeners.
	 * 
	 * @param userID the identifier of the user that will get registered for events
	 * @param connection the CNPConnection of the client that will registered for events
	 */
	public void addFileTaskEventListener(int userID, CNPConnection connection) {
		clientConnections.put(userID, connection);
	}

	/**
	 * Remove a user from the list of file task event listeners.
	 * 
	 * @param userID user ID of the user to remove
	 */
	public void removeFileTaskEventListener(int userID) {
		clientConnections.remove(userID);
	}

	/**
	 * Check if a user is a registered listener for this file.
	 * 
	 * @param userID The ID of the user to check
	 * @return true only if user is currently listening for changes
	 */
	public boolean userIsListening(int userID) {
		if (clientConnections.containsKey(userID)) {
			return true;
		} else {
			return false;
		}
	}

}
