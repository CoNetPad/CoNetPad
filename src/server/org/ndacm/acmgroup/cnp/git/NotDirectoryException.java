package org.ndacm.acmgroup.cnp.git;

/**
 * JRepository needs a directory to initialize a repo. This exception gets
 * thrown if you pass a file instead of a directory.
 * @author Cesar Ramirez
 */
public class NotDirectoryException extends Exception {


	private static final long serialVersionUID = 6224103704369199452L;

}
