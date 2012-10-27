package org.ndacm.acmgroup.cnp.git;

import org.ndacm.acmgroup.cnp.file.ServerSourceFile;

public interface IGitRepo {

	boolean addFile(ServerSourceFile file);
	
	boolean removeFile(ServerSourceFile file);
	
	boolean commit(String message);
	
	boolean push();
}
