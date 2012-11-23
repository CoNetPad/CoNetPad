package org.ndacm.acmgroup.cnp.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.ndacm.acmgroup.cnp.task.Task;

public class ClientNetwork extends BaseNetwork {

	private CNPConnection serverConnection;

	// private SSLSocketFactory sslSocketFactory;
	// private SSLSocket socket;

	public ClientNetwork() {
		serverConnection = null;

	}

	public boolean connect(String ipAddress) {
		try {
			serverConnection = new CNPConnection(new Socket(ipAddress, 4444),
					0, this, false);
			serverConnection.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void disconnect() {
		System.out.println("Closing connection with server");
		serverConnection.close();
	}

	public void sendTask(Task task) {
		serverConnection.sendTask(task);
	}
}
