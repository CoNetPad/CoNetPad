package org.ndacm.acmgroup.cnp.network;

import org.ndacm.acmgroup.cnp.Account;
import org.ndacm.acmgroup.cnp.client.CNPClient;
import org.ndacm.acmgroup.cnp.server.CNPServer;
import org.ndacm.acmgroup.cnp.server.CNPSession;

public class CNPConnection {

	private CNPSession session;
	private CNPClient client;
	private CNPServer server;
	private Account account;
	
	public CNPSession getSession() {
		return session;
	}
}
