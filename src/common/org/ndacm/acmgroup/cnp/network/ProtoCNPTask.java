package org.ndacm.acmgroup.cnp.network;

import org.ndacm.acmgroup.cnp.task.Task;

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
public class ProtoCNPTask extends Task {
	protected static final char DIV = ':';
	protected static final char END = '&';

	protected int id;
	protected int message;

	/**
	 * @param id
	 * @param message
	 */
	public ProtoCNPTask(int id, int message) {
		this.id = id;
		this.message = message;
	}

	/**
	 * @return
	 */
	public int getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(int message) {
		this.message = message;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new String(Integer.toString(id) + DIV
				+ Integer.toString(message) + END);
	}

	/**
	 * @param serial
	 *            String representation of the CNPTask, this is the message
	 *            recieved from the network and it will be translated.
	 * @return a CNPTask object that represents the string recieved.
	 */
	public static ProtoCNPTask fromString(String serial) {
		return new ProtoCNPTask(Integer.parseInt(serial.split(":")[0]),
				Integer.parseInt(serial.split(":")[1].substring(serial
						.split(":")[1].length() - 1)));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
