package org.ndacm.acmgroup.cnp.task;

public interface SessionTaskExecutor {
	
	public void executeTask(ChatTask task);
	public void executeTask(CloseFileTask task);
	public void executeTask(CommitTask task);
	public void executeTask(CreateFileTask task);
	public void executeTask(DeleteFileTask task);
	public void executeTask(DisconnectTask task);
	public void executeTask(DownloadFileTask task);
	public void executeTask(DownloadRepoTask task);
	public void executeTask(OpenFileTask task);

}
