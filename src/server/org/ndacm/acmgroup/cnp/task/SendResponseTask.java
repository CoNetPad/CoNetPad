package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

// for tasks that are sent only to users with a file open
public class SendResponseTask extends Task {

	private TaskResponse response;
	private CNPConnection client;
	
	public SendResponseTask(TaskResponse response, CNPConnection client) {
	
		this.response = response;
		this.client = client;
	}

	@Override
	public void run() {
		client.sendTaskResponse(response);
		
	}

}