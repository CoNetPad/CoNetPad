package org.ndacm.acmgroup.cnp.task.message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ndacm.acmgroup.cnp.CNPServer;
import org.ndacm.acmgroup.cnp.file.SourceFile.SourceType;
import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.CloseFileTask;
import org.ndacm.acmgroup.cnp.task.CommitTask;
import org.ndacm.acmgroup.cnp.task.CompileTask;
import org.ndacm.acmgroup.cnp.task.CreateAccountTask;
import org.ndacm.acmgroup.cnp.task.CreateFileTask;
import org.ndacm.acmgroup.cnp.task.CreatePrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.CreateSessionTask;
import org.ndacm.acmgroup.cnp.task.DeleteFileTask;
import org.ndacm.acmgroup.cnp.task.DeleteSessionTask;
import org.ndacm.acmgroup.cnp.task.DisconnectTask;
import org.ndacm.acmgroup.cnp.task.DownloadRepoTask;
import org.ndacm.acmgroup.cnp.task.EditorTask;
import org.ndacm.acmgroup.cnp.task.JoinPrivateSessionTask;
import org.ndacm.acmgroup.cnp.task.JoinSessionTask;
import org.ndacm.acmgroup.cnp.task.LoginTask;
import org.ndacm.acmgroup.cnp.task.OpenFileTask;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.response.ChatTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CloseFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CommitTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CompileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateAccountTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.CreateSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.DeleteFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.DeleteSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.DisconnectTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.DownloadRepoTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.JoinSessionTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.LoginTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.OpenFileTaskResponse;
import org.ndacm.acmgroup.cnp.task.response.TaskResponse;

/**
 * @author Cesar Factory that will do the work of translating between
 *         Task(Response) and Messages. The advantage of this class is that if
 *         will handle all the different kinds of tasks or messages to be
 *         translated.
 */
public class TaskMessageFactory {

	public enum TaskType {
		Chat, CloseFile, Commit, Compile, CreateAccount, CreateFile, CreatePrivateSession, CreateSessionTask, DeleteSession, DeleteFile, Disconnect, DownloadRepo, Editor, JoinPrivateSession, JoinSession, Login, OpenFile, EditorResponse
	};

	private static CNPServer server;

	public static void initalizeMessageFactory(CNPServer server) {
		TaskMessageFactory.server = server;
	}

	/**
	 * @param message
	 *            to be converted into a Task
	 * @return a Task based on the information received in the message
	 */
	public static Task fromMessageToTask(TaskMessage message) {
		switch (message.getTaskType()) {
		case Chat:
			return new ChatTask(
					Integer.parseInt(message.getData()[0]),
					message.getData()[1],
					Integer.parseInt(message.getData()[2]),
					message.getData()[3],
					message.getData()[4]);
		case CloseFile:
			return new CloseFileTask(Integer.parseInt(message.getData()[0]),
					message.getData()[1], message.getData()[2]);
		case Commit:
			return new CommitTask(Integer.parseInt(message.getData()[0]),
					message.getData()[1]);
		case Compile:
			List<String> sourceFilenames = new ArrayList<String>(
					message.getData().length - 2);
			for (int i = 0; i < message.getData().length - 2; i++) {
				sourceFilenames.add(message.getData()[1 + i]);
			}
			return new CompileTask(Integer.parseInt(message.getData()[0]),
					sourceFilenames, message.getData()[2]);
		case CreateAccount:
			return new CreateAccountTask(message.getData()[0],
					message.getData()[1], message.getData()[2]);
		case CreateFile:
			CreateFileTask create = null;
			int type = Integer.parseInt(message.getData()[2]);
			switch (type) {
			case 0:
				create = new CreateFileTask(
						Integer.parseInt(message.getData()[0]),
						message.getData()[1], SourceType.JAVA,
						message.getData()[2]);
				break;
			case 1:
				create = new CreateFileTask(
						Integer.parseInt(message.getData()[0]),
						message.getData()[1], SourceType.CPP,
						message.getData()[2]);
				break;
			case 2:
				create = new CreateFileTask(
						Integer.parseInt(message.getData()[0]),
						message.getData()[1], SourceType.GENERAL,
						message.getData()[2]);
				break;
			default:
				break;
			}

			return create;
		case CreatePrivateSession:
			return new CreatePrivateSessionTask(Integer.parseInt(message
					.getData()[0]), message.getData()[1], message.getData()[2]);
		case CreateSessionTask:
			return new CreateSessionTask(
					Integer.parseInt(message.getData()[0]),
					message.getData()[1]);
		case DeleteFile:
			return new DeleteFileTask(Integer.parseInt(message.getData()[0]),
					message.getData()[1], message.getData()[2]);
		case DeleteSession:
			return new DeleteSessionTask(
					Integer.parseInt(message.getData()[0]),
					Integer.parseInt(message.getData()[1]),
					message.getData()[2]);
		case Disconnect:
			return new DisconnectTask(Integer.parseInt(message.getData()[0]),
					message.getData()[1]);
		case DownloadRepo:
			return new DownloadRepoTask(Integer.parseInt(message.getData()[0]),
					message.getData()[1]);
		case Editor:
			return new EditorTask(
					Integer.parseInt(message.getData()[0]),
					Integer.parseInt(message.getData()[1]),
					Integer.parseInt(message.getData()[2]),
					Integer.parseInt(message.getData()[3]),
					Integer.parseInt(message.getData()[4]),
					message.getData()[5]);
		case JoinPrivateSession:
			return new JoinPrivateSessionTask(
					Integer.parseInt(message.getData()[0]),
					message.getData()[1], 
					message.getData()[2],
					message.getData()[3],
					message.getData()[4]);
		case JoinSession:
			return new JoinSessionTask(
					Integer.parseInt(message.getData()[0]),
					message.getData()[1],
					message.getData()[2],
					message.getData()[3]);

		case Login:
			return new LoginTask(message.getData()[0], message.getData()[1]);
		case OpenFile:
			return new OpenFileTask(Integer.parseInt(message.getData()[0]),
					Integer.parseInt(message.getData()[1]),
					message.getData()[2]);
		default:
			return null;
		}
	}

