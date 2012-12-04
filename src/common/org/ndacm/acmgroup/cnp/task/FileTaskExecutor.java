package org.ndacm.acmgroup.cnp.task;

/**
 * Interface for task executors at the level of the file.
 *  
 * @author Josh Tan
 *
 */
public interface FileTaskExecutor {

	/**
	 * Execute an editor task.
	 * @param task the task to execute
	 */
	public void executeTask(EditorTask task);

	/**
	 * Get the filename for the task.
	 * @return the filename
	 */
	public String getFilename();

}
