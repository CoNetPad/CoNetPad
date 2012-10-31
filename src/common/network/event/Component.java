package common.network.event;

public interface Component {
	public void addMessageReceivedEventListener(
			MessageReceivedEventListener listener);

	public void removeMessageReceivedEventListener(
			MessageReceivedEventListener listener);

	public void fireMessageReceivedEvent(MessageReceivedEvent evt);
}
