package org.ndacm.acmgroup.cnp.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import org.ndacm.acmgroup.cnp.task.response.TaskResponse;


/**
 * This class will be in charge of handling all the network connections,
 * listening for new clients and sending/receiving messages.
 * @author Cesar Ramirez
 * @version 2.0
 */
public class ServerNetwork extends BaseNetwork {

	public static final int SOCKET_NUMBER = 4444;				//The socket number to listen on
	public static int nextClientNum;							//A constant or getting the next client
	private ServerSocket serverSocket;							//The server socket for connect handling
	private ArrayList<CNPConnection> clientList;				//The arrayList of clients
	private boolean shouldStop; 								// whether the server should be stopped or not
	
	/**
	 * Default Constructor
	 * This initiates a new server, but does no listening.
	 */
	public ServerNetwork() {
		shouldStop = false;
		nextClientNum = 1;
		clientList = new ArrayList<CNPConnection>();
	}
	/**
	 * This listens for new clients and adds them to the client list
	 */
	public void startListening() {

		try {
			System.out.println("Trying to open socket: " + SOCKET_NUMBER);
			serverSocket = new ServerSocket(SOCKET_NUMBER);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + SOCKET_NUMBER);
			System.err.println("Exiting.");
			System.exit(-1);
		}

		System.out.println("Starting to listen for clients");
		int id = 0;

		System.out.println("Starting clients pool");
		while (!shouldStop) {
			// listen for threads
			try {
				CNPConnection thread = new CNPConnection(serverSocket.accept(),
						id, this, true);
				clientList.add(thread);
				thread.start();
				System.out.println("Client connected.");
				id++;
			} catch (IOException e) {
				System.err.println("Error while connecting client.");
			}
		}

		// shut down server
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error while closing server socket");
		}

		// close client connections
		System.out.println("Closing connections.");
		for (int i = 0; i < clientList.size(); i++) {
			clientList.get(i).close();
		}

		// wait for client threads to die
		for (int i = 0; i < clientList.size(); i++) {
			try {
				clientList.get(i).join();
				System.out.println("Thread " + i + " closed.");
			} catch (InterruptedException e) {
				System.err.println("Error while closing thread " + i);
			}
		}
	}
	/**
	 * This stops the server from listening t clients
	 */
	public void stopServer() {
		System.out.println("Closing network");
		shouldStop = true;

	}
	/**
	 * This disconnects or stops a client from sending messages
	 * @param id			The Unique ID of the client
	 */
	public void stopClient(int id) {
		System.out.println("Closing connection with client " + id);
		clientList.get(id).close();
	}
	/**
	 * This sends a specific task response to a given client
	 * @param id			The unique ID of the client or user
	 * @param task			The task response to send them
	 */
	public void sendTaskResponseToClient(int id, TaskResponse task) {
		clientList.get(id).sendTaskResponse(task);
	}
	/**
	 * This sends a response task to all clients
	 * @param task			The task to send all clients
	 */
	public void sendTaskResponseToAllClients(TaskResponse task) {
		for(CNPConnection client : clientList){
			client.sendTaskResponse(task);			
		}
	}
}
