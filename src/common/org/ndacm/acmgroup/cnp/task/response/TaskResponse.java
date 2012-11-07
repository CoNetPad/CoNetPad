package org.ndacm.acmgroup.cnp.task.response;

import org.ndacm.acmgroup.cnp.server.CNPSession;
import org.ndacm.acmgroup.cnp.task.Task;

public abstract class TaskResponse extends Task implements Runnable {

	protected CNPSession session;

	
}
