package javiergs;

import org.eclipse.paho.client.mqttv3.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements MqttCallback, Runnable {
	
//	private static final String IP = "localhost";
//	private static final int PORT = 8888;

	private final static String BROKER = "tcp://test.mosquitto.org:1883";
	private final static String TOPIC = "pong/game";
	private final static String CLIENT_ID = "mqtt-pong-publisher";
	
//	private final ObjectOutputStream outputStream;
//	private final ObjectInputStream inputStream;

	private final MqttClient client;
	private boolean isReady;
	
	public Client() throws MqttException {
//		Socket socket = new Socket(IP, PORT);
//		outputStream = new ObjectOutputStream(socket.getOutputStream());
//		inputStream = new ObjectInputStream(socket.getInputStream());
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
////				receive();
//				send();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	private void send() throws MqttException {
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
			PongBrain.getInstance().setServerPlayerY(pongData.getServerPlayerY());
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
		receive(topic, mqttMessage);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	}
}