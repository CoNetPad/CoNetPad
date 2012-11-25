package org.ndacm.acmgroup.cnp.task.response;

public interface TaskResponseExecutor {
	
	public void executeTask(ChatTaskResponse task);
	public void executeTask(CloseFileTaskResponse task);
	public void executeTask(CommitTaskResponse task);
	public void executeTask(CompileTaskResponse task);
	public void executeTask(CreateAccountTaskResponse task);
	public void executeTask(CreateFileTaskResponse task);
	public void executeTask(CreateSessionTaskResponse task);
	public void executeTask(DeleteFileTaskResponse task);
	public void executeTask(DeleteSessionTaskResponse task);
	public void executeTask(LeaveSessionTaskResponse task);
	public void executeTask(DownloadFileTaskResponse task);
	public void executeTask(DownloadRepoTaskResponse task);
	public void executeTask(EditorTaskResponse task);
	public void executeTask(JoinSessionTaskResponse task);
	public void executeTask(LoginTaskResponse task);
	public void executeTask(OpenFileTaskResponse task);

}
