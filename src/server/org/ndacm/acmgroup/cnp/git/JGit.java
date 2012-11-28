package org.ndacm.acmgroup.cnp.git;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;

/**
 * @author cesar
 * 
 *         This class will interface with the git repos. The JGit class is an
 *         abstraction of git for java, so this class will allow manipulation of
 *         different git repos and keeping track of each one of the active
 *         repos.
 * 
 */
public class JGit {

	public static final String REPO_DIR = "Repos";

	private Map<String, JRepository> repos;

	public File storage;

	public JGit() {
		// create File based on session name
	}

	/**
	 * Default Constructor
	 * 
	 * @param storage
	 *            The directory to store the local GIT Repo.
	 * @throws NotDirectoryException
	 *             If the directory doesn't exist, this exception is thrown
	 */
	public JGit(File storage) throws NotDirectoryException {
		if (!storage.isDirectory()) {
			throw new NotDirectoryException();
		}
		this.storage = new File(storage + File.separator + REPO_DIR);
		repos = new ConcurrentHashMap<String, JRepository>();
	}

	/**
	 * This activates the local repository
	 * 
	 * @param name
	 *            The name of the repository to open
	 * @return The repository object of the activated repo
	 * @throws FileNotFoundException
	 *             If the directory or repo doesn't exist, this exception is
	 *             thrown
	 */
	public JRepository activateRepo(String name) throws FileNotFoundException {
		File tempRepo = new File(storage.getAbsolutePath() + File.separatorChar
				+ name);
		if (tempRepo.exists()) {
			try {
				JRepository repoToLoad = new JRepository(tempRepo, name);
				repoToLoad.gitLoad();
				repos.put(repoToLoad.getName(), repoToLoad);
				return repoToLoad;
			} catch (NotDirectoryException e) {
				e.printStackTrace();
			}
		} else {
			throw new FileNotFoundException();
		}
		return null;
	}

	/**
	 * This deactiviates the local repository
	 * 
	 * @param name
	 *            The name of the repository
	 */
	public void deactivateRepo(String name) throws NoHeadException,
			NoMessageException, UnmergedPathsException,
			ConcurrentRefUpdateException, WrongRepositoryStateException,
			GitAPIException {
		if (repos.get(name) != null) {
			repos.get(name).gitCommit("Closing repo");
		}
	}

	/**
	 * This creates a new git repository
	 * 
	 * @param name
	 *            The name of the repository
	 */
	public void createRepo(String name) {
		File repo = new File(storage.getAbsolutePath() + File.separatorChar
				+ name);
		if (!repo.exists()) {
			repo.mkdirs();
		}

		try {
			JRepository repoToLoad = new JRepository(repo, name);
			repoToLoad.gitInit();
			repos.put(name, repoToLoad);
		} catch (NotDirectoryException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This adds a new file to the git repository
	 * 
	 * @param name
	 *            The name of the repository
	 * @param fileToAdd
	 *            The file to add to the repository
	 */
	public void addFileToRepo(String name, File fileToAdd) {
		if (repos.get(name) != null) {
			repos.get(name).gitAdd(fileToAdd);
		}
	}

	/**
	 * This removes a file from the git repository
	 * 
	 * @param name
	 *            The name of the repository
	 * @param fileToRemove
	 *            The file to remove from the repository
	 */
	public void removeFileFromRepo(String name, File fileToRemove) {
		if (repos.get(name) != null) {
			repos.get(name).gitRm(fileToRemove);
		}
	}

	public void commitToRepo(int sessionID, String message)
			throws NoHeadException, NoMessageException, UnmergedPathsException,
			ConcurrentRefUpdateException, WrongRepositoryStateException,
			GitAPIException {
		if (repos.get(sessionID) != null) {
			repos.get(sessionID).gitCommit(message);
		}
	}

	/**
	 * This returns the directory of a repository
	 * 
	 * @param name
	 *            The name of the repository
	 * @return The directory of the returned repository or null if repository
	 *         doesn't exist
	 */
	public File retrieveRepo(String name) {
		if (repos.get(name) != null) {
			return repos.get(name).getDirectory();
		} else {
			return null;
		}
	}
	
	/**
	 * This deletes the directory of a repository
	 * @param name		The name of the repository
	 * @return			True if successful, false otherwise
	 */
	public boolean deleteRepo(String name)
	{
		if (repos.get(name) != null)
		{
			return repos.get(name).deleteDirectory();
		}
		else
		{
			return false;
		}
	
	}
	
	/**
	 * This will clear ALL the repositories
	 * @return	True if successfull, false otherwise
	 */
	public boolean clearRepos()
	{

		boolean suc = true;
		for (Map.Entry<String, JRepository> r : repos.entrySet())
		{

			suc = deleteRepo(r.getKey()) && suc;
			
		}
		return suc;
	}
}