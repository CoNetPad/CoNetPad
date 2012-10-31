package common.network.event;

import java.util.EventListener;

public interface MessageReceivedEventListener extends EventListener {
	public void MessageReceivedEventOccurred(MessageReceivedEvent evt);
}
