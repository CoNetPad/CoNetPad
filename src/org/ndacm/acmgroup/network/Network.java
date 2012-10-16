package org.ndacm.acmgroup.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Network {

	private ServerSocket serverSocket;
	public ArrayList<CNPConnection> clientList;

	public void startListening() {
		try {
			System.out.println("Trying to open socket");
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(-1);
		}
		System.out.println("Starting to listen for clients");
		int id = 0;

		clientList = new ArrayList<CNPConnection>(0);

		System.out.println("Starting queue thread");

		while (true) {
			try {
				CNPConnection thread = new CNPConnection(serverSocket.accept(),
						id);
				clientList.add(thread);
				thread.start();
				System.out.println("Client connected");
				id++;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}

		System.out.println("Closing connections");

		for (int i = 0; i < clientList.size(); i++) {
			try {
				clientList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopServer() {
		System.out.println("Closing network");
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < clientList.size(); i++) {
			clientList.get(i).close();
		}
	}

	public void stopClient(int id) {
		System.out.println("Closing connection with client " + id);
		clientList.get(id).close();
	}
}
