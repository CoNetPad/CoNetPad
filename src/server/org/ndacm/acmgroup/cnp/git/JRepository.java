package org.ndacm.acmgroup.cnp.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

/**
 * This is the class that deals with the GIT handling for sessions
 * @author Cesar Ramirez, Josh Tan
 * @version 2.0
 */
public class JRepository {
	public static final String GIT_DIR = ".git";			//The Git director file type
	private String name;									//The name of the repository	
	private File localDirectory;							//The local directory that the repo works on
	private Repository localRepo;							//
	private Git git;


	/**
	 * Default constructor
	 * @param directory					The directory in which the git repo works on.  Should point towards existing
	 * @param name						The unique name of the git repo
	 * @throws NotDirectoryException	If the given directory doesn't exist an exception wil be thrown
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
	 * This loads git into the given directory
	 * @throws IOException		If the local directory doesnt exist, an exception will be thrown.
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
	 * This initilizes the git repo object
	 * @throws IOException		If the local directory doesnt exist, this exception will be thrown.
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
	 * This adds a file to the GIt repository
	 * @param fileToAdd					The file to add to the git repo
	 * @throws IOException				If the file doesn't exist, this exception gets thrown.
	 * @throws NoFilepatternException	If there is an error with the file, this exception will be thrown.
	 */
	public void gitAdd(File fileToAdd) {
		try {
			git.add().addFilepattern(fileToAdd.getAbsolutePath()).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	/**	This removes a file from the git repo
	 * @param fileToRemove					The file to remove
	 * @throws IOException					If the file or directory doesnt exist, this exception is thrown
	 * @throws NoFilepatternException		If there is an error with the file, this exception will be thrown.
	 */
	public void gitRm(File fileToRemove) {
		try {
			git.rm().addFilepattern(fileToRemove.getAbsolutePath()).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	/**	This commits all the files to the repo
	 * @param message		The message for the commit.
	 * @throws GitAPIException 
	 * @throws WrongRepositoryStateException 
	 * @throws ConcurrentRefUpdateException 
	 * @throws UnmergedPathsException 
	 * @throws NoMessageException 
	 * @throws NoHeadException 
	 */
	public void gitCommit(String message) throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, GitAPIException {

		git.commit().setMessage("Added myfile").call();

	}

	/**	This pushes the changes to the git repo
	 * @throws IOException					If the directory of the git repo doesn't exist, this exception is thrown
	 * @throws JGitInternalException		If there is an error with pushing the changes, this exception is thrwn
	 * @throws InvalidRemoteException		If the the remote git repo doesn't exist or is down, this exception is thrown.
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
	 * This gets information about the master branch of the git repo
	 * @throws IOException					If the directory of the repo doesnt exist, this exception is thrown
	 * @throws JGitInternalException		If there is an error with the git repo, this exception is thrown
	 * @throws RefAlreadyExistsException	If the master branch already exist, this exception is thrown?
	 * @throws RefNotFoundException			If the remote git repo doesn't exist, this exception is thrown
	 * @throws InvalidRefNameException		If the name (i.e. Master) doesn't exist, this exception is thrown
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
	 * This pulls the latest version of the git repo from remote
	 * @throws IOException							If the directory of the local git doesnt exist, this exception is thrown
	 * @throws WrongRepositoryStateException		If the local branch is in a bad state, this exception is thrown
	 * @throws InvalidConfigurationException		If the local branch has a bad configuration, this exception is thrown
	 * @throws DetachedHeadException				If the master head is detached, this exception is thrown
	 * @throws InvalidRemoteException				IF the remote repo doesnt exist or is down this exception is thrown
	 * @throws CanceledException					If the pull is canceled by remote, this exception is thrown
	 * @throws RefNotFoundException					If the reference of the remote git repo not found, this exception is thrown
	 * @throws NoHeadException						If there is no head branch, this exceptin is thrown
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
	 * This determines if the local directory contains a git Repo
	 * @return		True if it does, false otherwise
	 */
	public boolean containsRepo() {
		File gitDir = new File(localDirectory.getAbsolutePath()
				+ File.separatorChar + GIT_DIR);
		return gitDir.exists();
	}

	/**
	 * This gets the name of the git repo
	 * @return		The name of the repo
	 */
	public String getName() {
		return name;
	}

	/**
	 * This gets the directory of the local git repo
	 * @return		The directory or path to the local git repo
	 */
	public File getDirectory() {
		return localDirectory;
	}

	/**
	 * This gets the directory of the local git repo
	 * @return		The directory or path to the local git repo
	 */
	public File[] getDirectoryFiles() {
		return localDirectory.listFiles();
	}


}
