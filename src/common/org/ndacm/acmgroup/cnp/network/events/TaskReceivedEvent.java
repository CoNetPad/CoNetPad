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
	 * @param task
	 *            This method should called by the TaskEventSource that is
	 *            firing the event.
	 */
	public TaskReceivedEvent(Task task, CNPConnection connection) {
		super(task);
		this.task = task;
		this.connection = connection;
	}

	/**
	 * @return Objects receiving this event can use this method to retrieve the
	 *         CNPTask.
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @return connection where messages are coming from
	 */
	public CNPConnection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            CNPConnetion where we are going to be listening for events
	 */
	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}
}
