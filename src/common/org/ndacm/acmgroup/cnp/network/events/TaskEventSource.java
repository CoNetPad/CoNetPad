package org.ndacm.acmgroup.cnp.network.events;

/**
 * This interface will allow for firing "TaskReceived" events. Other
 * objects can also register listeners for the objects implementing this
 * interface to receive those events.
 */
public interface TaskEventSource {

	/**
	 * Add a listener for when a task is received.
	 * 
	 * @param listener
	 *            Listener to be attached.
	 */
	public void addTaskReceivedEventListener(
			TaskReceivedEventListener listener);

	/**
	 * Remove a listener of task received events.
	 * 
	 * @param listener
	 *            Listener to be removed
	 */
	public void removeTaskReceivedEventListener(
			TaskReceivedEventListener listener);

	/**
	 * Fire an event signifying that a task has been received.
	 * 
	 * @param evt
	 *            This event will contain a CNPTask object, you can call
	 *            getCNPTask to retrieve it.
	 */
	public void fireTaskReceivedEvent(TaskReceivedEvent evt);
}
