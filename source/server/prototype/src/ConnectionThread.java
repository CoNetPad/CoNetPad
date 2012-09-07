import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ConnectionThread extends Thread {
	private Socket socket = null;
	private int id;
	public ConnectionThread(Socket socket, int id) {
		super();
		this.socket = socket;
		this.id = id;
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
				//System.out.print("Command recieved from " + id + " ");
				outputCode = kkp.processInput(inputLine);
				switch (outputCode) {
				case 1:
					break;
				case -1:
					stop = true;
					break;
				default:
					//System.out.println(outputCode);
					break;
				}
			}
			out.close();
			in.close();
			socket.close();
			System.out.println("Thread for client stopped");
			long eDateTime = new Date().getTime();
		    System.out.println(id + " Took at: " + (eDateTime-lDateTime));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}