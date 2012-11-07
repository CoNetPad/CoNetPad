package org.ndacm.acmgroup.cnp.network.event;

import java.util.EventObject;

import org.ndacm.acmgroup.cnp.task.Task;

public class MessageReceivedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private Task task;
	
	public MessageReceivedEvent(Task task) {
		super(task);
		this.task = task;
	}

	public Task getTask() {
		return task;
	}	
}
