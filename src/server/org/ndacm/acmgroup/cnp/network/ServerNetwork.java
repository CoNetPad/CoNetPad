package org.ndacm.acmgroup.cnp.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * @author cesar
 * 
 *         This class will be in charge of handling all the network connections,
 *         listening for new clients and sending/receiving messages.
 */
public class ServerNetwork extends BaseNetwork {

	private ServerSocket serverSocket;
	private ArrayList<CNPConnection> clientList;

	public void startListening() {
		try {
			System.out.println("Trying to open socket");
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444");
			System.err.println("Exiting");
			System.exit(-1);
		}
		System.out.println("Starting to listen for clients");
		int id = 0;

		clientList = new ArrayList<CNPConnection>(0);

		System.out.println("Starting clients pool");

		while (true) {
			try {
				CNPConnection thread = new CNPConnection(serverSocket.accept(),
						id, this);
				clientList.add(thread);
				thread.start();
				System.out.println("Client connected");
				id++;
			} catch (IOException e) {
				System.err.println("Error while connecting client " + id);
				break;
			}
		}

		System.out.println("Closing connections");

		for (int i = 0; i < clientList.size(); i++) {
			try {
				clientList.get(i).join();
				System.out.println("Thread " + i + " closed");
			} catch (InterruptedException e) {
				System.err.println("Error while closing thread " + i);
			}
		}
	}

	public void stopServer() {
		System.out.println("Closing network");
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error while closing server socket");
		}
		for (int i = 0; i < clientList.size(); i++) {
			clientList.get(i).close();
		}
	}

	public void stopClient(int id) {
		System.out.println("Closing connection with client " + id);
		clientList.get(id).close();
	}
	
	public void sendMessageToClient(int id, ProtoCNPTask task) {
		clientList.get(id).sendCommand(task);
	}
}
