package org.ndacm.acmgroup.cnp.client.Cesar;

import org.ndacm.acmgroup.cnp.CNPClient;
import org.ndacm.acmgroup.cnp.CNPServer;

public class ClientTestRunnable implements Runnable {

	public void run() {
		CNPClient client = new CNPClient();
		client.connectToServer("localhost");
		for (int i = 0; i < 1000; i++) {
			client.sendChatMessage(Integer.toString(i));
		}

	}
}
