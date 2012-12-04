package org.ndacm.acmgroup.cnp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.ndacm.acmgroup.cnp.network.events.TaskEventSource;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.task.LeaveSessionTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.message.TaskMessage;
import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

/**
 * This class is a single thread that will listen from messaged from the client.
 * This class is initialized in the server when a client connects. Other objects
 * can call sendMessage() to send a message to the client, this method is not
 * called by this thread.
 * 
 * @author Cesar Ramirez
 */
public class CNPConnection extends Thread {
	private Socket socket = null;
	private int id;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private TaskEventSource taskSource;
	private boolean isServer;
	private boolean stop = false;
	private int sessionID;
	private int userID;
	private String auth;

	/**
	 * Defualt constructor.
	 * 
	 * @param socket
	 *            socket that connects to the client
	 * @param id
	 *            integer, unique identifier for this connection and client
	 * @param component
	 *            object that handles firing events, this will usually be the
	 *            Network object.
	 */
	public CNPConnection(Socket socket, int id, TaskEventSource taskSource,
			boolean isServer) {
		super();
		this.socket = socket;
		this.id = id;
		this.taskSource = taskSource;
		this.isServer = isServer;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

		} catch (IOException e) {
			System.err.println("Error establishing in/out streams");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			System.out.println("Thread for client " + id + " started");
			String inputLine;
			StringBuffer buff;
			while ((inputLine = in.readLine()) != null && !stop) {
				TaskMessage taskMessage;
				if (inputLine.charAt(inputLine.length() - 1) != TaskMessage.end) {
					buff = new StringBuffer();
					while (true) {
						buff.append(in.readLine() + "\n\r");
						if (buff.length() < 3) {
							continue;
						} else if (buff.toString().charAt(buff.length() - 3) != TaskMessage.end) {
							continue;
						} else {
							break;
						}
					}
					taskMessage = new TaskMessage(inputLine
							+ buff.substring(0, buff.length() - 4));
				} else {
					taskMessage = new TaskMessage(inputLine);
				}

				Task task = null;
				if (isServer) {
					task = TaskMessageFactory.fromMessageToTask(taskMessage);
					task.setClientId(id);
				} else {
					System.out.println("total elements"
							+ taskMessage.getData().length);
					task = TaskMessageFactory
							.fromMessageToTaskResponse(taskMessage);
				}
				System.out.println("Message recieved");
				taskSource.fireTaskReceivedEvent(new TaskReceivedEvent(task,
						this));
			}
			System.out.println("Thread for client stopped correctly");
		} catch (IOException e) {
			System.err.println("Thread for client stopped with an exception");
		}
		taskSource.fireTaskReceivedEvent(new TaskReceivedEvent(
				new LeaveSessionTask(userID, "", sessionID, auth), this));
	}

	/**
	 * Send a task through the connection.
	 * 
	 * @param task to be send through the network
	 */
	public void sendTask(Task task) {
		String message = TaskMessageFactory.fromTaskToMessage(task)
				.getMessageString();
		sendData(message);
		System.out.println("Task sent");
	}

	/**
	 * Send a task response through the connection.
	 * 
	 * @param task (response) to be send through the network
	 */
	public void sendTaskResponse(TaskResponse task) {
		String message = TaskMessageFactory.fromTaskResponseToMessage(task)
				.getMessageString();
		sendData(message);
		System.out.println("TaskResponse sent");
	}

	/**
	 * Send a string representation message of a task.
	 * 
	 * @param message string representation of a task
	 */
	private void sendData(String message) {
		out.println(message);
	}

	/**
	 * This method will close all the buffers(read, write and socket). This
	 * method should be call after stopThread().
	 */
	public void close() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the stopped status of the thread.
	 * 
	 * @return get the flag of the current status of the thread
	 */
	public boolean isStop() {
		return stop;
	}

	/**
	 * Set the status of the thread to stopped.
	 * 
	 * the stop flag is raised and the running thread should stop. The close()
	 * method should be called after this.
	 */
	public void stopThread() {
		this.stop = true;
	}

	/**
	 * Get the session ID of the connection.
	 * 
	 * @return sessionID
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * Set the session ID of the connection.
	 * 
	 * @param sessionID
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/** 
	 * Get the user ID of the connection.
	 * 
	 * @return userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Set the user ID of the connection.
	 * 
	 * @param userID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * Get the authorization token for the user of the connection.
	 * 
	 * @return userAuthToken
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * Set the authorization token for the user of the connection.
	 * 
	 * @param auth userAuthToken
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

}