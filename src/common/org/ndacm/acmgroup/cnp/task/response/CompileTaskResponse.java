package org.ndacm.acmgroup.cnp.task.response;

/**
 * Task response for compiling session source files.
 * 
 * @author Josh Tan
 *
 */
public class CompileTaskResponse extends TaskResponse {

	private String compilerMessage;
	private boolean success;

	/**
	 * Default constructor.
	 * 
	 * @param compilerMessage the compiler output message
	 * @param success the success status for the task
	 */
	public CompileTaskResponse(String compilerMessage, boolean success) {
		this.compilerMessage = compilerMessage;
		this.success = success;
	}

	/**
	 * Execute this task.
	 */
	@Override
	public void run() {
		client.executeTask(this);
	}

	/**
	 * Get the compiler message for the task.
	 * 
	 * @return the compiler message
	 */
	public String getCompilerMessage() {
		return compilerMessage;
	}

	/**
	 * Set the success status for the task.
	 * @return the success status
	 */
	public boolean isSuccess() {
		return success;
	}

}
