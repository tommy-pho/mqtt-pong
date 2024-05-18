import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {
	
	private JLabel ballLabel;
	
	public ClientGUI() {
		setTitle("Pong Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLayout(new BorderLayout());
		
		ballLabel = new JLabel("o");
		ballLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(ballLabel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void updateBallPosition(Point position) {
		ballLabel.setLocation(position);
		repaint();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(ClientGUI::new);
	}
}
