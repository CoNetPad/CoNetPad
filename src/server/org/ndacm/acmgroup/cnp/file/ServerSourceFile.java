package org.ndacm.acmgroup.cnp.file;

import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import org.ndacm.acmgroup.cnp.server.CNPSession;
import org.ndacm.acmgroup.cnp.task.DownloadTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;

public class ServerSourceFile extends SourceFile {

	private CNPSession session;
	private ExecutorService taskQueue;

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
		EditorTaskResponse response = new EditorTaskResponse(keyPressed, editIndex, filename);
		// what does one of these need to have to get distributed efficiently?

	}
}

}
