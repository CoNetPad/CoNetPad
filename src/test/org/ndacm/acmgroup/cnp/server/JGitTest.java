package org.ndacm.acmgroup.cnp.server;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ndacm.acmgroup.cnp.git.JGit;

/**
 * JUnit test for testing JGit.
 * 
 * @author Justin
 */
public class JGitTest {

	public static JGit jg;
	public static File file;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		file = new File(".");
		jg = new JGit(file);

		jg.createRepo("testRepo1");
		jg.createRepo("testRepo2");
		jg.createRepo("testRepo3");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		jg.clearRepos();
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#JGit(java.io.File)}.
	 */
	@Test
	public final void testJGitFile() {
		assertNotNull(jg);
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#deactivateRepo(java.lang.String)}.
	 */
	@Test
	public final void testDeactivateRepo() {

		try{
			jg.activateRepo("testRepo2");
			jg.deactivateRepo("testRepo2");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("Unable de-activate repo");
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#createRepo(java.lang.String)}.
	 */
	@Test
	public final void testCreateRepo() {
		try{
			jg.createRepo("testRepo");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("Unable to create repo");

		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#deleteRepo(java.lang.String)}.
	 */
	@Test
	public final void testDeleteRepo() {

		try{
			jg.createRepo("testRepo5");
			boolean test = jg.deleteRepo("testRepo5");
			assertTrue(test);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("Unable to create repo");

		}
		catch(AssertionError e)
		{
			fail("Delete failed");
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#activateRepo(java.lang.String)}.
	 */
	@Test
	public final void testActivateRepo() {

		try{
			jg.activateRepo("testRepo2");
		}
		catch(Exception e)
		{
			fail("Unable activate repo");
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#addFileToRepo(java.lang.String, java.io.File)}.
	 */
	@Test
	public final void testAddFileToRepo() {
		try{
			File test =  new File("blarg.txt");
			jg.activateRepo("testRepo1");
			jg.addFileToRepo("testRepo1", test);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("Unable to add new file");
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#removeFileFromRepo(java.lang.String, java.io.File)}.
	 */
	@Test
	public final void testRemoveFileFromRepo() {
		try{
			File test =  new File("blarg.txt");
			jg.activateRepo("testRepo1");
			jg.addFileToRepo("testRepo1", test);
			jg.removeFileFromRepo("testRepo1", test);
		}
		catch(Exception e)
		{	
			e.printStackTrace();
			fail("Unable to delete file");
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#retrieveRepo(java.lang.String)}.
	 */
	@Test
	public final void testRetrieveRepo() {
		File test = jg.retrieveRepo("testRepo1");
		assertNotNull(test);
	}

}
