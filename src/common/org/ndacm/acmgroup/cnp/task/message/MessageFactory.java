package org.ndacm.acmgroup.cnp.task.message;

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
import org.ndacm.acmgroup.cnp.task.response.EditorTaskResponse;

public class MessageFactory {
	
	private static CNPServer server;
	
	public static void initalizeMessageFactory(CNPServer server) {
		MessageFactory.server = server;
	}
	
	public enum TaskType {
		Chat, CloseFile, Commit, Compile, CreateAccount, CreateFile, CreatePrivateSession, CreateSessionTask, 
		DeleteSession, DeleteFile, Disconnect, DownloadRepo, Editor, JoinPrivateSession, JoinSession, Login, 
		OpenFile, EditorResponse
	};

	public static Task convertMessageToTask(Message message) {
		switch (message.getTaskType()) {
		case Chat:
			return new ChatTask(Integer.parseInt(message.getData()[0]),
					message.getData()[1], message.getData()[2]);
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
			int sessionID = Integer.parseInt(message.getData()[2]);
			int fileID = Integer.parseInt(message.getData()[5]);
			return new EditorTask(Integer.parseInt(message.getData()[0]),
					message.getData()[1],
					sessionID,
					Integer.parseInt(message.getData()[3]),
					Integer.parseInt(message.getData()[4]),
					server.getSession(sessionID).getFile(fileID),
					message.getData()[6]);
		case JoinPrivateSession:
			return new JoinPrivateSessionTask(Integer.parseInt(message
					.getData()[0]), message.getData()[1], message.getData()[2],
					message.getData()[3]);
		case JoinSession:
			return new JoinSessionTask(Integer.parseInt(message.getData()[0]),
					message.getData()[1], message.getData()[2]);
		case Login:
			return new LoginTask(message.getData()[0], message.getData()[1]);
		case OpenFile:
			return new OpenFileTask(Integer.parseInt(message.getData()[0]),
					Integer.parseInt(message.getData()[1]), message.getData()[2]);
		case EditorResponse:
			return new EditorTaskResponse(message.getData()[0],
					Integer.parseInt(message.getData()[1]),
					Integer.parseInt(message.getData()[2]),
					Integer.parseInt(message.getData()[3]));
			
		default:
			return null;
		}
	}

	
	public static Message convertTaskToMessage(Task task) {
		return convertTaskToMessage(task);
	}
	
	public static Message converTaskToMessage(EditorTask task) {
		String[] data = { Integer.toString(task.getUserID()),
				task.getUsername(),
				Integer.toString(task.getSessionID()),
				Integer.toString(task.getKeyPressed()),
				Integer.toString(task.getEditIndex()),
				Integer.toString(task.getFile().getFileID()),
				task.getUserAuthToken() };
		return new Message(TaskType.Editor, data);
	}

	public static Message convertTaskToMessage(EditorTaskResponse task) {
		String[] data = { task.getUsername(),
				Integer.toString(task.getKeyPressed()),
				Integer.toString(task.getEditIndex()),
				Integer.toString(task.getFileID())};
		return new Message(TaskType.EditorResponse, data);
	}
	
	// repeat ^ for other types

