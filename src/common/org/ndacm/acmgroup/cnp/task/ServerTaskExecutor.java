package org.ndacm.acmgroup.cnp.task;

/**
 * Interface for an executor of tasks at the level of the server.
 * 
 * @author Josh Tan
 */
public interface ServerTaskExecutor {

	/**
	 * Execute a create account task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask (CreateAccountTask task);

	/**
	 * Execute a create session task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask (CreateSessionTask task);

	/**
	 * Execute a delete session task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask (DeleteSessionTask task);

	/**
	 * Execute a join session task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask (JoinSessionTask task);

	/**
	 * Execute a leave session task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask (LeaveSessionTask task);

	/**
	 * Execute a login task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask (LoginTask task);

	/**
	 * Execute a commit task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask (CommitTask task);
}
