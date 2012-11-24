/**
 * This class handles the client connection and message sending on the client end
 * @author Cesar Ramirez
 * @version 1.5
 */
package org.ndacm.acmgroup.cnp.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.ndacm.acmgroup.cnp.task.Task;

public class ClientNetwork extends BaseNetwork {

	private CNPConnection serverConnection;

	// private SSLSocketFactory sslSocketFactory;
	// private SSLSocket socket;
	
	/**
	 * Default Constructor - DONT USE
	 * This creates a new ClientNetwork with serverConnection set to null
	 */
	public ClientNetwork() {
		serverConnection = null;

	}
	/**
	 * This creates a new clientNetwork using a server IP Address
	 * @param ipAddress		The IP address of the server
	 */
	public void connect(String ipAddress) {
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
	/**
	 * This disconnects the user from the server
	 */
	public void disconnect() {
		System.out.println("Closing connection with server");
		serverConnection.close();
	}

	/**
	 * This sends a task over the network to the server
	 * @param task		The task to send to the server
	 */
	public void sendTask(Task task) {
		serverConnection.sendTask(task);
	}
}
