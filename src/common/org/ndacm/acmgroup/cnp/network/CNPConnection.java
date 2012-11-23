package org.ndacm.acmgroup.cnp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.ndacm.acmgroup.cnp.network.events.TaskEventSource;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.message.TaskMessage;
import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

/**
 * @author cesar
 * 
 *         This class is a single thread that will listen from messaged from the
 *         client. This class is initialized in the server when a client
 *         connects. Other objects can call sendMessage() to send a message to
 *         the client, this method is not called by this thread.
 */
public class CNPConnection extends Thread {
	private Socket socket = null;
	private int id;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private TaskEventSource taskSource;
	private boolean isServer = false;
	private boolean stop = false;

	/**
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
			while ((inputLine = in.readLine()) != null && !stop) {
				TaskMessage message = new TaskMessage(inputLine);
				Task task = null;
				if (isServer) {
					task = TaskMessageFactory.fromMessageToTask(message);
					task.setClientId(id);
				} else {
					task = TaskMessageFactory
							.fromMessageToTaskResponse(message);
				}
				taskSource.fireTaskReceivedEvent(new TaskReceivedEvent(task,
						this));
			}
			System.out.println("Thread for client stopped correctly");
		} catch (IOException e) {
			System.err.println("Thread for client stopped with an exception");
		}
	}

	public void sendTask(Task task) {
		out.println(TaskMessageFactory.fromTaskToMessage(task)
				.getMessageString());
	}

	public void sendTaskResponse(TaskResponse task) {
		out.println(TaskMessageFactory.fromTaskResponseToMessage(task)
				.getMessageString());
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
	 * @return get the flag of the current status of the thread
	 */
	public boolean isStop() {
		return stop;
	}

	/**
	 * the stop flag is raised and the running thread should stop. The close()
	 * method should be called after this.
	 */
	public void stopThread() {
		this.stop = true;
	}

}