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

	public JGit(File storage) throws NotDirectoryException {
		if (!storage.isDirectory()) {
			throw new NotDirectoryException();
		}
		this.storage = storage;
		repos = new Hashtable<String, JRepository>();
	}

	public void activateRepo(String name) throws FileNotFoundException {
		File tempRepo = new File(storage.getAbsolutePath() + File.separatorChar
				+ name);
		if (tempRepo.exists()) {
			try {
				JRepository repoToLoad = new JRepository(tempRepo, name);
				repoToLoad.gitLoad();
				repos.put(repoToLoad.getName(), repoToLoad);
			} catch (NotDirectoryException e) {
				e.printStackTrace();
			}
		} else {
			throw new FileNotFoundException();
		}
	}

	public void deactivateRepo(String name) {
		if (repos.get(name) != null) {
			repos.get(name).gitCommit("Closing repo");
		}
	}

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

	public void addFileToRepo(String name, File fileToAdd) {
		if (repos.get(name) != null) {
			repos.get(name).gitAdd(fileToAdd);
		}
	}

	public void removeFileFromRepo(String name, File fileToRemove) {
		if (repos.get(name) != null) {
			repos.get(name).gitRm(fileToRemove);
		}
	}

	public void commitToRepo(String name, String message) {
		if (repos.get(name) != null) {
			repos.get(name).gitCommit(message);
		}
	}

	public File retrieveRepo(String name) {
		if (repos.get(name) != null) {
			return repos.get(name).getDirectory();
		} else {
			return null;
		}
	}
}