import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.awt.*;


public class Client implements Runnable, ActionListener {
	
	private Socket socket;
	private ObjectOutputStream outputStream;
	private ClientGUI clientGUI;
	
	public Client(ClientGUI clientGUI) {
		this.clientGUI = clientGUI;
		System.out.println("0");
		connectToServer();
		System.out.println("00");
		// Sending random ball positions every 1 second for demonstration
		Timer timer = new Timer(1000, this);
		timer.start();
		System.out.println("000");
	}
	
	private void connectToServer() {
		System.out.println("1");
		try {
			System.out.println("2");
			socket = new Socket("localhost", 8888); // Change this to your server address
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("3");
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
		System.out.println("100 ");
		try {
			System.out.println("120 ");
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			System.out.println("140 ");
			while (true) {
				System.out.println("160 ");
				Point ballPosition = (Point) inputStream.readObject();
				System.out.println("170 ");
				clientGUI.updateBallPosition(ballPosition);
				System.out.println("180 ");
			}
		} catch (Exception e) {
		//	e.printStackTrace();
			System.out.println(">>>>> EEEERRRROORRRR " + e.getMessage());
		}
		System.out.println("200 ");
	}
	
	public static void main(String[] args) {
		ClientGUI clientGUI = new ClientGUI();
		Client client = new Client(clientGUI);
		new Thread(client).start();
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Point position = new Point((int) (Math.random() * 300), (int) (Math.random() * 300));
		sendBallPosition(position);
	}
}
