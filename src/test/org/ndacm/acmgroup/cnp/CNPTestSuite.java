package org.ndacm.acmgroup.cnp;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.ndacm.acmgroup.cnp.file.ClientSourceFileTest;

public class CNPTestSuite extends TestSuite {

	public static Test suite(){
		TestSuite suite = new TestSuite();


		suite.addTestSuite(ClientSourceFileTest.class);

		return suite;
	}

}


