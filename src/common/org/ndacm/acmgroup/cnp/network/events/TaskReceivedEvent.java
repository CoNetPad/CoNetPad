package org.ndacm.acmgroup.cnp.network.events;

import java.util.EventObject;

import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.Task;

/**
 * Event to be fired when a message is received through the network. This class
 * is just a wrapper over a CNPTask.
 * 
 * @author Cesar Ramirez
 * 
 */
public class TaskReceivedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private Task task;
	private CNPConnection connection;

	/**
	 * Default constructor.
	 * 
	 * @param task
	 *            This constructor should called by the TaskEventSource that is
	 *            firing the event.
	 */
	public TaskReceivedEvent(Task task, CNPConnection connection) {
		super(task);
		this.task = task;
		this.connection = connection;
	}

	/**
	 * Get the task for this event.
	 * 
	 * @return Objects receiving this event can use this method to retrieve the
	 *         CNPTask.
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Get the connection from which the message was received.
	 * 
	 * @return connection where messages are coming from
	 */
	public CNPConnection getConnection() {
		return connection;
	}

	/**
	 * Set the connection from which the message was received.
	 * 
	 * @param connection
	 *            CNPConnetion where we are going to be listening for events
	 */
	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}
}
