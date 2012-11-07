package org.ndacm.acmgroup.cnp;

public class Account {
	
	private String username;
	private String email;
	
	public Account()
	{
		username = "guest";
		email = "none";
	}
	public Account(String uname, String eml)
	{
		username = uname;
		email = eml;
	}
	public enum FilePermissionLevel {
		READ,
		READ_WRITE,
		UNRESTRICTED
	}
	
	public enum ChatPermissionLevel {
		MUTE,
		TO_LEADER,
		VOICE
	}

}
