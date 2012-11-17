package org.ndacm.acmgroup.cnp.task.message;

import org.ndacm.acmgroup.cnp.task.ChatTask;
import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory.TaskType;

public class TaskMessage {
	private TaskType taskType;

	private String[] data;

	public TaskMessage(TaskType tasktype, String[] data) {
		this.taskType = tasktype;
		this.data = data;
	}

	public TaskMessage(String input) {
		String[] elements = input.split(";");

		int tasktypeId = Integer.parseInt(elements[0]);
		TaskType taskType = TaskType.values()[tasktypeId];

		String[] data = new String[elements.length - 1];
		for (int i = 0; i < data.length; i++) {
			data[i] = elements[1 + i];
		}

		this.taskType = taskType;
		this.data = data;
	}

	public String getMessageString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(taskType.ordinal() + ";");
		for (String element : data) {
			buffer.append(element + ";");
		}

		return buffer.toString();
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public String[] getData() {
		return data;
	}
}
