package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.CNPServer;

public abstract class TaskRequest extends Task {
	
	protected CNPServer server;
	
	public void setServer(CNPServer server) {
		this.server = server;
	}
	
	public CNPServer getServer() {
		return server;
	}

}
