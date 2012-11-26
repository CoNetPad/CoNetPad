package org.ndacm.acmgroup.cnp.task;

public interface ServerTaskExecutor {

	public void executeTask (CreateAccountTask task);
	public void executeTask (CreateSessionTask task);
	public void executeTask (DeleteSessionTask task);
	public void executeTask (JoinSessionTask task);
	public void executeTask (LeaveSessionTask task);
	public void executeTask (LoginTask task);
	public void executeTask (CommitTask task);
}
