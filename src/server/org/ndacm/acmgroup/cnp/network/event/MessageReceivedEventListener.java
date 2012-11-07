package org.ndacm.acmgroup.cnp.network.event;

import java.util.EventListener;

public interface MessageReceivedEventListener extends EventListener {
	
	public void MessageReceivedEventOccurred(MessageReceivedEvent evt);
}
