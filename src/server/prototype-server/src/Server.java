/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

	private ServerSocket serverSocket;
	private BlockingQueue<Integer> queue;
	private boolean listening;
	private QueueThread qthread;
	public ArrayList<ConnectionThread> clientList;
	public Server() {
		serverSocket = null;
		listening = true;
		queue = new LinkedBlockingQueue<Integer>();
	}

	public void run() {
		
		try {
			System.out.println("Trying to open socket");
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(-1);
		}
		System.out.println("Starting to listen for clients");
		int id = 0;
		
		clientList = new ArrayList<ConnectionThread>(0);

		System.out.println("Starting queue thread");
		qthread = new QueueThread(queue, clientList, this);
		qthread.start();		
		
		while (listening) {
			try {
				new ConnectionThread(serverSocket.accept(), id, queue, this).start();
				System.out.println("Client connected");
				id++;
			} catch (IOException e) {
				e.printStackTrace();
				listening = false;
			}
		}
		
		System.out.println("Closing connections");
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clientDisconnects(){
		qthread.clientDisconnects();
	}
	
	public void stopServeer(){
		for (int i = 0; i < clientList.size(); i++) {
			clientList.get(i).finalize();
		}
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}