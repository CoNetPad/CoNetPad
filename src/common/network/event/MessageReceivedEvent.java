package common.network.event;

import java.util.EventObject;

import common.network.CNPTask;

public class MessageReceivedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private CNPTask task;
	
	public MessageReceivedEvent(CNPTask task) {
		super(task);
		this.task = task;
	}

	public CNPTask getTask() {
		return task;
	}	
}
