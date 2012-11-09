package org.ndacm.acmgroup.cnp.git;

public class NotDirectoryException extends Exception {

	/**
	 * JRepository needs a directory to initialize a repo. This exception gets
	 * thrown if you pass a file instead of a directory.
	 */
	private static final long serialVersionUID = 6224103704369199452L;

}
