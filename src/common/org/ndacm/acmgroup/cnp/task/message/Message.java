package org.ndacm.acmgroup.cnp.task.message;

import org.ndacm.acmgroup.cnp.task.message.MessageFactory.ID;

public class Message {
	private ID taskType;

	private String[] data;
	
	public Message(ID tasktype, String[] data ){
		this.taskType = tasktype;
		this.data = data;
	}
	
	public String getMessageString(){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(taskType);
		for(String element : data){
			buffer.append(element);
		}
		
		return buffer.toString();
	}
	
	public ID getTaskType() {
		return taskType;
	}

	public String[] getData() {
		return data;
	}
}
