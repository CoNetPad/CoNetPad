package server.org.ndacm.acmgroup.cnp.network;

import client.org.ndacm.acmgroup.cnp.client.CNPClient;
import server.org.ndacm.acmgroup.cnp.Account;
import server.org.ndacm.acmgroup.cnp.server.*;


public class CNPConnection {

	private CNPSession session;
	private CNPClient client;
	private CNPServer server;
	private Account account;
	
	public CNPSession getSession() {
		return session;
	}
}
