package main;

import java.awt.Graphics;

import gamestates.GameStates;
import gamestates.Menu;
import gamestates.Playing;
import ui.OptionsDialog;

// The main class for the entire game. Initializes the game window, handlers, players/
// enemies, etc.
public class GameClass implements Runnable {
	
	private GameWindow window;		// JFrame for the GUI
	private GamePanel panel;		// JPanel for the GUI
	private Thread gameLoop;		// Thread that will run the game loop
	private final int FPS = 120;	// set amount of frames/sec
	private final int UPS = 150;	// set amount of updates/sec
	private GameStates previousState = null;
    private MusicManager musicManager = new MusicManager();
	private Playing playing;
	private Menu menu;
	private GameClass game;
	
	// level data
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	// constructor
	public GameClass () {
		createEntities();					// create game entities
		
		panel = new GamePanel(this);		// create game panel
		window = new GameWindow(panel);		// create game window
		panel.requestFocusInWindow(); 		// focus input on game panel
		
		startGameLoop();					// begin game loop

	}
	
	// initialize game and level
	private void createEntities() {
		menu = new Menu(this);
		playing = new Playing(this);
	}

	// the game loop runs in its own thread
	public void startGameLoop() {
		gameLoop = new Thread(this);
		gameLoop.start();		// calls run()
	}
	public GameClass getGame() {
	    return this.game;
	  }
	
	public void restartGame() {
		  // Reset the game state to PLAYING
		  GameStates.state = GameStates.PLAYING;

		  // Create game entities
		  createEntities();

		  // Reset any other necessary game data or states here...
		}
	// updates the game according to whatever game state were in
	public void update() {
		switch(GameStates.state) {
		case MENU:
			 menu.update();
             if (previousState != GameStates.MENU) { // If state has just changed to MENU
                 previousState = GameStates.MENU;
                 musicManager.playMusic(GameStates.MENU);
             }
			break;
		case PLAYING:
			try { // Serializes Threads
				playing.gameSem.acquire();
				playing.update();
			} catch(InterruptedException e) {
				e.printStackTrace();
			} finally {
				playing.playerSem.release();
			}
			if (previousState != GameStates.PLAYING) { // If state has just changed to PLAYING
                previousState = GameStates.PLAYING;
                musicManager.playMusic(GameStates.PLAYING);
            }
			break;
		case OPTIONS:
			OptionsDialog optionsDialog = new OptionsDialog(musicManager, window);
			optionsDialog.setVisible(true);
			break;
			
		case QUIT:
			
		default:
			System.exit(0);
			break;
		}
	}
	
	// renders the game according to what game state
	public void render(Graphics g) {
		switch(GameStates.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		default:
			break;
		}
	}

	@Override
	// game loop thread 
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS;	// duration of each frame
		double timePerUpdate = 1000000000.0 / UPS;	// duration of each update
		
		long previousTime = System.nanoTime();		// save time previous to first update
		
		int frames = 0;								// frame counter
		int updates = 0;							// updates counter
		
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {
			
			
			long currentTime = System.nanoTime();	// save current time
			
			deltaU += (currentTime - previousTime) / timePerUpdate;		
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();				// update game logic
				updates ++;
				deltaU --;
			}
			
			if (deltaF >= 1) {
				panel.repaint();		// re-render game (call GamePanel.paintComponent())
				frames++;
				deltaF --;
			}
			
			// FPS/UPS checker
			if (System.currentTimeMillis() - lastCheck >= 1000) {
					lastCheck = System.currentTimeMillis();
					System.out.println("FPS: " + frames + " | UPS: " + updates);
					frames = 0;
					updates = 0;
				}
		}
		
	}
	
	// calls player booleans to reset to false since game has lost focus
	public void windowFocusLost() {
		if(GameStates.state == GameStates.PLAYING) {
			playing.getPlayer().resetBooleans();
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
	public Playing getPlaying() {
		return playing;
	}
	

}
