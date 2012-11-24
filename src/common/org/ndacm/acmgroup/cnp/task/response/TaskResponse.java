package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.task.Task;

public abstract class TaskResponse extends Task {
	
	protected TaskResponseExecutor client;
	
	public TaskResponseExecutor getClient() {
		return client;
	}
	
	// call this when CNPClient receives the task, before sending it to client executor
	public void setClient(TaskResponseExecutor client) {
		this.client = client;
	}

}
