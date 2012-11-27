package org.ndacm.acmgroup.cnp.git;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

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

	public static final String TEMP_REPO_DIR = "./Repos";

	private Hashtable<String, JRepository> repos;

	public File storage;

	public JGit() {
		// create File based on session name
	}
	/**
	 * Default Constructor
	 * @param storage					The directory to store the local GIT Repo.
	 * @throws NotDirectoryException	If the directory doesn't exist, this exception is thrown
	 */
	public JGit(File storage) throws NotDirectoryException {
		if (!storage.isDirectory()) {
			throw new NotDirectoryException();
		}
		this.storage = storage;
		repos = new Hashtable<String, JRepository>();
	}
	
	/**
	 * This activates the local repository
	 * @param name						The name of the repository to open
	 * @return							The repository object of the activated repo
	 * @throws FileNotFoundException	If the directory or repo doesn't exist, this exception is thrown
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
	 * @param name			The name of the repository
	 */
	public void deactivateRepo(String name) {
		if (repos.get(name) != null) {
			repos.get(name).gitCommit("Closing repo");
		}
	}
	/**
	 * This creates a new git repository
	 * @param name		The name of the repository
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
	 * @param name			The name of the repository
	 * @param fileToAdd		The file to add to the repository
	 */
	public void addFileToRepo(String name, File fileToAdd) {
		if (repos.get(name) != null) {
			repos.get(name).gitAdd(fileToAdd);
		}
	}
	
	/**
	 * This removes a file from the git repository
	 * @param name				The name of the repository
	 * @param fileToRemove		The file to remove from the repository
	 */
	public void removeFileFromRepo(String name, File fileToRemove) {
		if (repos.get(name) != null) {
			repos.get(name).gitRm(fileToRemove);
		}
	}

	/**
	 * Commit changes to the git repository
	 * @param name			The name of the repository
	 * @param message		The message for the commit
	 */
	public void commitToRepo(String name, String message) {
		if (repos.get(name) != null) {
			repos.get(name).gitCommit(message);
		}
	}

	/**
	 * This returns the directory of a repository
	 * @param name		The name of the repository
	 * @return			The directory of the returned repository or null if repository doesn't exist
	 */
	public File retrieveRepo(String name) {
		if (repos.get(name) != null) {
			return repos.get(name).getDirectory();
		} else {
			return null;
		}
	}
}