	/**
	 * @param task
	 *            to be translated into a message
	 * @return message containing the string re[presentation of the task
	 */
	public static TaskMessage fromTaskToMessage(Task task) {

		TaskMessage message = null;

		if (task instanceof ChatTask) {
			ChatTask chat = (ChatTask) task;
			String[] data = { Integer.toString(chat.getUserID()),
					chat.getUsername(),
					Integer.toString(chat.getSessionID()),
					chat.getMessage(), chat.getUserAuthToken() };
			message = new TaskMessage(TaskType.Chat, data);
		} else if (task instanceof CloseFileTask) {
			CloseFileTask close = (CloseFileTask) task;
			String[] data = { Integer.toString(close.getUserID()),
					close.getFilename(), close.getUserAuthToken() };
			message = new TaskMessage(TaskType.CloseFile, data);
		} else if (task instanceof CommitTask) {
			CommitTask commit = (CommitTask) task;
			String[] data = { Integer.toString(commit.getUserID()),
					commit.getUserAuthToken() };
			message = new TaskMessage(TaskType.Commit, data);
		} else if (task instanceof CompileTask) {
			CompileTask compile = (CompileTask) task;
			int listSize = compile.getSourceFilenames().size();
			String[] data = new String[listSize + 2];
			data[0] = Integer.toString(compile.getUserID());
			for (int i = 0; i < listSize; i++) {
				data[i + 1] = compile.getSourceFilenames().get(i);
			}
			data[data.length - 2] = compile.getUserAuthToken();
			message = new TaskMessage(TaskType.Compile, data);
		} else if (task instanceof CreateAccountTask) {
			CreateAccountTask create = (CreateAccountTask) task;
			String[] data = { create.getUsername(), create.getEmail(),
					create.getPassword() };
			message = new TaskMessage(TaskType.CreateAccount, data);
		} else if (task instanceof CreateFileTask) {
			CreateFileTask createFile = (CreateFileTask) task;
			String[] data = { Integer.toString(createFile.getUserID()),
					createFile.getFilename(), null,
					createFile.getUserAuthToken() };

			switch (createFile.getType()) {
			case JAVA:
				data[2] = "0";
				break;
			case CPP:
				data[2] = "1";
				break;
			case GENERAL:
				data[2] = "2";
				break;
			default:
				break;
			}

			message = new TaskMessage(TaskType.CreateFile, data);
		} else if (task instanceof CreatePrivateSessionTask) {
			CreatePrivateSessionTask createPrivate = (CreatePrivateSessionTask) task;
			String[] data = {
					Integer.toString(createPrivate.getSessionLeader()),
					createPrivate.getSessionPassword(),
					createPrivate.getUserAuthToken() };
			message = new TaskMessage(TaskType.CreatePrivateSession, data);
		} else if (task instanceof CreateSessionTask) {
			CreateSessionTask createSession = (CreateSessionTask) task;
			String[] data = {
					Integer.toString(createSession.getSessionLeader()),
					createSession.getUserAuthToken() };
			message = new TaskMessage(TaskType.CreateSessionTask, data);
		} else if (task instanceof DeleteFileTask) {
			DeleteFileTask deleteFile = (DeleteFileTask) task;
			String[] data = { Integer.toString(deleteFile.getUserID()),
					deleteFile.getFilename(), deleteFile.getUserAuthToken() };
			message = new TaskMessage(TaskType.DeleteFile, data);
		} else if (task instanceof DeleteSessionTask) {
			DeleteSessionTask deleteSession = (DeleteSessionTask) task;
			String[] data = { Integer.toString(deleteSession.getUserID()),
					Integer.toString(deleteSession.getSessionID()),
					deleteSession.getUserAuthToken() };
			message = new TaskMessage(TaskType.DeleteSession, data);
		} else if (task instanceof DisconnectTask) {
			DisconnectTask disconnect = (DisconnectTask) task;
			String[] data = { Integer.toString(disconnect.getUserID()),
					disconnect.getUserAuthToken() };
			message = new TaskMessage(TaskType.Disconnect, data);
		} else if (task instanceof DownloadRepoTask) {
			DownloadRepoTask download = (DownloadRepoTask) task;
			String[] data = { Integer.toString(download.getUserID()),
					download.getUserAuthToken() };
			message = new TaskMessage(TaskType.DownloadRepo, data);
		} else if (task instanceof EditorTask) {
			EditorTask editor = (EditorTask) task;
			String[] data = { Integer.toString(editor.getUserID()),
					Integer.toString(editor.getKeyPressed()),
					Integer.toString(editor.getEditIndex()),
					editor.getFilename(), editor.getUserAuthToken() };
			message = new TaskMessage(TaskType.Editor, data);
		} else if (task instanceof JoinPrivateSessionTask) {
			JoinPrivateSessionTask joinPrivate = (JoinPrivateSessionTask) task;
			String[] data = { Integer.toString(joinPrivate.getUserID()),
					joinPrivate.getUsername(),
					joinPrivate.getSessionName(),
					joinPrivate.getSessionPassword(),
					joinPrivate.getUserAuthToken() };
			message = new TaskMessage(TaskType.JoinPrivateSession, data);
		} else if (task instanceof JoinSessionTask) {
			JoinSessionTask joinSession = (JoinSessionTask) task;
			String[] data = { Integer.toString(joinSession.getUserID()),
					joinSession.getUsername(),
					joinSession.getSessionName(),
					joinSession.getUserAuthToken() };
			message = new TaskMessage(TaskType.JoinSession, data);
		} else if (task instanceof LoginTask) {
			LoginTask login = (LoginTask) task;
			String[] data = { login.getUsername(), login.getPassword() };
			message = new TaskMessage(TaskType.Login, data);
		} else if (task instanceof OpenFileTask) {
			OpenFileTask openFile = (OpenFileTask) task;
			String[] data = { Integer.toString(openFile.getUserID()),
					Integer.toString(openFile.getFileID()),
					openFile.getUserAuthToken() };
			message = new TaskMessage(TaskType.OpenFile, data);
		}

		return message;
	}

