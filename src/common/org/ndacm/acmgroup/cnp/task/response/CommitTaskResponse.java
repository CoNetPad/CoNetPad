package org.ndacm.acmgroup.cnp.task.response;

public class CommitTaskResponse extends TaskResponse {
	
	private boolean success;

	public CommitTaskResponse(boolean success) {
		this.success = success;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public boolean isSuccess(){
		return this.success;
	}
}
