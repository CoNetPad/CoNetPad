package org.ndacm.acmgroup.cnp.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.ndacm.acmgroup.cnp.task.Task;

public class ClientNetwork extends BaseNetwork {

	private CNPConnection serverConnection;
//	private SSLSocketFactory sslSocketFactory;
//	private SSLSocket socket;
	
	public ClientNetwork() {
		serverConnection = null;

	}

	public void connect(String ipAddress) {
		try {
			serverConnection = new CNPConnection(new Socket(ipAddress, 4444),
					0, this);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		// https://www.securecoding.cert.org/confluence/display/java/MSC00-J.+Use+SSLSocket+rather+than+Socket+for+secure+data+exchange
//		sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//		
//		try {
//			
//			socket = (SSLSocket) sslSocketFactory.createSocket("localhost", 9999);
//			serverOut = new PrintWriter(socket.getOutputStream(), true);
//			serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			
//		} catch (IOException e) {
//			// try to handle it
//		} finally {
//			if (socket != null) {
//				try {
//					socket.close();
//				} catch (IOException e) {
//					System.err.println("Error closing client socket.");
//					e.printStackTrace();
//				}
//			}
//		}
		
	}

	public void disconnect() {
		System.out.println("Closing connection with server");
		serverConnection.close();
	}
	
	public void sendTask(Task task) {
		serverConnection.sendTask(task);
	}
	
	public void sendMessage(ProtoCNPTask task){
		serverConnection.sendCommand(task);		
	}
}