	/**
	 * @param message
	 *            to be converted into a TaskResponse
	 * @return a TaskResponse based on the information received in the message
	 */
	public static TaskResponse fromMessageToTaskResponse(TaskMessage message) {

		switch (message.getTaskType()) {
		case Chat:
			return new ChatTaskResponse(message.getData()[0],
					message.getData()[1]);
		case CloseFile:
			return new CloseFileTaskResponse(message.getData()[0],
					Boolean.parseBoolean(message.getData()[1]));
		case Commit:
			return new CommitTaskResponse(Boolean.parseBoolean(message
					.getData()[0]));
		case Compile:
			return new CompileTaskResponse(message.getData()[0],
					Boolean.parseBoolean(message.getData()[1]));
		case CreateAccount:
			return new CreateAccountTaskResponse(Integer.parseInt(message
					.getData()[0]), Boolean.parseBoolean(message.getData()[1]));
		case CreateFile:
			int sourceTypeInt = Integer.parseInt(message.getData()[3]);
			return new CreateFileTaskResponse(
					Integer.parseInt(message.getData()[0]),
					Integer.parseInt(message.getData()[1]),
					message.getData()[2],
					getSourceTypeFromInt(sourceTypeInt),
					Boolean.parseBoolean(message.getData()[4]));
		case CreatePrivateSession:
		case CreateSessionTask:
			return new CreateSessionTaskResponse(Integer.parseInt(message
					.getData()[0]), Boolean.parseBoolean(message.getData()[1]));
		case DeleteFile:
			return new DeleteFileTaskResponse(message.getData()[0],
					Boolean.parseBoolean(message.getData()[1]));
		case DeleteSession:
			return new DeleteSessionTaskResponse(message.getData()[0],
					Boolean.parseBoolean(message.getData()[1]));
		case Disconnect:
			return new DisconnectTaskResponse(Boolean.parseBoolean(message
					.getData()[0]));

		case DownloadRepo:
			File tmp = null;
			return new DownloadRepoTaskResponse(tmp,
					Boolean.parseBoolean(message.getData()[1]));

		case Editor:
			return new EditorTaskResponse(message.getData()[0],
					Integer.parseInt(message.getData()[1]),
					Integer.parseInt(message.getData()[2]),
					Integer.parseInt(message.getData()[3]),
					Boolean.parseBoolean(message.getData()[4]));
		case JoinPrivateSession:
		case JoinSession:
			List<String> fileNames = new ArrayList<String>();
			int numFiles = Integer.parseInt(message.getData()[5]);
			for (int i = 0; i < numFiles; i++) {
				fileNames.add(message.getData()[6 + i]);
			}
			return new JoinSessionTaskResponse(
					Integer.parseInt(message.getData()[0]),
					message.getData()[1],
					message.getData()[2],
					Integer.parseInt(message.getData()[3]),
					Boolean.parseBoolean(message.getData()[4]),
					fileNames);
		case Login:
			return new LoginTaskResponse(
					Integer.parseInt(message.getData()[0]),
					message.getData()[1],
					Boolean.parseBoolean(message.getData()[2]),
					message.getData()[3]);
		case OpenFile:
			return new OpenFileTaskResponse(
					Integer.parseInt(message.getData()[0]),
					message.getData()[1],
					message.getData()[2],
					Boolean.parseBoolean(message.getData()[3]));
		default:
			return null;
		}
	}

