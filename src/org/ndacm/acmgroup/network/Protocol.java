package org.ndacm.acmgroup.network;

public class Protocol {

	public int processInput(String theInput) {
		int theOutput = 0;

		try {
			theOutput = Integer.parseInt(theInput);
		} catch (Exception e) {
			return -1;
		}
		return theOutput;
	}
}