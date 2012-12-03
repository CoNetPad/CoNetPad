package org.ndacm.acmgroup.cnp.file;

import static org.junit.Assert.assertEquals;

import java.awt.Event;
import java.io.File;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;

/**
 * JUnit test case for ClientSourceFile
 *
 */
public class ClientSourceFileTest {
	
	public static ClientSourceFile file;
	public EditorTaskResponse response;
	public static ClientSourceFile file1;
	public static ClientSourceFile file2;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		file1 = new ClientSourceFile(189, "testfile1.cpp", SourceType.CPP, "whoa", null);
		file2 = new ClientSourceFile(190, "testfile2.cpp", SourceType.CPP, "", null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		file = new ClientSourceFile(101, "HelloWorld.java",SourceType.JAVA,"Hello World!", null);
		response = new EditorTaskResponse("jotan", 65, 6, 101, true);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File file = new File("HelloWorld.java");
		File file1 = new File("testfile1.cpp");
		File file2 = new File("testfile2.cpp");
		file.delete();
		file1.delete();
		file2.delete();
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.file.ClientSourceFile#editSource(org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse)}.
	 */
	@Test
	public final void testEditSourceEditorTaskResponse() {
		file.editSource(response);
		assertEquals("Hello AWorld!", file.toString());
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.file.SourceFile#editSource(int, int)}.
	 */
	@Test
	public final void testEditSourceIntInt() {
		//file.editSource(66, 6);
		file.editSource((int) 'd', 2);
		assertEquals("Hedllo World!", file.toString());
		file.editSource(Event.BACK_SPACE, 3);
		assertEquals("Hello World!", file.toString());
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.file.SourceFile#toString()}.
	 */
	@Test
	public final void testToString() {
		assertEquals(file1.toString(), "whoa");
	}

}