	/**
	 * @param taskResponse
	 *            to be translated into a message
	 * @return message containing the string representation of the taskResponse
	 */

	public static TaskMessage fromTaskResponseToMessage(TaskResponse task) {

		TaskMessage message = null;

		if (task instanceof ChatTaskResponse) {
			ChatTaskResponse chat = (ChatTaskResponse) task;
			String[] data = { chat.getUsername(), chat.getMessage() };
			message = new TaskMessage(TaskType.Chat, data);
		} else if (task instanceof CloseFileTaskResponse) {
			CloseFileTaskResponse close = (CloseFileTaskResponse) task;
			String[] data = { close.getFilename(),
					Boolean.toString(close.isSuccess()) };
			message = new TaskMessage(TaskType.CloseFile, data);
		} else if (task instanceof CommitTaskResponse) {
			CommitTaskResponse commit = (CommitTaskResponse) task;
			String[] data = { Boolean.toString(commit.isSuccess()) };
			message = new TaskMessage(TaskType.Commit, data);
		} else if (task instanceof CompileTaskResponse) {
			CompileTaskResponse compile = (CompileTaskResponse) task;
			String[] data = { Boolean.toString(compile.isSuccess()) };
			message = new TaskMessage(TaskType.Compile, data);
		} else if (task instanceof CreateAccountTaskResponse) {
			CreateAccountTaskResponse create = (CreateAccountTaskResponse) task;
			String[] data = { Integer.toString(create.getUserID()),
					Boolean.toString(create.isSuccess()) };
			message = new TaskMessage(TaskType.CreateAccount, data);
		} else if (task instanceof CreateFileTaskResponse) {
			CreateFileTaskResponse createFile = (CreateFileTaskResponse) task;

			String[] data = {
					Integer.toString(createFile.getFileID()),
					Integer.toString(createFile.getUserID()),
					createFile.getFilename(),
					Integer.toString((createFile.getType().ordinal())),
					Boolean.toString(createFile.isSuccess()) };
			message = new TaskMessage(TaskType.CreateFile, data);
		} else if (task instanceof CreateSessionTaskResponse) {
			CreateSessionTaskResponse createSession = (CreateSessionTaskResponse) task;
			String[] data = { Integer.toString(createSession.getSessionID()),
					Boolean.toString(createSession.isSuccess()) };
			message = new TaskMessage(TaskType.CreateSessionTask, data);
		} else if (task instanceof DeleteFileTaskResponse) {
			DeleteFileTaskResponse deleteFile = (DeleteFileTaskResponse) task;
			String[] data = { deleteFile.getFilename(),
					Boolean.toString(deleteFile.isSuccess()) };
			message = new TaskMessage(TaskType.DeleteFile, data);
		} else if (task instanceof DeleteSessionTaskResponse) {
			DeleteSessionTaskResponse deleteSession = (DeleteSessionTaskResponse) task;
			String[] data = { deleteSession.getSessionName(),
					Boolean.toString(deleteSession.isSuccess()) };
			message = new TaskMessage(TaskType.DeleteSession, data);
		} else if (task instanceof DisconnectTaskResponse) {
			DisconnectTaskResponse disconnect = (DisconnectTaskResponse) task;
			String[] data = { Boolean.toString(disconnect.isSuccess()) };
			message = new TaskMessage(TaskType.Disconnect, data);
		} else if (task instanceof DownloadRepoTaskResponse) {
			DownloadRepoTaskResponse download = (DownloadRepoTaskResponse) task;
			String[] data = { download.getEncodedRepo(),
					Boolean.toString(download.isSuccess()) };
			message = new TaskMessage(TaskType.DownloadRepo, data);
		} else if (task instanceof EditorTaskResponse) {
			EditorTaskResponse editor = (EditorTaskResponse) task;
			String[] data = { editor.getUsername(),
					Integer.toString(editor.getKeyPressed()),
					Integer.toString(editor.getEditIndex()),
					Integer.toString(editor.getFileID()),
					Boolean.toString(editor.isSuccess())};
			message = new TaskMessage(TaskType.Editor, data);
		} else if (task instanceof JoinSessionTaskResponse) {
			JoinSessionTaskResponse joinSession = (JoinSessionTaskResponse) task;
			List<String> data = new ArrayList<String>();
			data.add(Integer.toString(joinSession.getUserID()));
			data.add(joinSession.getUsername());
			data.add(joinSession.getSessionName());
			data.add(Integer.toString(joinSession.getSessionID()));
			data.add(Boolean.toString(joinSession.isSuccess()));
			data.add(Integer.toString(joinSession.getSessionFiles().size())); // number of files - needed to parse message
			for (String filename : joinSession.getSessionFiles()) {
				data.add(filename);
			}
			String[] dataArray = (String[]) data.toArray();
			message = new TaskMessage(TaskType.JoinSession, dataArray);
		} else if (task instanceof LoginTaskResponse) {
			LoginTaskResponse login = (LoginTaskResponse) task;
			String[] data = { Integer.toString(login.getUserID()),
					login.getUsername(),
					Boolean.toString(login.isSuccess()),
					login.getUserAuthToken() };
			message = new TaskMessage(TaskType.Login, data);
		} else if (task instanceof OpenFileTaskResponse) {
			OpenFileTaskResponse openFile = (OpenFileTaskResponse) task;
			String[] data = { 
					Integer.toString(openFile.getFileID()),
					openFile.getFilename(),
					openFile.getFileContent(),
					Boolean.toString(openFile.isSuccess()) };
			message = new TaskMessage(TaskType.OpenFile, data);
		}

		return message;
	}

	public static TaskMessage convertTaskToMessage(Task task) {
		return convertTaskToMessage(task);
	}

	public static TaskMessage converTaskToMessage(EditorTask task) {
		String[] data = { Integer.toString(task.getUserID()),
				task.getUsername(), Integer.toString(task.getSessionID()),
				Integer.toString(task.getKeyPressed()),
				Integer.toString(task.getEditIndex()),
				Integer.toString(task.getFile().getFileID()),
				task.getUserAuthToken() };
		return new TaskMessage(TaskType.Editor, data);
	}

	public static TaskMessage convertTaskToMessage(EditorTaskResponse task) {
		String[] data = { task.getUsername(),
				Integer.toString(task.getKeyPressed()),
				Integer.toString(task.getEditIndex()),
				Integer.toString(task.getFileID()) };
		return new TaskMessage(TaskType.EditorResponse, data);
	}

	public static SourceType getSourceTypeFromInt(int typeInt) {
		SourceType type = null;
		switch (typeInt) {

		case 0: type = SourceType.JAVA;
		break;
		case 1: type = SourceType.CPP;
		break;
		case 2: type = SourceType.GENERAL;
		break;

		}
		return type;
	}

}