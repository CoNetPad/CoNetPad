package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import org.ndacm.acmgroup.cnp.file.ClientSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.gui.CreateSessionDialog;
import org.ndacm.acmgroup.cnp.gui.LoginDialog;
import org.ndacm.acmgroup.cnp.gui.MainFrame;
import org.ndacm.acmgroup.cnp.gui.RegisterDialog;
import org.ndacm.acmgroup.cnp.gui.ServerConnectionDialog;
import org.ndacm.acmgroup.cnp.gui.SessionDialog;
import org.ndacm.acmgroup.cnp.network.ClientNetwork;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.CreateAccountTask;
import org.ndacm.acmgroup.cnp.task.CreateFileTask;
import org.ndacm.acmgroup.cnp.task.CreatePrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateSessionTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.JoinPrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.LoginTask;
import org.ndacm.acmgroup.cnp.task.OpenFileTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.response.ChatTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateAccountTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.DownloadFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.JoinSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.OpenFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponseExecutor;

public class CNPClient implements TaskReceivedEventListener,
		TaskResponseExecutor {

	private String serverURL; // The URL to the server
	private String sessionName; // The unique name of the session the user
								// belongs to
	private int sessionID; // The unique ID of the session the user belongs
	private int userID; // ID of account logged in as
	private String username; // The Username of the user
	private String authToken; // assigned by server after authentication

	private ExecutorService clientExecutor; // this is for executing varoous
											// tasks
	private Map<Integer, ClientSourceFile> sourceFiles; // The files the client
														// is reading through.
														// This is used in the
														// GUI

	private ClientNetwork network; // The network connection for doing messaging
									// sending and recieving
	private MainFrame clientFrame; // The frame of the GUI
	private RegisterDialog regDialog;
	private LoginDialog logDialog;
	private SessionDialog sesDialog;
	private CreateSessionDialog createSessionDialog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ServerConnectionDialog dialog = new ServerConnectionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Default Constructor
	 */
	public CNPClient() {

		sourceFiles = new ConcurrentHashMap<Integer, ClientSourceFile>();
		clientExecutor = Executors.newCachedThreadPool();
		network = new ClientNetwork();

		// register as task event listener with network
		network.addTaskReceivedEventListener(this);
	}

	/**
	 * This connects to a server using an URL
	 * 
	 * @param serverURL
	 *            The URL of the serve to connect to
	 */
	public boolean connectToServer(String serverURL) {
		if (network.connect(serverURL)) {
			this.serverURL = serverURL;
		} else {
			return false;
		}
		return true;
	}

	public void closeConnection() {
		network.disconnect();
	}

	public void setRegDialog(RegisterDialog regDialog) {
		this.regDialog = regDialog;
	}

	public void setSessionDialog(SessionDialog sessionDialog) {
		this.sesDialog = sessionDialog;
	}

	public void setLogDialog(LoginDialog logDialog) {
		this.logDialog = logDialog;
	}

	public void setCreateSessionDialog(CreateSessionDialog createDialog) {
		this.createSessionDialog = createDialog;
	}

	public void createSession(String password) {
		CreateSessionTask task;
		if (password.isEmpty()) {
			task = new CreateSessionTask(userID, authToken);
		} else {
			task = new CreatePrivateSessionTask(userID, password, authToken);
		}
		network.sendTask(task);
	}

	/**
	 * This creates an account for the user or client
	 * 
	 * @param username
	 *            The username the client wishes to use
	 * @param email
	 *            The email of the client to use
	 * @param password
	 *            The password the client to use - Un-encrypted
	 */
	public void createAccount(String username, String email, String password) {
		CreateAccountTask task = new CreateAccountTask(username, email,
				password);
		network.sendTask(task);
	}

	/**
	 * This log the user in if he/she has an account
	 * 
	 * @param username
	 *            The username of their account
	 * @param password
	 *            The password of their account - Un-encrypted
	 */
	public void loginToAccount(String username, String password) {
		Task task = new LoginTask(username, password);
		network.sendTask(task);
	}

	/**
	 * This joins the user to a given session using the uniqu ename
	 * 
	 * @param sessionName
	 *            The unique name of the session
	 */
	public void joinSession(String sessionName, String password) {
		Task task;
		if (password.isEmpty()) {
			task = new JoinSessionTask(userID, username, sessionName, authToken);
		} else {
			task = new JoinPrivateSessionTask(userID, username, sessionName,
					password, authToken);
		}
		network.sendTask(task);
	}

	/**
	 * This edits the file the user is viewing or working on
	 * 
	 * @param userID
	 *            The user ID of which the edit came from
	 * @param sessionID
	 *            the session Id of which the file belongs to
	 * @param keyPressed
	 *            The key that is pressed when the edit is being made
	 * @param editIndex
	 *            The index of the character or white space being edited
	 * @param fileID
	 *            The unique file ID of the ile being edited
	 * @param userAuthToken
	 *            The authentication cooki prevent hackers from editing
	 */
	public void editFile(int userID, int sessionID, int keyPressed,
			int editIndex, int fileID, String userAuthToken) {

		Task task = new EditorTask(userID, sessionID, keyPressed, editIndex,
				fileID, userAuthToken);
		network.sendTask(task);

	}

	/**
	 * This compiles a list of files [Not Implemented]
	 * 
	 * @param fileNames
	 *            The list of files that are being compiled
	 * @return True if the files compiled successful, false otherwise
	 */
	public boolean compile(List<String> fileNames) {
		// TODO implement
		return false;
	}

	/**
	 * This creates a new file to be worked on or edited.
	 * 
	 * @param fileID
	 *            The unique File ID to assign to the new file
	 * @param filename
	 *            The unique file name to assign to the file
	 * @param type
	 *            The type of the newly created file
	 */
	public void createSourceFile(int fileID, String filename, SourceType type) {
		Task task = new CreateFileTask(userID, filename, type, authToken);
		network.sendTask(task);

	}

	/**
	 * This opens up an existing file given a unique file ID
	 * 
	 * @param fileID
	 *            The unique file ID of the file to open
	 */
	public void openSourceFile(int fileID) {
		Task task = new OpenFileTask(userID, fileID, authToken);
		network.sendTask(task);

	}

	/**
	 * This sends a chat message to the server.
	 * 
	 * @param message
	 *            The message to send
	 */
	public void sendChatMessage(String message) {
		Task task = new ChatTask(userID, username, sessionID, message,
				authToken);
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

	@Override
	public void executeTask(TaskResponse task) {
		executeTask(task);

	}

	/**
	 * This executes a a createAccount Tasks I.e creates a new account via task.
	 * [Not Implemented]
	 * 
	 * @param task
	 *            The createUserTask to create the new account
	 */
	public void executeTask(CreateAccountTaskResponse task) {
		if (task.isSuccess()) {
			JOptionPane.showMessageDialog(logDialog, "Account created.");
			Runnable doWorkRunnable = new Runnable() {
				public void run() {
					regDialog.dispose();
				}
			};
			SwingUtilities.invokeLater(doWorkRunnable);
		} else {
			JOptionPane.showMessageDialog(logDialog,
					"Error while creating an account.");
		}
	}

	/**
	 * This logs in the user via LogInTaskResponse
	 * 
	 * @param task
	 *            The loginTaskResponse to use to login the user
	 */
	public void executeTask(LoginTaskResponse task) {
		if (task.isSuccess()) {
			userID = task.getUserID();
			username = task.getUsername();
			authToken = task.getUserAuthToken();
			Runnable doWorkRunnable = new Runnable() {
				public void run() {
					logDialog.openSessionDialog();
				}
			};
			SwingUtilities.invokeLater(doWorkRunnable);
		} else {
			JOptionPane.showMessageDialog(logDialog, "Error logging in");
			Runnable doWorkRunnable = new Runnable() {
				public void run() {
					logDialog.resetDialog();
				}
			};
			SwingUtilities.invokeLater(doWorkRunnable);
		}
	}

	/**
	 * This creates a new session via CreateSessionTAsk
	 * 
	 * @param task
	 *            The Task to use to create a new session
	 */
	public void executeTask(final CreateSessionTaskResponse task) {
		if (task.isSuccess()) {
			JOptionPane.showMessageDialog(createSessionDialog,
					"Session created.");
			Runnable doWorkRunnable = new Runnable() {
				public void run() {
					createSessionDialog.dispose();
					sesDialog.setSessionName(task.getSessionName());
				}
			};
			SwingUtilities.invokeLater(doWorkRunnable);
		} else {
			JOptionPane.showMessageDialog(createSessionDialog,
					"Error creating session");
			Runnable doWorkRunnable = new Runnable() {
				public void run() {
					createSessionDialog.resetDialog();
				}
			};
			SwingUtilities.invokeLater(doWorkRunnable);
		}
	}

	/**
	 * This lets the user join a session via JoinSessionTask
	 * 
	 * @param task
	 *            The JoinSession Task used to let the user join a session
	 */
	public void executeTask(JoinSessionTaskResponse task) {
		if (task.isSuccess()) {
			Runnable doWorkRunnable = new Runnable() {
				public void run() {
					sesDialog.openMainFrame();
				}
			};
			SwingUtilities.invokeLater(doWorkRunnable);
			if (task.getUserID() == userID) {
				// update client frame with list of files
				clientFrame.addToFileList(task.getSessionFiles());
			} else {
				// another client sent the task - update user list
				clientFrame.addUser(task.getUsername());
			}
		} else {
			JOptionPane.showMessageDialog(sesDialog, "Error accessing session");
			Runnable doWorkRunnable = new Runnable() {
				public void run() {
					sesDialog.resetDialog();
				}
			};
			SwingUtilities.invokeLater(doWorkRunnable);
		}
	}

	/**
	 * This creates a new file via CreateFileTAsk
	 * 
	 * @param task
	 *            The createfileTask to use to create the new file
	 */
	public void executeTask(CreateFileTaskResponse task) {
		if (task.isSuccess()) { // client is a session leader

			sourceFiles.put(task.getFileID(),
					new ClientSourceFile(task.getFileID(), task.getFilename(),
							task.getType(), "", this));

			// if client is a session leader, then open file up in tab
			if (task.getUserID() == userID) {

				// create and open new tab
				clientFrame.addTab(task.getFileID(), task.getFilename(), "");

			}

			// populate file tree for all users
			clientFrame.addToFileList(task.getFilename());
		}
	}

	/**
	 * This will open a new file via OpenFileTask
	 * 
	 * @param task
	 *            The openFileTaskResponse used to open a file
	 */
	public void executeTask(OpenFileTaskResponse task) {
		clientFrame.addTab(task.getFileID(), task.getFilename(),
				task.getFileContent());
	}

	/**
	 * This executes a file edit via EditorTaskRepsonse
	 * 
	 * @param task
	 *            The EditorTaske used to edit the file
	 * @throws BadLocationException
	 *             If the file doesn't exist, this exception is thrown
	 */
	public void executeTask(EditorTaskResponse task)
			throws BadLocationException {

		if (task.isSuccess()) {
			sourceFiles.get(task.getFileID()).editSource(task);
			clientFrame.updateSourceTab(task.getFileID(), task.getKeyPressed(),
					task.getEditIndex());
		}
	}

	/**
	 * This sends a chat message via ChatTaskResponse
	 * 
	 * @param task
	 *            The ChatTasResponse used to send the chat message
	 */
	public void executeTask(ChatTaskResponse task) {
		clientFrame.updateChat(task.getUsername(), task.getMessage());
	}

	/**
	 * This downloads a file via DownloadFileTAsk response [Not Implemented]
	 * 
	 * @param task
	 *            the DownloadFileTAsk to use to download the file
	 * @return True if successful, false otherwise
	 */
	public boolean executeTask(DownloadFileTaskResponse task) {
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
