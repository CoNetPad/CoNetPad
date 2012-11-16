package org.ndacm.acmgroup.cnp.client;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ndacm.acmgroup.cnp.CNPClient;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;

/**
 * JUnit test class for testing CNPClient.
 *
 */
public class CNPClientTest {
	
	private static CNPClient client1;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// initialize client1
		client1 = new CNPClient("acmgroup.ndacm.org");
		client1.createSourceFile(10, "sourceFile1", SourceType.JAVA);
		client1.createSourceFile(11, "sourceFile2", SourceType.CPP);
		client1.createSourceFile(12, "sourceFile3", SourceType.GENERAL);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.client.CNPClient#getAllSourceFiles()}.
	 */
	@Test
	public void testGetAllSourceFiles() {
		fail("Not yet implemented");
	}

}
