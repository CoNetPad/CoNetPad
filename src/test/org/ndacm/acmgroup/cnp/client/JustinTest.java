package org.ndacm.acmgroup.cnp.client;

import junit.framework.TestCase;

import org.ndacm.acmgroup.cnp.database.Database;

public class JustinTest extends TestCase {

	
	public static void main(String[] args)
	{
		try{
			Database db = new Database(null);
			db.createAccount("John", "test@gmail.com", "password");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
