package org.ndacm.acmgroup.cnp.client.Cesar;
import org.ndacm.acmgroup.cnp.CNPServer;


	public class ServerTestRunnable implements Runnable {

		public void run() {
			CNPServer server = new CNPServer("");
			server.startNetwork();
		}
	}
