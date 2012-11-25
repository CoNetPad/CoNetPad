package org.ndacm.acmgroup.cnp;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.ndacm.acmgroup.cnp.client.CNPClientTest;
import org.ndacm.acmgroup.cnp.client.CesarTest;
import org.ndacm.acmgroup.cnp.client.JustinTest;

public class CNPTestSuite extends TestSuite {

	public static Test suite(){
		TestSuite suite = new TestSuite();

		suite.addTestSuite(CNPClientTest.class);
		suite.addTestSuite(CesarTest.class);
		suite.addTestSuite(JustinTest.class);

		return suite;
	}

}

