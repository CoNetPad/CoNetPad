/**
 * This is a response task to send to clients
 * @author Cesar Ramirez, Josh Tan
 * @version 1.5
 */
package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

// for tasks that are sent only to users with a file open
public class SendResponseTask extends Task {

	private TaskResponse response;			//The TaskReponse 
	private CNPConnection client;			//The client's connect
	
	/**
	 * The default constructor
	 * @param response		The Task response to send to the user
	 * @param client		The Client connection to send the response to
	 */
	public SendResponseTask(TaskResponse response, CNPConnection client) {
	
		this.response = response;
		this.client = client;
	}
	/**
	 * This executes the task response or sends the task response to the user.
	 */
	@Override
	public void run() {
		client.sendTaskResponse(response);
		
	}

}
