package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.CNPClient;
import org.ndacm.acmgroup.cnp.task.Task;

public abstract class TaskResponse extends Task {
	
	protected CNPClient client;
	
	public CNPClient getClient() {
		return client;
	}
	
	// call this when CNPClient receives the task, before sending it to client executor
	public void setClient(CNPClient client) {
		this.client = client;
	}

}
