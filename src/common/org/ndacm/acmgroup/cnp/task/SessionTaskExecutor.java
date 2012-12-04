package org.ndacm.acmgroup.cnp.task;

/**
 * Interface for a task executor at the level of a session.
 * 
 * @author Josh Tan
 *
 */
public interface SessionTaskExecutor {

	/**
	 * Execute a chat task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(ChatTask task);

	/**
	 * Execute a close file task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(CloseFileTask task);

	/**
	 * Execute a create file task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(CreateFileTask task);

	/**
	 * Execute a delete file task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(DeleteFileTask task);

	/**
	 * Execute a compile task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(CompileTask task);

	/**
	 * Execute a download repository task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(DownloadRepoTask task);

	/**
	 * Execute a open file task.
	 * 
	 * @param task the task to execute
	 */
	public void executeTask(OpenFileTask task);

}
