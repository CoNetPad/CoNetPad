package org.ndacm.acmgroup.cnp.network.events;

import java.util.EventObject;

import org.ndacm.acmgroup.cnp.task.Task;

/**
 * @author cesar
 * 
 *         Event to be fired when a message is received through the network.
 *         This class is just a wrapper over a CNPTask.
 * 
 */
public class TaskReceivedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private Task task;

	/**
	 * @param task
	 *            This method should called by the Component that is firing the
	 *            event.
	 */
	public TaskReceivedEvent(Task task) {
		super(task);
		this.task = task;
	}

	/**
	 * @return Objects receiving this event can use this method to retrieve the
	 *         CNPTask.
	 */
	public Task getTask() {
		return task;
	}
}
