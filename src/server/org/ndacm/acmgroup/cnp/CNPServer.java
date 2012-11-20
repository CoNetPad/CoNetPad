package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.xml.soap.MessageFactory;

import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;
import org.ndacm.acmgroup.cnp.network.ServerNetwork;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.CreateAccountTask;
import org.ndacm.acmgroup.cnp.task.CreatePrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateSessionTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory;
import org.ndacm.acmgroup.cnp.task.response.ChatTaskResponse;

public class CNPServer implements TaskReceivedEventListener {

	private ServerNetwork network;
	private Database database;
	private Compiler compiler;
	private String baseDirectory;
	private Map<Integer, CNPSession> openSessions; // maps sessionID to //
													// CNPSession
	private TaskMessageFactory messageFactory;

	private SecretKey key; // TODO implement
	private Cipher cipher; // TODO implement

	public CNPServer(String baseDirectory) {

		this.baseDirectory = baseDirectory;
		network = new ServerNetwork();
		compiler = new Compiler();
		openSessions = new ConcurrentHashMap<Integer, CNPSession>();
		TaskMessageFactory.initalizeMessageFactory(this);

		try {
			database = new Database();
		} catch (Exception ex) {
			System.err.println("Error setting up databse.");
			System.exit(1);
		}

		// register as task event listener with network
		network.addTaskReceivedEventListener(this);

	}

	// args[0] is base installation directory
	public static void main(String[] args) {
		CNPServer server = new CNPServer(args[0]);
		server.startNetwork();
	}

	// start listening for client connection
	public void startNetwork() {
		network.startListening();
	}

	public void createAccount(CreateAccountTask task) throws SQLException {
		database.createAccount(task.getUsername(), task.getEmail(),
				task.getPassword());
		// send back response
	}

	public void connectToSession(JoinSessionTask task) throws SQLException {

		// CNPSession session = null;
		// Account userAccount = database.retrieveAccount(task.getUserID(),
		// task.getPassword());
		//
		// if (!openSessions.containsKey(task.getSessionName())) {
		// // retrieve session from database and add to openSessions
		//
		// } else {
		// // retrieve from openSessions
		// session = openSessions.get(task.getSessionName());
		// }
		//
		// // associate account with session
		// session.addUser(userAccount, task.getConnection());
	}

	public CNPSession createCNPSession(CreateSessionTask task)
			throws FailedSessionException, SQLException {

		// return database.createSession(task.getSessionLeader(),
		// CNPSession.generateString(), this);
		return null;

	}

	public CNPSession createCNPSession(CreatePrivateSessionTask task)
			throws FailedSessionException, SQLException {

		// return database.createSession(task.getSessionLeader(),
		// CNPSession.generateString(), this, task.getSessionPassword());
		return null;
	}

	public File compile(List<String> fileNames, CNPSession session) {
		// TODO implement
		return new File(""); // return tar or something
	}

	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {

		//TODO
		//REMOVE BLOCK
		//THIS IS FOR TESTING PURPOSES ONLY!
		ChatTask cTask = (ChatTask) evt.getTask();
		System.out.println("server: " + cTask.getMessage());
		network.sendTaskResponseToAllClients(new ChatTaskResponse("example",
				cTask.getMessage()));
		
		if (1 > 0) {
			return;
		}

		Task task = evt.getTask();

		if (task instanceof EditorTask) { // send to ServerSourceFile's
											// taskQueue

			EditorTask editorTask = (EditorTask) task;
			CNPSession session = openSessions.get(editorTask.getSessionID());
			session.executeTask(editorTask);

		} else if (task instanceof CreateAccountTask) {

			try {
				createAccount((CreateAccountTask) task);
			} catch (SQLException e) {
				System.err.println("Failed to create account:\n"
						+ e.getMessage());

			}

		} else if (task instanceof JoinSessionTask) {

			JoinSessionTask connectTask = (JoinSessionTask) task;
			try {
				connectToSession(connectTask);
			} catch (SQLException e) {
				// System.err.println("Failed to connect " +
				// connectTask.getUsername() +
				// S " to " + connectTask.getSessionName());
			}

		} else if (task instanceof CreateSessionTask) {

			CreateSessionTask createTask = (CreateSessionTask) task;
			try {
				createCNPSession(createTask);
			} catch (SQLException e) {
				System.err.println("Failed to create session.");
			} catch (FailedSessionException e) {
				System.err.println("Failed to create session.");
			}

		} else { // send to sessionTaskQueue if should

			// TODO implement
		}

	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public CNPSession getSession(int sessionID) {
		return openSessions.get(sessionID);
	}
}
