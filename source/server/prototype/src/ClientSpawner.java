import java.io.IOException;

public class ClientSpawner {
	public static void main(String[] args) throws IOException {
		System.out.println("Spawning clients");		
		
		for (int i = 0; i < 10; i++) {
			new TestClient(i).run();
			System.out.println("Client created");		
		}
	}
}
