package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.task.Task;

/**
 * Abstract class for a task response.
 * 
 * @author Josh Tan
 *
 */
public abstract class TaskResponse extends Task {

	/**
	 * The executor for this task response.
	 */
	protected TaskResponseExecutor client;

	/**
	 * Get the client that will execute this task.
	 * 
	 * @return the client executor
	 */
	public TaskResponseExecutor getClient() {
		return client;
	}

	/**
	 * Set the client that will execute this task. Call this when CNPClient receives the
	 * task, before sending it to the client ExecutorService.
	 * 
	 * @param client
	 */
	public void setClient(TaskResponseExecutor client) {
		this.client = client;
	}

}
