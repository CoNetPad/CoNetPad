package common.org.ndacm.acmgroup.cnp.task;

/**
 * Abstract class for a task. A CNPTask unifies the execution of a task under a single method call: execute().
 * Every class that extends CNPTask should provide an appropriate definition for PERMISSION.
 *
 */
public abstract class Task {
	
	/**
	 * Execute the task associated with this CNPTask. Will call either run() (for Runnable) or 
	 * call() (for Callable). 
	 */
	
	public abstract void execute();
	
}
