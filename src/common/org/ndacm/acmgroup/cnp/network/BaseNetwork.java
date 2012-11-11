package org.ndacm.acmgroup.cnp.network;

import javax.swing.event.EventListenerList;

import org.ndacm.acmgroup.cnp.network.events.TaskEventSource;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEventListener;
import org.ndacm.acmgroup.cnp.network.events.TaskReceivedEvent;

public class BaseNetwork implements TaskEventSource {

	private EventListenerList listenerList = new EventListenerList();

	public void addTaskReceivedEventListener(
			TaskReceivedEventListener listener) {
		listenerList.add(TaskReceivedEventListener.class, listener);
	}

	public void removeTaskReceivedEventListener(
			TaskReceivedEventListener listener) {
		listenerList.remove(TaskReceivedEventListener.class, listener);
	}

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
