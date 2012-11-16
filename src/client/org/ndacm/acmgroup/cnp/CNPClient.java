package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.text.BadLocationException;

import org.ndacm.acmgroup.cnp.file.ClientSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.gui.MainFrame;
import org.ndacm.acmgroup.cnp.network.ClientNetwork;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.CreateFileTask;
import org.ndacm.acmgroup.cnp.task.DownloadFileTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.LoginTask;
import org.ndacm.acmgroup.cnp.task.OpenFileTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.response.CreateFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.JoinSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.OpenFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

public class CNPClient implements TaskReceivedEventListener {

	private String serverURL;
	private String sessionName;
	private int userID; // ID of account logged in as
	private String authToken; // assigned by server after authentication

	private ExecutorService clientExecutor;
	private Map<Integer, ClientSourceFile> sourceFiles; // fileID -
														// ClientSourceFile

	private ClientNetwork network;
	private MainFrame sourceFrame;

	public CNPClient(String serverURL) {

		this.serverURL = serverURL;
		sourceFiles = new ConcurrentHashMap<Integer, ClientSourceFile>();
		clientExecutor = Executors.newCachedThreadPool();
		sourceFrame = new MainFrame();

		// register as task event listener with network
		network.addTaskReceivedEventListener(this);
	}

	public void connectToServer(String serverURL) {
		network.connect(serverURL);
	}

	public void loginToAccount(String username, String password) {
		Task task = new LoginTask(username, password);
		network.sendTask(task);
	}

	public void joinSession(String sessionName) {
		Task task = new JoinSessionTask(userID, sessionName, authToken);
		network.sendTask(task);
	}

	public void editFile(int userID, int sessionID, int keyPressed,
			int editIndex, SourceFile file, String userAuthToken) {

		Task task = new EditorTask(userID, sessionID, keyPressed, editIndex,
				file.getFileID(), file, userAuthToken);
		network.sendTask(task);

	}

	public boolean compile(List<String> fileNames) {
		// TODO implement
		return false;
	}

	public void createSourceFile(int fileID, String filename, SourceType type) {
		Task task = new CreateFileTask(userID, filename, type, authToken);
		network.sendTask(task);

	}

	public void openSourceFile(int fileID) {
		Task task = new OpenFileTask(userID, fileID, authToken);
		network.sendTask(task);

	}

	/**
	 * Convert the SourceFile with the given filename to a File and return this
	 * file.
	 * 
	 * @param fileName
	 *            The filename of the SourceFile to return.
	 * @return The specified SourceFile converted to a File.
	 */
	public File getSourceFile(String fileName) {
		File file = sourceFiles.get(fileName).toFile();
		return file;
	}

	/**
	 * Convert all the SourceFiles for the session that the client is connected
	 * to actual Files. Return these Files in a List.
	 * 
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

	public void executeTask(Task task) {
		executeTask(task);
	}

	public void executeTask(LoginTaskResponse task) {
		if (task.isSuccess()) {
			userID = task.getUserID();
			authToken = task.getUserAuthToken();
		}
	}

	public void executeTask(JoinSessionTaskResponse task) {
		if (task.isSuccess()) {
			// open up main form and source files and stuff
		}

	}

	public void executeTask(CreateFileTaskResponse task) {
		if (task.isSuccess()) { // client is a session leader

			sourceFiles.put(task.getFileID(),
					new ClientSourceFile(task.getFileID(), task.getFilename(),
							task.getType(), "", this));
			// create new tab; populate file tree for other users (will register
			// them only if they open)
		}
	}

	public void executeTask(OpenFileTaskResponse task) {
		// open up tab for file (may need to also include code to synchronize it
		// initially..)
	}

	public void executeTask(EditorTaskResponse task)
			throws BadLocationException {
		task.getFile().editSource(task);
		sourceFrame.updateSourceTab(task.getFileID(), task.getKeyPressed(),
				task.getEditIndex());
	}

	public boolean executeTask(ChatTask task) {
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

	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {
		Task task = evt.getTask();

		if (task instanceof TaskResponse) {
			TaskResponse response = (TaskResponse) task;
			response.setClient(this);
			clientExecutor.submit(response);
		}

	}

}
