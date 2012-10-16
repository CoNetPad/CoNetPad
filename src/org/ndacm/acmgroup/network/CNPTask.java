package org.ndacm.acmgroup.network;

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
