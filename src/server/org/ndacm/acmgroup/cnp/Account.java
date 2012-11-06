package server.org.ndacm.acmgroup.cnp;

public class Account {
	
	private String username;
	private String email;
	
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
