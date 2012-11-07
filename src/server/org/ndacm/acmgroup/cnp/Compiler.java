package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ndacm.acmgroup.cnp.file.ServerSourceFile;

public class Compiler {
	
	private Compiler compiler; // singleton instance
	private String javaCompilerPath;
	private String cppCompilerPath;
	
	public List<File> compile(List<ServerSourceFile> sourceFiles) {
		// TODO implement
		return new ArrayList<File>();
	}

}
