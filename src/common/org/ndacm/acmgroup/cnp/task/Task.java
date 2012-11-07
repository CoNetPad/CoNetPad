package org.ndacm.acmgroup.cnp.task;

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

// Cesar's stuff:
//
//private static final char DIV = ':';
//private static final char END = '&';
//
//private int id;
//private int message;
//
//public CNPTask(int id, int message) {
//	this.id = id;
//	this.message = message;
//}
//
//public int getMessage() {
//	return message;
//}
//
//public void setMessage(int message) {
//	this.message = message;
//}
//
//public int getId() {
//	return id;
//}
//
//public void setId(int id) {
//	this.id = id;
//}
//
//public String toString() {
//	return new String(Integer.toString(id) + DIV
//			+ Integer.toString(message) + END);
//}
//
//public static CNPTask fromString(String serial) {
//	return new CNPTask(Integer.parseInt(serial.split(":")[0]),
//			Integer.parseInt(serial.split(":")[1].substring(serial
//					.split(":")[1].length() - 1)));
//}
