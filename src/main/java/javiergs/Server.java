import java.io.*;
import java.net.*;
import java.awt.*;

public class Server implements Runnable {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ObjectOutputStream outputStream;
	
	public Server() {
		startServer();
	}
	
	private void startServer() {
		try {
			serverSocket = new ServerSocket(8888); // Change this to your desired port
			clientSocket = serverSocket.accept();
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBallPosition(Point position) {
		try {
			outputStream.writeObject(position);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
				sendBallPosition(new Point((int) (Math.random() * 400), (int) (Math.random() * 400)));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		new Thread(server).start();
	}
}
