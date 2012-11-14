package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLServerSocket;

import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.exceptions.FailedAccountException;
import org.ndacm.acmgroup.cnp.exceptions.FailedSessionException;
import org.ndacm.acmgroup.cnp.network.ServerNetwork;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateAccountTask;
import org.ndacm.acmgroup.cnp.task.CreatePrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateSessionTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.Task;

public class CNPServer implements TaskReceivedEventListener {

	
	
	private ServerNetwork network;	
	private Database database;
	private Compiler compiler;
	private String baseDirectory;
	private Map<String, CNPSession> openSessions; // maps session name to CNPSession

	private SecretKey key; // TODO implement
	private Cipher cipher; // TODO implement

	public CNPServer(String baseDirectory) {

		this.baseDirectory = baseDirectory;
		network = new ServerNetwork();
		compiler = new Compiler();
		openSessions = new ConcurrentHashMap<String, CNPSession>();

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
		database.createAccount(task.getUsername(), task.getEmail(), task.getPassword());
		// send back response
	}

	public void connectToSession(JoinSessionTask task) throws SQLException {
		
//		CNPSession session = null;
//		Account userAccount = database.retrieveAccount(task.getUserID(), task.getPassword());
//		
//		if (!openSessions.containsKey(task.getSessionName())) {
//			// retrieve session from database and add to openSessions
//			
//		} else {
//			// retrieve from openSessions
//			session = openSessions.get(task.getSessionName());
//		}
//		
//		// associate account with session
//		session.addUser(userAccount, task.getConnection());
	}


	public CNPSession createCNPSession(CreateSessionTask task) throws FailedSessionException, SQLException {

//		return database.createSession(task.getSessionLeader(), CNPSession.generateString(), this);
		return null;
		
	}
	

	public CNPSession createCNPSession(CreatePrivateSessionTask task) throws FailedSessionException, SQLException {

//		return database.createSession(task.getSessionLeader(), CNPSession.generateString(), this, task.getSessionPassword());
		return null;
	}



	public File compile(List<String> fileNames, CNPSession session) {
		// TODO implement
		return new File(""); // return tar or something
	}

	@Override
	public void TaskReceivedEventOccurred(TaskReceivedEvent evt) {

//		Task task = evt.getTask();
		Task task = null;
		
		if (task instanceof EditorTask) { // send to ServerSourceFile's taskQueue

			EditorTask editorTask = (EditorTask) task;
//		S	editorTask.getSourceFile().addTask(editorTask);

		} else if (task instanceof CreateAccountTask) {

			try {
				createAccount((CreateAccountTask) task);
			} catch (SQLException e) {
				System.err.println("Failed to create account:\n" + e.getMessage());

			}

		} else if (task instanceof JoinSessionTask) {

			JoinSessionTask connectTask = (JoinSessionTask) task;
			try { 
				connectToSession(connectTask);
			} catch (SQLException e) {
//				System.err.println("Failed to connect " + connectTask.getUsername() + 
//		S				" to " + connectTask.getSessionName());
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
			
		}  else { // send to sessionTaskQueue if should

			// TODO implement
		}


	}
	
	public String getBaseDirectory() {
		return baseDirectory;
	}





}
