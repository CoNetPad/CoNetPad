package org.ndacm.acmgroup.cnp.client;

import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.TaskBuilder;

public class JustinTest {

	
	public static void main(String[] args)
	{
		TaskBuilder tb = new TaskBuilder("{\"type\":\"chat\", \"msg\":\"Message\", \"username\":\"ander\"}");
		Task t = tb.getCNPTask();
		System.out.println(t);
	}
}
