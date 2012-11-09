package org.ndacm.acmgroup.cnp.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientNetwork extends BaseNetwork {

	private CNPConnection serverConnection;

	public void connect(String ipAddress) {
		try {
			serverConnection = new CNPConnection(new Socket(ipAddress, 4444),
					0, this);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		System.out.println("Closing connection with server");
		serverConnection.close();
	}
	
	public void sendMessage(ProtoCNPTask task){
		serverConnection.sendCommand(task);		
	}
}
