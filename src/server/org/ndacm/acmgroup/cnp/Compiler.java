package org.ndacm.acmgroup.cnp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ndacm.acmgroup.cnp.file.ServerSourceFile;

public class Compiler {
	
	private static final String JAVAC_PATH = "";
	private static final String GCC_PATH = "";
	
	private static Compiler compiler; // singleton instance
	
	
	public List<File> compile(List<ServerSourceFile> sourceFiles) {
		// TODO implement
		return new ArrayList<File>();
	}

	
	private void compileJava(ServerSourceFile sf)
	{
		
	}
}
