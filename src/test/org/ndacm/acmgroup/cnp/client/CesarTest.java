package org.ndacm.acmgroup.cnp.client;

import org.ndacm.acmgroup.cnp.client.Cesar.ClientTestRunnable;
import org.ndacm.acmgroup.cnp.client.Cesar.ServerTestRunnable;


public class CesarTest {
	public static void main(String[] args) {

		new Thread( new ServerTestRunnable()).start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread( new ClientTestRunnable()).start();
		new Thread( new ClientTestRunnable()).start();

	}

}
