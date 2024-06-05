package javiergs;

import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;

import org.eclipse.paho.client.mqttv3.*;

public class Server implements MqttCallback, Runnable {
	
//	private static final int PORT = 8888;
//
//	private final ObjectOutputStream outputStream;
//	private final ObjectInputStream inputStream;
	private final static String BROKER = "tcp://test.mosquitto.org:1883";
	private final static String TOPIC = "pong/game";
	private final static String CLIENT_ID = "mqtt-pong-subscriber";

	private final MqttClient client;
	private boolean isReady;
	
	
	public Server() throws MqttException {
//		ServerSocket serverSocket = new ServerSocket(PORT);
//		Socket clientSocket = serverSocket.accept();
//		outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//		inputStream = new ObjectInputStream(clientSocket.getInputStream());
		client = new MqttClient(BROKER, CLIENT_ID);
		client.setCallback(this);
		client.connect();
		client.subscribe(TOPIC);
		isReady = true;
	}
	
	@Override
	public void run() {
//		while (true) {
//			try {
//				Thread.sleep(1000 / 30);
//				send();
////				receive();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	private void send() throws MqttException, IOException {
		PongData pongData = PongBrain.getInstance().getPongData().clone();
		String content = "Test msg";
		MqttMessage message = new MqttMessage(content.getBytes());
		message.setQos(2);
		client.publish(TOPIC, message);
//		outputStream.writeObject(pongData);
//		outputStream.flush();
	}
	
	private void receive(String topic, MqttMessage message) {
		try {
			PongData pongData = new PongData();
			PongBrain.getInstance().setClientPlayerY(pongData.getClientPlayerY());
			// create an engine for the pong game, i,e, the ball position logic
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isReady() {
		return isReady;
	}

	@Override
	public void connectionLost(Throwable throwable) {
		System.out.println("Connection lost: " + throwable.getMessage());
	}

	@Override
	public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
		System.out.println("Message arrived. Topic: " + topic + " Message: " + new String(mqttMessage.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	}
}