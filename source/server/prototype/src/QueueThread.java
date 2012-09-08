import java.util.concurrent.BlockingQueue;


public class QueueThread extends Thread{
	private BlockingQueue<Integer> queue;
	public QueueThread (BlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}
	
	public void run(){
		while(true){
			try {
				System.out.println("Queue Take:" + queue.take());
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
