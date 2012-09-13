import java.io.*;
import java.net.*;
import java.util.Random;

public class TestClient extends Thread {

	private int id; // ID of this client
	private Socket kkSocket = null; // Socket for comunicating with the server

	private FileOutputStream fos;
	private OutputStreamWriter fout;

	private PrintWriter out = null;
	private BufferedReader in = null;

	private ListenThread lthread; // Thread that handles incoming connections
									// from the server
	private WriteThread wthread; // Thread that handles outgoing connections to
									// the server

	public TestClient(int id) {
		this.id = id;
	}

	public void run() {

		try {
			System.out.println("Opening client-socket");
			kkSocket = new Socket("127.0.0.1", 4444);
			fos = new FileOutputStream("client-" + id + ".txt");
			fout = new OutputStreamWriter(fos, "UTF-8");
			in = new BufferedReader(new InputStreamReader(
					kkSocket.getInputStream()));
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			lthread = new ListenThread();
			lthread.start();
			wthread = new WriteThread();
			wthread.start();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host.");
			System.exit(1);
		} catch (IOException e) {
			System.err
					.println("Couldn't get I/O for the connection to the host.");
			System.exit(1);
		}

		try {
			wthread.join();
			lthread.join();
			System.out.println("Threads completed");
		} catch (InterruptedException e) {
			System.err.println("Waiting for threads failed");
		}

		try {
			System.out.println("Closing reader and writer");
			in.close();
			out.close();
		} catch (IOException e) {
			System.err.println("Failed closing reader and writer");
		}
		System.out.println("Client stopped");
	}

	private class ListenThread extends Thread {

		public void run() {
			System.out.println("Starting listen thread");
			try {
				int outputCode;
				String inputLine = "";
				boolean stop = false;
				while (!stop && (inputLine = in.readLine()) != null) {
					outputCode = Integer.parseInt(inputLine);
					fout.write(Integer.toString(outputCode) + "\n");
					System.out.println(id + " " + outputCode + " <=");

					if (outputCode == -2) {
						stop = true;
						System.out.println(outputCode
								+ " recieved, stopping client");
					}
				}

			} catch (SocketException e1) {
				System.err
						.println("Exception in the connection, probably closed by server");
			} catch (IOException e1) {
				System.err.println("Exception with IO component");
			} catch (NumberFormatException e1) {
				System.err.println("Exception parsing string to Integer");
			}

			try {
				System.out.println("Closing file");
				fout.close();
				fos.close();
			} catch (IOException e) {
				System.err.println("Closing file writers filed");
			}
			System.out.println("Finishing listen thread");
		}
	}

	private class WriteThread extends Thread {

		public void run() {
			System.out.println("Starting write thread");
			try {
				Random rand = new Random();
				int code = -1;

				for (int i = 0; i < 500; i++) {
					code = rand.nextInt(100);
					out.println(code);
					System.out.println(id + " " + code + " => ");
					Thread.sleep(code);
				}
				out.println(-1);
			} catch (NumberFormatException e1) {
				System.err.println("Exception parsing string to Integer");
			} catch (InterruptedException e) {
				System.err.println("Exception while sleeping the thread");
			}
			System.out.println("Finishing write thread");
		}
	}
}