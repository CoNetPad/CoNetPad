import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

	private ServerSocket serverSocket;
	private BlockingQueue<Integer> queue;
	private QueueThread qthread;
	public ArrayList<ConnectionThread> clientList;

	public Server() {
		serverSocket = null;
		queue = new LinkedBlockingQueue<Integer>();
	}

	public void run() {

		try {
			System.out.println("Trying to open socket");
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(-1);
		}
		System.out.println("Starting to listen for clients");
		int id = 0;

		clientList = new ArrayList<ConnectionThread>(0);

		System.out.println("Starting queue thread");
		qthread = new QueueThread(queue, clientList, this);
		qthread.start();

		while (true) {
			try {
				ConnectionThread thread = new ConnectionThread(
						serverSocket.accept(), id, queue);
				clientList.add(thread);
				thread.start();
				System.out.println("Client connected");
				id++;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}

		System.out.println("Closing connections");

		for (int i = 0; i < clientList.size(); i++) {
			try {
				clientList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopServeer() {
		System.out.println("Cleaning network");
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < clientList.size(); i++) {
			clientList.get(i).finalize();
		}
	}
}