package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import org.ndacm.acmgroup.cnp.file.ClientSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.gui.CreateSessionDialog;
import org.ndacm.acmgroup.cnp.gui.LoginDialog;
import org.ndacm.acmgroup.cnp.gui.MainFrame;
import org.ndacm.acmgroup.cnp.gui.NewFileDialog;
import org.ndacm.acmgroup.cnp.gui.RegisterDialog;
import org.ndacm.acmgroup.cnp.gui.ServerConnectionDialog;
import org.ndacm.acmgroup.cnp.gui.SessionDialog;
import org.ndacm.acmgroup.cnp.network.ClientNetwork;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.CommitTask;
import org.ndacm.acmgroup.cnp.task.CreateAccountTask;
import org.ndacm.acmgroup.cnp.task.CreateFileTask;
import org.ndacm.acmgroup.cnp.task.CreatePrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateSessionTask;
import org.ndacm.acmgroup.cnp.task.JoinPrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.LoginTask;
import org.ndacm.acmgroup.cnp.task.OpenFileTask;
import org.ndacm.acmgroup.cnp.task.SendEditorTaskTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.response.ChatTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CloseFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CommitTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CompileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateAccountTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.DeleteFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.DeleteSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.DownloadRepoTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.JoinSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LeaveSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.OpenFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponseExecutor;

/**
 * 
 * ###################KK###################
 * ##################KEDK##################
 * #################K#EEfK#################
 * ################KWWWDEEK################
 * ###############KWWWK###DK###############
 * ##############KWWWW#K#EKDK##############
 * #############KWWKWWWWWKEKEK#############
 * ############KKW#WDWW#WEKKKKK############
 * ###########KiLKW        WW#KK###########
 * ##########K;E#  ,#WWWK#,  #EGK##########
 * #########KWj  ###W##W##KE#  DGK#########
 * ########KW#  WKWW####WWDGLG  EEK########
 * #######KWW  W#KWKWWWWEWGGLLD  E#K#######
 * ######KK## WWK#KKKKEEDEDGGDDD ##EK######
 * #####KWKW #WKKWK#W#WKKEEDDDDE# EEEK#####
 * ####KKK#W #WKKKKWWWWKEEEDDDDDE WEKKK####
 * ###K#WWK ,KWWKKEKKKKKEEEEEEEEE, KE#DK###
 * ##KE##WE ##   WWWj  #; W :#  W# EWKLEK##
 * #KEWW##K ##,#  #  Dt ;       EW #WEKW#K#
 * KGWWED#W ##K   #  ###; ## ## j# #tKWWW#K
 * DEWWGEK# #  ## #  ###; ## ## j# EEWKKKKD
 * #DGWWKEW W  #. #  ## ; WW WW tW DEW#KKD#
 * ##EKWKKK #W  W WW   K; WW KK tW EWWW#E##
 * ###EGWWE ,KKWWKKKWWKWKKKKKEEEE, KDWWE###
 * ####DKEEW KKKKK##KWW#WWWKKKEEE WEDED####
 * #####DDGK #EKEDK#E##WKWWEEGDD# GEWD#####
 * ######EDW# DGGDDDDDEEDDGGGLL# ##WE######
 * #######EEW  DDDEDDKWWW###WWE  W#E#######
 * ########DKW  EDEEG##KWWWEEL  fKD########
 * #########DK#  #fEKDDGLfDE#  LGD#########
 * ##########DE##  ,WDLDLE,  #WGD##########
 * ###########DKWKW        WDfDD###########
 * ############EKWKKEK##KfG#DGE############
 * #############EEWKEEEKKGEKGE#############
 * ##############DDWEEEEEDK#D##############
 * ###############EEWKKEG##D###############
 * ################KKWKDGLK################
 * #################KEWEGK#################
 * ##################KEKK##################
 * ###################KK###################
 * 
 * 
 * This class is the main client-side class. It handles the communication and
 * the various client functionalities.
 * 
 * @author Cesar Ramirez
 * 
 */
