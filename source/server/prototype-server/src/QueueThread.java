import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;

public class QueueThread extends Thread {
	private BlockingQueue<Integer> queue;
	private FileOutputStream fos;
	private OutputStreamWriter out;

	public QueueThread(BlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
		try {
			fos = new FileOutputStream("server.txt");
			out = new OutputStreamWriter(fos, "UTF-8");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				Integer integer = queue.take();
				System.out.println("Queue Take:" + integer);
				out.write(Integer.toString(integer));
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
