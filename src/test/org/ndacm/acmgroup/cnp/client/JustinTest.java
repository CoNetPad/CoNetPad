package org.ndacm.acmgroup.cnp.client;

import org.ndacm.acmgroup.cnp.database.Database;
import org.ndacm.acmgroup.cnp.task.Task;
import org.ndacm.acmgroup.cnp.task.TaskBuilder;

public class JustinTest {

	
	public static void main(String[] args)
	{
		try{
			Database db = new Database();
			db.createAccount("John", "test@gmail.com", "password");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
