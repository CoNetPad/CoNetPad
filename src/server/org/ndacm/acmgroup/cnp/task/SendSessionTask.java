package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.CNPSession;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

// for tasks that are sent to all session users (e.g. chat)
public class SendSessionTask extends Task {
	
	private CNPSession session;
	private TaskResponse response;

	public SendSessionTask(CNPSession session, TaskResponse response) {
		this.session = session;
		this.response = response;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
