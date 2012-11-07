package org.ndacm.acmgroup.cnp.git;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.api.Git;

import org.ndacm.acmgroup.cnp.file.ServerSourceFile;

public class GitRepo {
	
	private Repository repo;
	private Git git;
	private String localRepoPath;
	
	public GitRepo() {
		// TODO implement
	}
	
	public boolean addFile(ServerSourceFile file) {
		// TODO implement
		return false;
	}
	
	public boolean removeFile(ServerSourceFile file) {
		// TODO implement
		return false;
	}
	
	public boolean commit(String message) {
		// TODO implement
		return false;
	}
	
	public boolean push() {
		// TODO implement
		return false;
	}
	
	// to clone, have server tar up localRepoPath or something; GitRepo shouldn't
	// worry about it, since it's not really a true Git clone

	
}
