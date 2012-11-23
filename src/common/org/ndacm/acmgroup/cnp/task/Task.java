package org.ndacm.acmgroup.cnp.task;

/**
 * Abstract class for a task. A CNPTask is executable through the run() method.
 * 
 */
public abstract class Task implements Runnable {
	protected int clientId;

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}