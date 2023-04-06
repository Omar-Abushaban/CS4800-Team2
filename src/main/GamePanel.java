package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import achievements.Achievements;
import entity.Player;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS/DIMENSIONS
	final int screenWidth = 1600;
	final int screenHeight = 900;
	
	// GAME
	String player1Name = "Player 1";
	String player2Name = "Player 2";
	
	//FIXME !!!! IF WE DO ALLOW USERS TO CHANGE NAMES, ENSURE NO TWO PLAYERS HAVE THE SAME NAME !!!
	public Keybinds keybinds1 = new Keybinds(player1Name);
	public Keybinds keybinds2 = new Keybinds(player2Name);
	
	public KeyHandler keyH1 = new KeyHandler(this, keybinds1, 1);
	public KeyHandler keyH2 = new KeyHandler(this, keybinds2, 2);
	int FPS = 60;
	Thread gameThread;
	String winner;
	
	// ACHIEVEMENTS
	public Achievements achievements = new Achievements(this);
	
	// UI
	UI ui = new UI(this);
	
	// PLAYERS
	Player player1 = new Player("Player 1", this, keyH1);
	Player player2 = new Player("Player 2", this, keyH2);
	
	// GAME STATES
	public enum GameState {
		MainMenu, AchievementsMenu, Options, CharacterSelect, InGame, GameOver;
	}
	public GameState gameState = GameState.MainMenu;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH1);
		this.addKeyListener(keyH2);
		this.setFocusable(true);
	}

	
	public void setupGame() {
		// Pass
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run(){
		double drawInterval = 1000000000/FPS; // nanoTime is in nanoseconds hence 10^9
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}	
	}
	
	public void update() {
		//player1.update();
		//player2.update();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// 2D Graphics for sprites/etc
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// DRAW ACCORDING TO GAME STATE
		switch(gameState) {
		case CharacterSelect:
			ui.draw(g2);
			break;
		case GameOver:
			ui.draw(g2);
			break;
		case InGame:
			ui.draw(g2);
			break;
		case MainMenu:
			ui.draw(g2);
			break;
		case AchievementsMenu:
			ui.draw(g2);
			break;
		case Options:
			ui.draw(g2);
			break;
		default:
			break;
		}
		g2.dispose();
	}
}
