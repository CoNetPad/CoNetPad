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

import java.io.*;
import java.net.*;
import java.util.Random;

public class TestClient extends Thread {
	
	private int id;
	
	public TestClient(int id){
		this.id = id;
	}
	
	public void run() {

Socket kkSocket = null;
		PrintWriter out = null;
		
		try {
			System.out.println("Opening client-socket");
			kkSocket = new Socket("127.0.0.1", 4444);
			out = new PrintWriter(kkSocket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host.");
			System.exit(1);
		} catch (IOException e) {
			System.err
					.println("Couldn't get I/O for the connection to the host.");
			System.exit(1);
		}

		Random rand = new Random();
		int code = -1;
		for(int i = 0; i < 1000; i++){
			code = rand.nextInt(100);
			//System.out.println(id + "] Sending: " + code);
			System.out.println(id + "] " + i);
			out.println(code);
			/*try {
				Thread.sleep(code * 25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
		out.println(-1);

		System.out.println("Closing Connections");
		out.close();
		try {
			kkSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}