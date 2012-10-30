/**
 * Task Builder Class
 * 
 * This is a class that will take a json string convert it to an event object
 * @author Justin Anderson
 * @version 1.0
 */

package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.TaskType;
import org.ndacm.acmgroup.cnp.TaskType.CNPTaskType;

import com.google.gson.Gson;

public class TaskBuilder
{

	private static String jsonString;   //This is the raw form of the string
	private Gson gs;					//This is the api call to the GSON object
	public JSONTask t;					//This is the special task for json
	private CNPTaskType taskType;		//This is the CNPTaskType enumeration
	
	/**
	 * Default Constructor
	 * @param json	String	This is the json string to convert
	 */
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
	
	/**
	 * getCNPTask()
	 * This will return the Task Object
	 * @return
	 */
	public Task getCNPTask()
	{
	
		Task ret = null;
		switch(taskType)
		{
			case CHAT:
		//		ret =  new ChatTask(username, msg);
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
