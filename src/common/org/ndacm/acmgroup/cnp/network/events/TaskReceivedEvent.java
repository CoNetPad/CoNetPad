package org.ndacm.acmgroup.cnp.network.events;

import java.util.EventObject;

import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.message.TaskMessage;

/**
 * @author cesar
 * 
 *         Event to be fired when a message is received through the network.
 *         This class is just a wrapper over a CNPTask.
 * 
 */
public class TaskReceivedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private TaskMessage task;

	/**
	 * @param task
	 *            This method should called by the TaskEventSource that is firing the
	 *            event.
	 */
	public TaskReceivedEvent(TaskMessage task) {
		super(task);
		this.task = task;
	}

	/**
	 * @return Objects receiving this event can use this method to retrieve the
	 *         CNPTask.
	 */
	public TaskMessage getTask() {
		return task;
	}
}
