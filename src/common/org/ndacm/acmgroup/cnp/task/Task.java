package org.ndacm.acmgroup.cnp.task;

/**
 * Abstract class for a task. A CNPTask is executable through the run() method.
 * 
 */
public abstract class Task implements Runnable {
	protected int clientId;

	/**
	 * Get the client ID for this task.
	 * 
	 * @return the client ID
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * Set the client ID for this task.
	 * @param clientId the client ID
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}