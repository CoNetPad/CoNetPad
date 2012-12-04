package org.ndacm.acmgroup.cnp.task.response;

/**
 * Interface for a component that executes task responses. The
 * client will need to implement this interface.
 * 
 * @author Josh Tan
 *
 */
public interface TaskResponseExecutor {

	/**
	 * Execute a chat response task.
	 * 
	 * @param task the chat task to execute
	 */
	public void executeTask(ChatTaskResponse task);

	/**
	 * Execute a close file response task.
	 * 
	 * @param task the close file task to execute
	 */
	public void executeTask(CloseFileTaskResponse task);

	/**
	 * Execute a commit response task.
	 * 
	 * @param task the commit task to execute
	 */
	public void executeTask(CommitTaskResponse task);

	/**
	 * Execute a compile response task.
	 * 
	 * @param task the compile task to execute
	 */
	public void executeTask(CompileTaskResponse task);

	/**
	 * Execute a create account response task.
	 * 
	 * @param task the create account task to execute
	 */
	public void executeTask(CreateAccountTaskResponse task);

	/**
	 * Execute a create file response task.
	 * 
	 * @param task the create file task to execute
	 */
	public void executeTask(CreateFileTaskResponse task);

	/**
	 * Execute a create session response task.
	 * 
	 * @param task the create session task to execute
	 */
	public void executeTask(CreateSessionTaskResponse task);

	/**
	 * Execute a delete file response task.
	 * 
	 * @param task the delete file task to execute
	 */
	public void executeTask(DeleteFileTaskResponse task);

	/**
	 * Execute a delete session response task.
	 * 
	 * @param task the delete session task to execute
	 */
	public void executeTask(DeleteSessionTaskResponse task);

	/**
	 * Execute a leave session response task.
	 * 
	 * @param task the leave session task to execute
	 */
	public void executeTask(LeaveSessionTaskResponse task);

	/**
	 * Execute a download repository response task.
	 * 
	 * @param task the download repository task to execute
	 */
	public void executeTask(DownloadRepoTaskResponse task);

	/**
	 * Execute a editor response task.
	 * 
	 * @param task the editor task to execute
	 */
	public void executeTask(EditorTaskResponse task);

	/**
	 * Execute a join session response task.
	 * 
	 * @param task the join session task to execute
	 */
	public void executeTask(JoinSessionTaskResponse task);

	/**
	 * Execute a login response task.
	 * 
	 * @param task the login task to execute
	 */
	public void executeTask(LoginTaskResponse task);

	/**
	 * Execute a open file response task.
	 * 
	 * @param task the open file task to execute
	 */
	public void executeTask(OpenFileTaskResponse task);

}
