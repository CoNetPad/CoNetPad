package org.ndacm.acmgroup.cnp.task.message;

import org.ndacm.acmgroup.cnp.task.message.MessageFactory.TaskType;

public class Message {
	private TaskType type;

	private String[] data;
	
	public Message(TaskType type, String[] data ){
		this.type = type;
		this.data = data;
	}
	
	public String getMessageString(){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(type);
		for(String element : data){
			buffer.append(element);
		}
		
		return buffer.toString();
	}
	
	public TaskType getTaskType() {
		return type;
	}

	public String[] getData() {
		return data;
	}
}
