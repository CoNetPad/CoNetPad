package org.ndacm.acmgroup.cnp.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.file.ServerSourceFile;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.network.event.Component;
import org.ndacm.acmgroup.cnp.network.event.MessageReceivedEvent;
import org.ndacm.acmgroup.cnp.task.EditorTask;

public class CNPConnection extends Thread {

	//	private CNPSession session;
	//	private CNPClient client;
	//	private CNPServer server;
	//	private Account account;
	//	
	//	public CNPSession getSession() {
	//		return session;
	//	}

	private Socket socket = null;
	private int id;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private Component component;
	private boolean stop = false;

	public CNPConnection(Socket socket, int id, Component component) {
		super();
		this.socket = socket;
		this.id = id;
		this.component = component;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

		} catch (IOException e) {
			System.err.println("Error establishing in/out streams");
		}
	}

	public void run() {

		try {
			System.out.println("Thread for client " + id + "started");
			String inputLine;
			while ((inputLine = in.readLine()) != null && !stop) {
				component.fireMessageReceivedEvent(new MessageReceivedEvent(new EditorTask(new Account(),0,0, new ServerSourceFile("",SourceType.GENERAL,""))));
			}
			System.out.println("Thread for client stopped correctly");
		} catch (IOException e) {
			System.err.println("Thread for client stopped with an exception");
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
	}

	public boolean isStop() {
		return stop;
	}

	public void stopThread() {
		this.stop = true;
	}

}
