package common.network.events;

import java.util.EventObject;

import common.network.ProtoCNPTask;

/**
 * @author cesar
 * 
 *         Event to be fired when a message is received through the network.
 *         This class is just a wrapper over a CNPTask.
 * 
 */
public class MessageReceivedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private ProtoCNPTask task;

	/**
	 * @param task
	 *            This method should called by the Component that is firing the
	 *            event.
	 */
	public MessageReceivedEvent(ProtoCNPTask task) {
		super(task);
		this.task = task;
	}

	/**
	 * @return Objects receiving this event can use this method to retrieve the
	 *         CNPTask.
	 */
	public ProtoCNPTask getTask() {
		return task;
	}
}
