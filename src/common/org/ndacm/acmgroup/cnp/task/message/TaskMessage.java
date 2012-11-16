package org.ndacm.acmgroup.cnp.task.message;

import org.ndacm.acmgroup.cnp.task.message.TaskMessageFactory.TaskType;

public class TaskMessage {
	private TaskType taskType;

	private String[] data;

	public TaskMessage(TaskType tasktype, String[] data) {
		this.taskType = tasktype;
		this.data = data;
	}

	public String getMessageString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(taskType);
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
