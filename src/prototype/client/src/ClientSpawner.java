import java.io.IOException;


public class ClientSpawner {
	public static final int NUMBER_OF_CLIENTS = 10;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Spawning clients");		
		
		for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
			new TestClient(i).start();
			System.out.println("Client created");		
		}
	}
}
