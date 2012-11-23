package org.ndacm.acmgroup.cnp.client.Cesar;

import org.ndacm.acmgroup.cnp.CNPClient;

public class ClientTestRunnableMute implements Runnable {

	public void run() {
		CNPClient client = new CNPClient();
		client.connectToServer("localhost");

	}
}
