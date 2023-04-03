package main;
import javax.swing.JFrame;

public class Main{
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Fight - CS4800");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
		
		window.setLocationRelativeTo(null); // center of screen
		window.setVisible(true);
		
	}
}