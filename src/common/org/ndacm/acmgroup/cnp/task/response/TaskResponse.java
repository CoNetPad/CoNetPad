package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.task.Task;

/**
 * @author cesar
 *
 */
public abstract class TaskResponse extends Task {
	
	/**
	 * 
	 */
	protected TaskResponseExecutor client;
	
	/**
	 * @return
	 */
	public TaskResponseExecutor getClient() {
		return client;
	}
	
	// call this when CNPClient receives the task, before sending it to client executor
	/**
	 * @param client
	 */
	public void setClient(TaskResponseExecutor client) {
		this.client = client;
	}

}
