package org.ndacm.acmgroup.cnp.file;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;

public class ClientSourceFileTest {
	
	public static ClientSourceFile file;
	public static EditorTaskResponse response;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		file = new ClientSourceFile(101, "HelloWorld.java",SourceType.JAVA,"Hello World!", null);
		response = new EditorTaskResponse("jotan", 65, 6, 101, true);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEditSourceEditorTaskResponse() {
		file.editSource(response);
		assertEquals("Hello AWorld!", file.toString());
	}

	@Test
	public void testEditSourceIntInt() {
		file.editSource(66, 6);
		assertEquals("Hello BAWorld!", file.toString());
	}

	@Test
	public void testToFile() {
		fail("Not yet implemented");
	}

}
