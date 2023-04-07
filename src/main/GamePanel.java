package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

import achievements.Achievements;
import entity.Player;


public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN
	public final int screenWidth = 1600;
	public final int screenHeight = 900;
	int fullscreenWidth = screenWidth;
	int fullscreenHeight = screenHeight;
	BufferedImage screenBuffer;
	Graphics2D g2;
		
	// GAME
	String player1Name = "Player 1";
	String player2Name = "Player 2";
	Thread gameThread;
	String winner;
	
	//FIXME !!!! IF WE DO ALLOW USERS TO CHANGE NAMES, ENSURE NO TWO PLAYERS HAVE THE SAME NAME !!!
	//KEYBINDS / KEYHANDLER
	public Keybinds keybinds1 = new Keybinds(player1Name);
	public Keybinds keybinds2 = new Keybinds(player2Name);
	public KeyHandler keyH1 = new KeyHandler(this, keybinds1, 1);
	public KeyHandler keyH2 = new KeyHandler(this, keybinds2, 2);
	
	// FPS
	int FPS = 60;
	
	// ACHIEVEMENTS
	public Achievements achievements = new Achievements(this);
	
	// UI
	UI ui = new UI(this);
	
	// PLAYERS
	Player player1 = new Player("Player 1", this, keyH1, 1);
	Player player2 = new Player("Player 2", this, keyH2, 2);
	Thread player1Thread;
	Thread player2Thread;
	
	// SEMAPHORES/MUTEXES FOR GAME ORDER
	public Semaphore player1Sem;
	public Semaphore player2Sem;
	public Semaphore achieveSem;
	public Semaphore gamePanelSem;
	
	
	
	// GAME STATES
	public enum GameState {
		MainMenu, AchievementsMenu, Options, CharacterSelect, InGame, GameOver;
	}
	public GameState gameState;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH1);
		this.addKeyListener(keyH2);
		this.setFocusable(true);
	}

	public void startInGame() {
		player1.setStartValues(50, screenHeight/2);
		player2.setStartValues(screenWidth-50-player1.width, screenHeight/2);
		player1Thread = new Thread(player1);
		player2Thread = new Thread(player2);
		player1Thread.start();
		player2Thread.start();
	}
	
	public void setupGame() {
		gameState = GameState.MainMenu;
		// Buffer image to draw to
		screenBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)screenBuffer.getGraphics();
	}
	
	public void enableFullscreen() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		fullscreenWidth = Main.window.getWidth();
		fullscreenHeight = Main.window.getHeight();
	}
	
	public void initializeSemaphores() {
		player1Sem = new Semaphore(1);
		player2Sem = new Semaphore(0);
		achieveSem = new Semaphore(0);
		gamePanelSem = new Semaphore(0);
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
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			// In game we only want to render after players/achievements have been calculated for
			if (gameState == GameState.InGame) {
				try{
					gamePanelSem.acquire();
					//System.out.println("Game Thread");
					while(gameThread != null) {
						currentTime = System.nanoTime();
						delta += (currentTime - lastTime) / drawInterval;
						timer += (currentTime - lastTime);
						lastTime = currentTime;
						
						if (delta >= 1) {
							drawScreenBuffer();
							drawToScreen();
							delta--;
							drawCount++;
							break;
						}
						
						if(timer >= 1000000000) {
							System.out.println("FPS: " + drawCount);
							drawCount = 0;
							timer = 0;
						}
					}
					
				} catch (InterruptedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					player1Sem.release();
				}
			}
			
			else {
				currentTime = System.nanoTime();
				delta += (currentTime - lastTime) / drawInterval;
				lastTime = currentTime;
				
				if (delta >= 1) {
					drawScreenBuffer();
					drawToScreen();
					delta--;
				}				
			}
		}	
	}
	
	public void drawScreenBuffer() {
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
			player1.draw(g2);
			player2.draw(g2);
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
	}
	
	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(screenBuffer, 0, 0, fullscreenWidth, fullscreenHeight, null);
		g.dispose();
	}
}
