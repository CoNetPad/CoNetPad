package org.ndacm.acmgroup.cnp.client;
import org*;
public class JustinTest {

	
	public static void main(String[] args)
	{
		TaskBuilder tb = new TaskBuilder("{\"type\":\"chat\", \"msg\":\"Message\", \"username\":\"ander\"}");
		Task t = tb.getCNPTask();
		System.out.println(t);
	}
}
