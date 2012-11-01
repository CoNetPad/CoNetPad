package server.git;

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
 * @author cesar
 * 
 *         This class will interface with the git repos. The JGit class is an
 *         abstraction of git for java, so this class will allow manipulation of
 *         different git repos and keeping track of each one of the active
 *         repos.
 * 
 */
public class JGit {

	private String localPath, remotePath;
	private Repository localRepo;
	private Git git;

	public void init() throws IOException {
		localPath = "/home/me/repos/my";
		remotePath = "git@github.com:me/myrepo.git";
		localRepo = new FileRepository(localPath + "/.git");
		git = new Git(localRepo);
	}

	public void Create() throws IOException {
		Repository newRepo = new FileRepository(localPath + ".git");
		newRepo.create();
	}

	public void Clone() throws IOException, NoFilepatternException {
		try {
			Git.cloneRepository().setURI(remotePath)
					.setDirectory(new File(localPath)).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	public void Add() throws IOException, NoFilepatternException {
		File myfile = new File(localPath + "/myfile");
		myfile.createNewFile();
		try {
			git.add().addFilepattern("myfile").call();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Commit() throws IOException, NoHeadException,
			NoMessageException, ConcurrentRefUpdateException,
			JGitInternalException, WrongRepositoryStateException {
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

	public void Push() throws IOException, JGitInternalException,
			InvalidRemoteException {
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

	public void TrackMaster() throws IOException, JGitInternalException,
			RefAlreadyExistsException, RefNotFoundException,
			InvalidRefNameException {
		try {
			git.branchCreate().setName("master")
					.setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
					.setStartPoint("origin/master").setForce(true).call();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Pull() throws IOException, WrongRepositoryStateException,
			InvalidConfigurationException, DetachedHeadException,
			InvalidRemoteException, CanceledException, RefNotFoundException,
			NoHeadException {
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
}