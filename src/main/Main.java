package main;
import javax.swing.JFrame;

public class Main{
	
	public static JFrame window;
	
	public static void main(String[] args) {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Fight - CS4800");
		window.setUndecorated(true);
		
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);		
		window.pack();
		
		gamePanel.setupGame();
		gamePanel.enableFullscreen();
		gamePanel.initializeSemaphores();
		gamePanel.startGameThread();
		
		
		gamePanel.achievements.startProducerThread();
		
		window.setLocationRelativeTo(null); // center of screen
		window.setVisible(true);
		
	}
}