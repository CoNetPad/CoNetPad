package org.ndacm.acmgroup.cnp.client.Cesar;

import junit.framework.TestCase;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class CesarTest extends TestCase {
	public static void main(String[] args) {

		new Thread( new ServerTestRunnable()).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			fail("Unable to start server");
			e.printStackTrace();
		}
	
		try{
			new Thread( new ClientTestRunnable()).start();
			new Thread( new ClientTestRunnableMute()).start();
		}
		catch(Exception e)
		{
			fail("Unable to run clients");
		}

	}

}
