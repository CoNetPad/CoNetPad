package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.TaskType;
import org.ndacm.acmgroup.cnp.TaskType.CNPTaskType;

import com.google.gson.Gson;

public class TaskBuilder
{

	private static String jsonString;
	private Gson gs;
	public JSONTask t;
	private CNPTaskType taskType;
	
	public TaskBuilder(String json)
	{
		gs = new Gson();
		jsonString = json;
		try
		{
			t = gs.fromJson(json, JSONTask.class);
			taskType = TaskType.taskFromString(t.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public Task getCNPTask()
	{
	
		String username = "username";
		String msg = "message";
		Task ret = null;
		switch(taskType)
		{
			case CHAT:
				ret =  new ChatTask(username, msg);
			break;
			
		}
		return ret;
	
	}
	
	public static void main(String[] args)
	{
		//SOME TEST CODE
		TaskBuilder tb = new TaskBuilder("{\"type\":\"chat\"}");
		Task t = tb.getCNPTask();
		System.out.println(t);
	}
}
