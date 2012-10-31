package common.network;

/**
 * @author cesar
 * 
 *         For the network, any message send and received is just an array of
 *         bytes. Different sets of protocols can be used to receive/send
 *         messages(xml, json and others) no matter which one is actually used
 *         but this class will be the one in charge of doing the translation.
 * 
 *         This class represents a CNP task from the point of view of the
 *         network. This class will interface the CNPTask converting it in a
 *         string for the server or in an actual CNPTask for the executor
 *         service.
 * 
 * 
 */
public class CNPTask {
	private static final char DIV = ':';
	private static final char END = '&';

	private int id;
	private int message;

	public CNPTask(int id, int message) {
		this.id = id;
		this.message = message;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return new String(Integer.toString(id) + DIV
				+ Integer.toString(message) + END);
	}

	public static CNPTask fromString(String serial) {
		return new CNPTask(Integer.parseInt(serial.split(":")[0]),
				Integer.parseInt(serial.split(":")[1].substring(serial
						.split(":")[1].length() - 1)));
	}
}
