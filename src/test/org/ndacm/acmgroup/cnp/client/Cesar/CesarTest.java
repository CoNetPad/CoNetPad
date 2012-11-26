package org.ndacm.acmgroup.cnp.client.Cesar;

import junit.framework.TestCase;



public class CesarTest extends TestCase {
	public static void main(String[] args) {

		new Thread( new ServerTestRunnable()).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread( new ClientTestRunnable()).start();
		new Thread( new ClientTestRunnableMute()).start();

	}

}
