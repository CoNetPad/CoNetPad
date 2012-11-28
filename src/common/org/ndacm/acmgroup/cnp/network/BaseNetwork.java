package org.ndacm.acmgroup.cnp.network;

import javax.swing.event.EventListenerList;


import org.ndacm.acmgroup.cnp.network.events.TaskEventSource;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;

/**
 * Ths is used a parent class for the connections on the client and server side
 * @author Cesar Ramirez
 */
public class BaseNetwork implements TaskEventSource {

	/**
	 * List of objects listening for events
	 */
	private EventListenerList listenerList = new EventListenerList();


	public void addTaskReceivedEventListener(TaskReceivedEventListener listener) {
		listenerList.add(TaskReceivedEventListener.class, listener);
	}

	/* (non-Javadoc)
	 * @see org.ndacm.acmgroup.cnp.network.events.TaskEventSource#removeTaskReceivedEventListener(org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener)
	 */
	public void removeTaskReceivedEventListener(
			TaskReceivedEventListener listener) {
		listenerList.remove(TaskReceivedEventListener.class, listener);
	}

	/* (non-Javadoc)
	 * @see org.ndacm.acmgroup.cnp.network.events.TaskEventSource#fireTaskReceivedEvent(org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent)
	 */
	public void fireTaskReceivedEvent(TaskReceivedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TaskReceivedEventListener.class) {
				((TaskReceivedEventListener) listeners[i + 1])
						.TaskReceivedEventOccurred(evt);
			}
		}
	}

}
