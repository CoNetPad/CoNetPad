import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class ConnectionThread extends Thread {
	private Socket socket = null;
	private int id;
	private BlockingQueue<Integer> queue;
	private boolean serverIsListening;
	
	public ConnectionThread(Socket socket, int id, BlockingQueue<Integer> queue) {
		super();
		this.socket = socket;
		this.id = id;
		this.queue = queue;
	}

	public void run() {

		try {
			System.out.println("Thread for client started");
			long lDateTime = new Date().getTime();
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			String inputLine;
			int outputCode;
			Protocol kkp = new Protocol();
			boolean stop = false;
			while ((inputLine = in.readLine()) != null && !stop) {
				// System.out.print("Command recieved from " + id + " ");
				outputCode = kkp.processInput(inputLine);
				if (outputCode == -1)
					stop = true;
				try {
					queue.put(new Integer(outputCode));
					out.println(outputCode);
					System.out.println("Queue Put: From " + id + " "
							+ outputCode);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			out.close();
			in.close();
			System.out.println("Thread for client stopped");
			long eDateTime = new Date().getTime();
			System.out.println(id + " Took : " + (eDateTime - lDateTime)
					+ " ms");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}