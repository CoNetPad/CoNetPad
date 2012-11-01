package common.network;

import javax.swing.event.EventListenerList;

import common.network.events.Component;
import common.network.events.MessageReceivedEvent;
import common.network.events.MessageReceivedEventListener;

public class BaseNetwork implements Component {

	private EventListenerList listenerList = new EventListenerList();

	public void addMessageReceivedEventListener(
			MessageReceivedEventListener listener) {
		listenerList.add(MessageReceivedEventListener.class, listener);
	}

	public void removeMessageReceivedEventListener(
			MessageReceivedEventListener listener) {
		listenerList.remove(MessageReceivedEventListener.class, listener);
	}

	public void fireMessageReceivedEvent(MessageReceivedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == MessageReceivedEventListener.class) {
				((MessageReceivedEventListener) listeners[i + 1])
						.MessageReceivedEventOccurred(evt);
			}
		}
	}

}
