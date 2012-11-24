package org.ndacm.acmgroup.cnp.task;

import org.ndacm.acmgroup.cnp.network.CNPConnection;

/**
 * A task that should be executed by the server itself. Contains a reference to a server
 * so that this task can be submitted to the CNPServer's ExecutorService. ServerTasks
 * should not require serialization - tasks are not guaranteed to execute in order.
 * 
 * ServerTasks should be used when a user task needs to be executed before that user
 * has joined a session or opened a file. Since the CNPServer does not track
 * the connections of all connected clients (that task is delegated to CNPSessions and 
 * ServerSourceFiles), a CNPConnection reference should be set upon receipt by the server,
 * to allow for a task response to be sent directly back to the task sender.
 *
 */
public abstract class ServerTask extends Task {
	
	protected ServerTaskExecutor server;
	private CNPConnection connection;
	
	public void setServer(ServerTaskExecutor server) {
		this.server = server;
	}
	
	public ServerTaskExecutor getServer() {
		return server;
	}

	public CNPConnection getConnection() {
		return connection;
	}

	public void setConnection(CNPConnection connection) {
		this.connection = connection;
	}
	
	

}
