import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class QueueThread extends Thread {
	private BlockingQueue<Integer> queue;
	private FileOutputStream fos;
	private OutputStreamWriter out;
	private ArrayList<ConnectionThread> clientList;
	private int counter;
	private boolean stop;
	private Server server;

	public QueueThread(BlockingQueue<Integer> queue,
			ArrayList<ConnectionThread> clientList, Server server) {
		super();
		this.queue = queue;
		this.clientList = clientList;
		this.server = server;
		try {
			fos = new FileOutputStream("server.txt");
			out = new OutputStreamWriter(fos, "UTF-8");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		counter = 0;
		stop = false;
	}

	public void run() {
		while (!stop) {
			try {
				Integer integer = queue.take();
				System.out.println("Queue Take:" + integer);
				out.write(Integer.toString(integer));
				if(integer == -1 && counter == 0){
					break;
				}
				for (int i = 0; i < clientList.size(); i++) {
					if (clientList.get(i).isRunning) {
						clientList.get(i).sendCommand(integer);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).isRunning) {
				clientList.get(i).sendCommand(-2);
			}
		}
		try {
			out.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.stopServeer();
	}

	public void clientConnects() {
		counter++;

	}

	public void clientDisconnects() {
		counter--;
		if (counter == 0) {
			stop = true;
		}
	}
}