	public static Message fromTaskToMessage(Task task) {

		Message message = null;

		if (task instanceof ChatTask) {
			ChatTask chat = (ChatTask) task;
			String[] data = { Integer.toString(chat.getUserID()),
					chat.getMessage(), chat.getUserAuthToken() };
			message = new Message(TaskType.Chat, data);
		} else if (task instanceof CloseFileTask) {
			CloseFileTask close = (CloseFileTask) task;
			String[] data = { Integer.toString(close.getUserID()),
					close.getFilename(), close.getUserAuthToken() };
			message = new Message(TaskType.CloseFile, data);
		} else if (task instanceof CommitTask) {
			CommitTask commit = (CommitTask) task;
			String[] data = { Integer.toString(commit.getUserID()),
					commit.getUserAuthToken() };
			message = new Message(TaskType.Commit, data);
		} else if (task instanceof CompileTask) {
			CompileTask compile = (CompileTask) task;
			int listSize = compile.getSourceFilenames().size();
			String[] data = new String[listSize + 2];
			data[0] = Integer.toString(compile.getUserID());
			for (int i = 0; i < listSize; i++) {
				data[i + 1] = compile.getSourceFilenames().get(i);
			}
			data[data.length - 2] = compile.getUserAuthToken();
			message = new Message(TaskType.Compile, data);
		} else if (task instanceof CreateAccountTask) {
			CreateAccountTask create = (CreateAccountTask) task;
			String[] data = { create.getUsername(), create.getEmail(),
					create.getPassword() };
			message = new Message(TaskType.CreateAccount, data);
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

			message = new Message(TaskType.CreateFile, data);
		} else if (task instanceof CreatePrivateSessionTask) {
			CreatePrivateSessionTask createPrivate = (CreatePrivateSessionTask) task;
			String[] data = {
					Integer.toString(createPrivate.getSessionLeader()),
					createPrivate.getSessionPassword(),
					createPrivate.getUserAuthToken() };
			message = new Message(TaskType.CreatePrivateSession, data);
		} else if (task instanceof CreateSessionTask) {
			CreateSessionTask createSession = (CreateSessionTask) task;
			String[] data = {
					Integer.toString(createSession.getSessionLeader()),
					createSession.getUserAuthToken() };
			message = new Message(TaskType.CreateSessionTask, data);
		} else if (task instanceof DeleteFileTask) {
			DeleteFileTask deleteFile = (DeleteFileTask) task;
			String[] data = { Integer.toString(deleteFile.getUserID()),
					deleteFile.getFilename(), deleteFile.getUserAuthToken() };
			message = new Message(TaskType.DeleteFile, data);
		} else if (task instanceof DeleteSessionTask) {
			DeleteSessionTask deleteSession = (DeleteSessionTask) task;
			String[] data = { Integer.toString(deleteSession.getUserID()),
					Integer.toString(deleteSession.getSessionID()),
					deleteSession.getUserAuthToken() };
			message = new Message(TaskType.DeleteSession, data);
		} else if (task instanceof DisconnectTask) {
			DisconnectTask disconnect = (DisconnectTask) task;
			String[] data = { Integer.toString(disconnect.getUserID()),
					disconnect.getUserAuthToken() };
			message = new Message(TaskType.Disconnect, data);
		} else if (task instanceof DownloadRepoTask) {
			DownloadRepoTask download = (DownloadRepoTask) task;
			String[] data = { Integer.toString(download.getUserID()),
					download.getUserAuthToken() };
			message = new Message(TaskType.DownloadRepo, data);
//		} else if (task instanceof EditorTask) {
//		
//			EditorTask editor = (EditorTask) task;
//			String[] data = { Integer.toString(editor.getUserID()),
//					editor.getUsername(),
//					Integer.toString(editor.getSessionID()),
//					Integer.toString(editor.getKeyPressed()),
//					Integer.toString(editor.getEditIndex()),
//					Integer.toString(editor.getFile().getFileID()),
//					editor.getUserAuthToken() };
//			message = new Message(TaskType.Editor, data);
		} else if (task instanceof JoinPrivateSessionTask) {
			JoinPrivateSessionTask joinPrivate = (JoinPrivateSessionTask) task;
			String[] data = { Integer.toString(joinPrivate.getUserID()),
					joinPrivate.getSessionName(),
					joinPrivate.getSessionPassword(),
					joinPrivate.getUserAuthToken() };
			message = new Message(TaskType.JoinPrivateSession, data);
		} else if (task instanceof JoinSessionTask) {
			JoinSessionTask joinSession = (JoinSessionTask) task;
			String[] data = { Integer.toString(joinSession.getUserID()),
					joinSession.getSessionName(),
					joinSession.getUserAuthToken() };
			message = new Message(TaskType.JoinSession, data);
		} else if (task instanceof LoginTask) {
			LoginTask login = (LoginTask) task;
			String[] data = { login.getUsername(), login.getPassword() };
			message = new Message(TaskType.Login, data);
		} else if (task instanceof OpenFileTask) {
			OpenFileTask openFile = (OpenFileTask) task;
			String[] data = { Integer.toString(openFile.getUserID()),
					Integer.toString(openFile.getFileID()), openFile.getUserAuthToken() };
			message = new Message(TaskType.OpenFile, data);
		}

		return message;
	}
}
