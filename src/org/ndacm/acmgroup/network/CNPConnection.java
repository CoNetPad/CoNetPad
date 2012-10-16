package org.ndacm.acmgroup.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class CNPConnection extends Thread {
	private Socket socket = null;
	private int id;
	private BlockingQueue<Integer> queue;
	private PrintWriter out = null;
	private BufferedReader in = null;
	public boolean isRunning = false;

	public CNPConnection(Socket socket, int id) {
		super();
		this.socket = socket;
		this.id = id;
		this.queue = queue;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			System.out.println("Thread for client " + id + "started");
			isRunning = true;
			String inputLine;
			int outputCode;
			Protocol kkp = new Protocol();
			boolean stop = false;
			while ((inputLine = in.readLine()) != null && !stop) {
				outputCode = kkp.processInput(inputLine);
				if (outputCode == -1)
					stop = true;
				try {
					queue.put(new Integer(outputCode));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Thread for client stopped");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendCommand(int command) {
		out.println(command);
	}

	public void close() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isRunning = false;
	}

}