public class CNPClient implements TaskReceivedEventListener,
TaskResponseExecutor {

	// URL of the server connected to
	private String serverURL;
	// The unique name of the session the user belongs to
	private String sessionName;
	private int sessionID; // The unique ID of the session the user belongs
	private int userID; // ID of account logged in as
	private String username; // The username of the user
	private String authToken; // assigned by server after authentication

	/**
	 * Executor for executing client tasks.
	 */
	private ExecutorService clientExecutor;

	/**
	 * Executor for queuing editing events on the client side. Single threaded
	 * to serialize tasks as they are added.
	 */
	private ExecutorService editorTaskSender;

	/**
	 * True if the user is waiting for an editor response. Needed to ensure
	 * consistency in file editing events.
	 */
	private volatile boolean isWaiting;

	private final CNPClient cnpClient;

	/**
	 * The source files for the session that a client is connected to.
	 */
	private Map<Integer, ClientSourceFile> sourceFiles;

	/**
	 * Network handling the sending and receiving of messages.
	 */
	private ClientNetwork network;
	private MainFrame clientFrame; // The frame of the client GUI
	private RegisterDialog regDialog;
	private LoginDialog logDialog;
	private SessionDialog sesDialog;
	private CreateSessionDialog createSessionDialog;
	private NewFileDialog newFileDialog;
	private CNPClient client = this;

	/**
	 * Launch the application. Entry point for the client side of the
	 * application.
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
		editorTaskSender = Executors.newSingleThreadExecutor();
		network = new ClientNetwork();
		isWaiting = false;
		this.cnpClient = this;

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

	/**
	 * This disconnects the user from the server
	 */
	public void closeConnection() {
		network.disconnect();
		clientExecutor.shutdown();
	}

	/**
	 * This shows the dialog box for registering a new user
	 * 
	 * @param regDialog
	 *            The Dialogbox for registering the user
	 */
	public void setRegDialog(RegisterDialog regDialog) {
		this.regDialog = regDialog;
	}

	/**
	 * This shows the dialog box for setting a user session
	 * 
	 * @param sessionDialog
	 *            The SessionDialog for setting the session for the user
	 */
	public void setSessionDialog(SessionDialog sessionDialog) {
		this.sesDialog = sessionDialog;
	}

	/**
	 * This shows the dialog box for logging the user in
	 * 
	 * @param logDialog
	 *            The LoginDialog for logging the user in
	 */
	public void setLogDialog(LoginDialog logDialog) {
		this.logDialog = logDialog;
	}

	/**
	 * This shows the dialog box or creating a new session
	 * 
	 * @param createDialog
	 *            The Create Dialog box for creating a new session
	 */
	public void setCreateSessionDialog(CreateSessionDialog createDialog) {
		this.createSessionDialog = createDialog;
	}

	/**
	 * This shows the dialog box for adding a new file
	 * 
	 * @param dialog
	 *            The Dialog box for adding a new file
	 */
	public void setNewFileDialog(NewFileDialog dialog) {
		this.newFileDialog = dialog;
	}

	/**
	 * This creates either a private or public password
	 * 
	 * @param password
	 *            Leave blank to create a public session, or give a password to
	 *            create private
	 */
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
	public void editFile(int keyPressed, int fileID) {

		SendEditorTaskTask task = new SendEditorTaskTask(userID, sessionID,
				keyPressed, fileID, authToken, this);
		editorTaskSender.submit(task);
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
	 * @param filename
	 *            The unique file name to assign to the file
	 * @param type
	 *            The type of the newly created file
	 */
	public void createSourceFile(String filename, SourceType type) {
		Task task = new CreateFileTask(userID, sessionID, filename, type,
				authToken);
		network.sendTask(task);
	}

	/**
	 * This opens up an existing file given a unique file ID
	 * 
	 * @param fileID
	 *            The unique file ID of the file to open
	 */
	public void openSourceFile(int fileID) {
		Task task = new OpenFileTask(userID, sessionID, fileID, authToken);
		network.sendTask(task);
	}

	/**
	 * This opens up an existing file given a unique file name
	 * 
	 * @param fileName
	 *            The unique name of the file to open
	 */
	public void openSourceFile(String fileName) {
		for (ClientSourceFile entry : sourceFiles.values()) {
			if (entry.getFilename().compareTo(fileName) == 0) {
				Task task = new OpenFileTask(userID, sessionID,
						entry.getFileID(), authToken);
				network.sendTask(task);
				break;
			}
		}
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
	 * This sends a commit task to the server. This method should be used if the
	 * data from the ropes need to be saved to the disk.
	 */
	public void commitSession() {
		Task task = new CommitTask(userID, authToken, sessionID, sessionName,
				"Default");
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
		} else {
			JOptionPane.showMessageDialog(logDialog,
					"Error while creating an account.");
		}
		Runnable doWorkRunnable = new Runnable() {
			public void run() {
				regDialog.dispose();
			}
		};
		SwingUtilities.invokeLater(doWorkRunnable);
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
	public void executeTask(final JoinSessionTaskResponse task) {
		if (task.isSuccess()) {
			if (task.getUserID() == userID) {
				// update client frame with list of files

				File repoFolder = new File("Repos" + File.separator
						+ task.getSessionName());
				repoFolder.mkdirs();

				Runnable doWorkRunnable = new Runnable() {
					public void run() {
						clientFrame = sesDialog.openMainFrame();
						sessionID = task.getSessionID();
						sessionName = task.getSessionName();
						// populate user list with usernames of those already
						// connected
						clientFrame.setTitle(sessionName);
						clientFrame.addToUserList(new ArrayList<String>(task
								.getConnectedUsers()));

						clientFrame.addToFileList(task.getSessionFiles());

						for (int i = 0; i < task.getSessionFiles().size(); i++) {
							ClientSourceFile file = new ClientSourceFile(task
									.getFileIDs().get(i), task
									.getSessionFiles().get(i),
									SourceType.GENERAL, "", client);
							sourceFiles.put(task.getFileIDs().get(i), file);
						}
					}
				};
				SwingUtilities.invokeLater(doWorkRunnable);

			} else {
				// another client sent the task - update user list
				clientFrame.addToUserList(task.getUsername());
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

			// populate file tree for all users
			clientFrame.addToFileList(task.getFilename());
		} else {
			JOptionPane.showMessageDialog(clientFrame,
					"Error while creating the file.");
		}
		Runnable doWorkRunnable = new Runnable() {
			public void run() {
				newFileDialog.dispose();
			}
		};
		SwingUtilities.invokeLater(doWorkRunnable);
	}

	/**
	 * This will open a new file via OpenFileTask
	 * 
	 * @param task
	 *            The openFileTaskResponse used to open a file
	 */
	public void executeTask(OpenFileTaskResponse task) {
		if (clientFrame.addTab(task.getFileID(), task.getFilename(),
				task.getFileContent())) {
			sourceFiles.put(task.getFileID(),
					new ClientSourceFile(task.getFileID(), task.getFilename(),
							SourceType.GENERAL, task.getFileContent(), this));
			sourceFiles.get(task.getClientId()).save();
		}
	}

	/**
	 * This executes a file edit via EditorTaskRepsonse
	 * 
	 * @param task
	 *            The EditorTaske used to edit the file
	 * @throws BadLocationException
	 *             If the file doesn't exist, this exception is thrown
	 */
	public void executeTask(final EditorTaskResponse task) {

		if (task.isSuccess()) {
			ClientSourceFile file = sourceFiles.get(task.getFileID());
			if (file.editSource(task)) {

				Runnable doWorkRunnable = new Runnable() {
					public void run() {
						try {
							synchronized (cnpClient) {
								// temporarily turn filter on
								clientFrame.setEditorFilterActivated(true);
								clientFrame.updateSourceTab(task.getFileID(),
										task.getKeyPressed(),
										task.getEditIndex());
								// turn back off
								clientFrame.setEditorFilterActivated(false);

								cnpClient.setWaiting(false);
								cnpClient.notifyAll();
							}
						} catch (BadLocationException e) {
							// do something
						}
					}
				};
				SwingUtilities.invokeLater(doWorkRunnable);
			} else {
				System.out.println("Error updating text area");
			}
		}
	}

	/**
	 * This sends a chat message via ChatTaskResponse
	 * 
	 * @param task
	 *            The ChatTasResponse used to send the chat message
	 */
	public void executeTask(final ChatTaskResponse task) {
		Runnable doWorkRunnable = new Runnable() {
			public void run() {
				String use = task.getUsername();
				String mes = task.getMessage();
				if (clientFrame == null) {
					String sdf = "sddfdfg";
					sdf = sdf + "dsf";
				} else {
					String sdf = "sddfdfg";
					sdf = sdf + "dsf";
				}

				clientFrame.updateChat(use, mes);
			}
		};
		SwingUtilities.invokeLater(doWorkRunnable);
	}

	/**
	 * If task was executed successfully, close the tab for task file.
	 */
	@Override
	public void executeTask(final CloseFileTaskResponse task) {
		if (task.isSuccess()) {
			Runnable doWorkRunnable = new Runnable() {
				public void run() {
					clientFrame.removeTab(task.getTabIndex());
				}

			};
			SwingUtilities.invokeLater(doWorkRunnable);
		}
	}

	/**
	 * This executes a Commit Task for the Git repository
	 */
	@Override
	public void executeTask(CommitTaskResponse task) {
		if (task.isSuccess()) {
			JOptionPane.showMessageDialog(logDialog, "Files saved");
			for (Entry<Integer, ClientSourceFile> fileEntry : sourceFiles
					.entrySet()) {
				fileEntry.getValue().save();
			}
		} else {
			JOptionPane.showMessageDialog(logDialog, "Error while saving");
		}

	}

	/**
	 * This executes a compile task to compile source code
	 */
	@Override
	public void executeTask(CompileTaskResponse task) {
		// TODO Auto-generated method stub

	}

	/**
	 * This executes a delete file Task
	 */
	@Override
	public void executeTask(DeleteFileTaskResponse task) {
		// TODO Auto-generated method stub

	}

	/**
	 * This executes a delete session task
	 */
	@Override
	public void executeTask(DeleteSessionTaskResponse task) {
		// TODO Auto-generated method stub

	}

	/**
	 * Execute the task for leaving a session. If this client was the one that
	 * requested the session to be left, then update the client GUI to reflect
	 * this. Otherwise, remove the username in the task from the session user
	 * list.
	 */
	@Override
	public void executeTask(final LeaveSessionTaskResponse task) {
		if (task.isSuccess()) {
			// if this client sent the initial task
			if (task.getUserID() == userID) {
				// then leave session in GUI
			} else {

				Runnable doWorkRunnable = new Runnable() {
					public void run() {
						clientFrame.removeUser(task.getUsername());
					}

				};
				SwingUtilities.invokeLater(doWorkRunnable);
			}

		}

	}

	/**
	 * This executes a download Repository Task
	 */
	@Override
	public void executeTask(DownloadRepoTaskResponse task) {
		// TODO Auto-generated method stub

	}

	/**
	 * This handles and recieved tasks from the server
	 */
	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {

		Task task = evt.getTask();

		if (task instanceof TaskResponse) {
			TaskResponse response = (TaskResponse) task;
			response.setClient(this);
			clientExecutor.submit(response);
		}

	}

	/**
	 * This returns the Session name
	 * 
	 * @return The name of the session the user current is part of
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * @return the network component.
	 */
	public ClientNetwork getNetwork() {
		return network;
	}

	/**
	 * @param fileID
	 *            of the file to open.
	 * @return the jtextarea containing the specified fiel.
	 */
	public JTextArea getEditorTextArea(int fileID) {
		return clientFrame.getTab(fileID);
	}

	/**
	 * @return the value of the isWaiting flag.
	 */
	public boolean isWaiting() {
		return isWaiting;
	}

	/**
	 * @param isWaiting
	 *            the status to set to isWaiting flag to.
	 */
	public void setWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}

	/**
	 * Return the mapping of fileID to ClientSourceFile.
	 * @return the map between fileID and source files
	 */
	public Map<Integer, ClientSourceFile> getSourceFiles() {
		return sourceFiles;
	}
}
