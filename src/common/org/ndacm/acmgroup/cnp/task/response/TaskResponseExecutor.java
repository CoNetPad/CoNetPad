package org.ndacm.acmgroup.cnp.task.response;

/**
 * @author cesar
 *
 */
public interface TaskResponseExecutor {
	
	/**
	 * @param task
	 */
	public void executeTask(ChatTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(CloseFileTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(CommitTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(CompileTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(CreateAccountTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(CreateFileTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(CreateSessionTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(DeleteFileTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(DeleteSessionTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(LeaveSessionTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(DownloadRepoTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(EditorTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(JoinSessionTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(LoginTaskResponse task);
	/**
	 * @param task
	 */
	public void executeTask(OpenFileTaskResponse task);

}
