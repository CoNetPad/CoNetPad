package org.ndacm.acmgroup.cnp.task;

import java.io.File;
import java.util.concurrent.Callable;

public abstract class DownloadTask extends Task implements Callable<File> {

	
	public enum FileType {
		BINARY,
		SOURCE
	}

	@Override
	public abstract File call();

}