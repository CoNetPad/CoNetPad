package common.network.events;

import java.util.EventListener;

/**
 * @author cesar
 * 
 *         Listener class that should be extended when listening for events. You
 *         can use this interface to define what is going to be done when a
 *         message is received.
 * 
 */
public interface MessageReceivedEventListener extends EventListener {
	/**
	 * @param evt
	 * 
	 *            Use this method to receive the event and take an action based
	 *            on such event.
	 */
	public void MessageReceivedEventOccurred(MessageReceivedEvent evt);
}
