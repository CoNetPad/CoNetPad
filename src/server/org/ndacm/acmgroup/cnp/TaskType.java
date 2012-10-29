package org.ndacm.acmgroup.cnp;

public class TaskType {
	
	public enum CNPTaskType {
		CHAT{
			public String toString()
			{
				return "chat";
			}
		},
		EDITFILE,
		COMPILE,
		AUTHENTICATE{
			public String toString()
			{
				return "authenticate";
			}
		},
		VOID{
			public String toString()
			{
				return "void";
			}
		}
	}
	public static CNPTaskType taskFromString(String input)
	{
		String s = input.toLowerCase();
		if(s.equals("chat"))
		{
			return CNPTaskType.CHAT;
		}
		if(s.equals("authenticate"))
		{
			return CNPTaskType.AUTHENTICATE;
		}
		else
		{
			return CNPTaskType.VOID;
		}
	}
	
}
