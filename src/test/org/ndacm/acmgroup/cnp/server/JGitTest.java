/**
 * 
 */
package org.ndacm.acmgroup.cnp.server;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ndacm.acmgroup.cnp.git.JGit;

/**
 * @author Justin
 *
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
		jg.clearRepos();
	}
	

	public static void tearDown(){
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
			jg.createRepo("testRepo3");
			jg.activateRepo("testRepo3");
			jg.deactivateRepo("testRepo3");
		}
		catch(Exception e)
		{
			fail("Unable activate repo");
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#createRepo(java.lang.String)}.
	 */
	@Test
	public final void testCreateRepo() {
		
		
		try{
			jg.deleteRepo("testRepo");
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
			jg.createRepo("testRepo");
			
			boolean test = jg.deleteRepo("testRepo");
			assertTrue(test);
			//jg.createRepo("testRepo");
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
			jg.createRepo("testRepo2");
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
			jg.addFileToRepo("testFile", new File("blarg.txt"));
		}
		catch(Exception e)
		{
			fail("Unable to add new file");
		}
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#removeFileFromRepo(java.lang.String, java.io.File)}.
	 */
	@Test
	public final void testRemoveFileFromRepo() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#commitToRepo(int, java.lang.String)}.
	 */
	@Test
	public final void testCommitToRepo() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.ndacm.acmgroup.cnp.git.JGit#retrieveRepo(java.lang.String)}.
	 */
	@Test
	public final void testRetrieveRepo() {
		fail("Not yet implemented"); // TODO
	}

}
