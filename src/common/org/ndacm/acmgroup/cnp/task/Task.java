package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.CNPServer;

/**
 * Abstract class for a task. A CNPTask is executable through the run() method.
 *
 */
public abstract class Task implements Runnable {
	
	private CNPServer server;
	
	public void setServer(CNPServer server) {
		this.server = server;
	}
	
}
