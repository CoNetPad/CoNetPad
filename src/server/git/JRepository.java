package server.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

public class JRepository {
	private static final String GIT_DIR = ".git";
	private String name;
	private File localDirectory;
	private Repository localRepo;
	private Git git;

	/**
	 * @param directory
	 *            this file should point to an existing directory. The folder
	 *            can have or not a git repo already.
	 * @throws NotDirectoryException
	 *             if the file specified does not lead to a directory then an
	 *             exception will be fired. The reference to the file will not
	 *             be kept in the JRepository.
	 */

	public JRepository(File directory, String name)
			throws NotDirectoryException {
		if (!directory.isDirectory()) {
			throw new NotDirectoryException();
		}
		this.name = name;
		this.localDirectory = directory;
	}

	/**
	 * @throws IOException
	 */
	public boolean gitLoad() {
		try {
			localRepo = new FileRepository(localDirectory.getAbsolutePath()
					+ File.separatorChar + GIT_DIR);
			git = new Git(localRepo);
		} catch (IOException e) {
			e.printStackTrace();
			return false;

		}
		return true;

	}

	/**
	 * @throws IOException
	 */
	public boolean gitInit() {
		Repository newRepo;
		try {
			newRepo = new FileRepository(localDirectory.getAbsolutePath()
					+ File.separatorChar + GIT_DIR);
			newRepo.create();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param fileToAdd
	 * @throws IOException
	 * @throws NoFilepatternException
	 */
	public void gitAdd(File fileToAdd) {
		try {
			git.add().addFilepattern(fileToAdd.getAbsolutePath()).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param fileToRemove
	 * @throws IOException
	 * @throws NoFilepatternException
	 */
	public void gitRm(File fileToRemove) {
		try {
			git.rm().addFilepattern(fileToRemove.getAbsolutePath()).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param message
	 */
	public void gitCommit(String message) {
		try {
			git.commit().setMessage("Added myfile").call();
		} catch (UnmergedPathsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException
	 * @throws JGitInternalException
	 * @throws InvalidRemoteException
	 */
	public void gitPush() {
		try {
			git.push().call();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException
	 * @throws JGitInternalException
	 * @throws RefAlreadyExistsException
	 * @throws RefNotFoundException
	 * @throws InvalidRefNameException
	 */
	public void gitTrackMaster() {
		try {
			git.branchCreate().setName("master")
					.setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
					.setStartPoint("origin/master").setForce(true).call();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException
	 * @throws WrongRepositoryStateException
	 * @throws InvalidConfigurationException
	 * @throws DetachedHeadException
	 * @throws InvalidRemoteException
	 * @throws CanceledException
	 * @throws RefNotFoundException
	 * @throws NoHeadException
	 */
	public void gitPull() {
		try {
			git.pull().call();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public boolean containsRepo() {
		File gitDir = new File(localDirectory.getAbsolutePath()
				+ File.separatorChar + GIT_DIR);
		return gitDir.exists();
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public File getDirectory() {
		return localDirectory;
	}

}
