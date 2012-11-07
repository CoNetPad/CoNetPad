package server.org.ndacm.acmgroup.cnp.file;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;



import server.org.ndacm.acmgroup.cnp.server.CNPSession;

import common.org.ndacm.acmgroup.cnp.file.SourceFile;
import common.org.ndacm.acmgroup.cnp.task.DownloadTask;
import common.org.ndacm.acmgroup.cnp.task.EditorTask;

public class ServerSourceFile extends SourceFile {

	private CNPSession session;
	private ExecutorService taskQueue;
	
	public ServerSourceFile(String filename, SourceType type, String initialText) {
		super(filename, type, initialText);
		// TODO Auto-generated constructor stub
	}

	public void addTask(EditorTask task) {
		taskQueue.submit(task);
	}

	public boolean addTask(DownloadTask task) {
		// TODO implement
		return false;
	}

	@Override
	public void editSource(EditorTask task) {


		int keyPressed = task.getKeyPressed();
		int editIndex = task.getEditIndex();

		if (keyPressed == KeyEvent.VK_BACK_SPACE) {
			sourceRope = sourceRope.delete(editIndex, editIndex + 1);
		} else {
			sourceRope = sourceRope.insert(editIndex, Character.toString((char) keyPressed));
		}

		// create EditorTaskResponse and send it to users in session
		TaskResponse response = new EditorTaskResponse(keyPressed, editIndex, filename, session);
		session.distributeTask(response);

	}


}
