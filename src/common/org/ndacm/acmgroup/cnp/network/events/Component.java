package common.network.events;

/**
 * @author cesar
 * 
 *         This interface will allow for firing "MessageRecieved" events. Other
 *         objects can also register listeners for the objects implementing this
 *         interface to receive those events.
 */
public interface Component {
	/**
	 * @param listener
	 *            Listener to be attached.
	 */
	public void addMessageReceivedEventListener(
			MessageReceivedEventListener listener);

	/**
	 * @param listener
	 *            Listener to be removed
	 */
	public void removeMessageReceivedEventListener(
			MessageReceivedEventListener listener);

	/**
	 * @param evt
	 *            This event will contain a CNPTask object, you can call
	 *            getCNPTask to retrieve it.
	 */
	public void fireMessageReceivedEvent(MessageReceivedEvent evt);
}
