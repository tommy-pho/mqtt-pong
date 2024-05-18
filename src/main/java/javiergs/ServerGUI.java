import javax.swing.*;

public class ServerGUI extends JFrame {
	
	public ServerGUI() {
		setTitle("Pong Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(ServerGUI::new);
	}
}
