package common.network.event;

import java.util.EventObject;

import common.network.ProtoCNPTask;

public class MessageReceivedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private ProtoCNPTask task;
	
	public MessageReceivedEvent(ProtoCNPTask task) {
		super(task);
		this.task = task;
	}

	public ProtoCNPTask getTask() {
		return task;
	}	
}
