package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.Enemy;
import entities.Player;
import levels.LevelManager;
import main.GameClass;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilities.LoadnSave;

// the playing game state
public class Playing extends State implements StateMethods{

	private Player player;						// player entity
	private Enemy enemy;						// enemy entity
	private LevelManager levelManager;
	//private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;			// pause menu
	private GameOverOverlay gameOverOverlay;	// game over
	private boolean paused = false;				// show pause screen or not 
	private BufferedImage playingBGImg;			// background
	private boolean gameOver;
	
	public Playing(GameClass game) {
		super(game);
		createEntities();
		playingBGImg = LoadnSave.getSpriteAtlas(LoadnSave.PLAYING_BACKGROUND_IMG);
	}
	
	// initialize playing entities
	private void createEntities() {
		levelManager = new LevelManager(game);
		//enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int)(64*GameClass.SCALE), (int)(40*GameClass.SCALE), this);
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
		enemy = new Enemy (1100, 200, (int)(Enemy.CRABBY_WIDTH_DEFAULT*GameClass.SCALE), (int)(Enemy.CRABBY_HEIGHT_DEFAULT*GameClass.SCALE), this);
		enemy.loadLevelData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
	}

	@Override
	// update level and player in game logic
	public void update() {
		if (!paused) {			// not paused
			levelManager.update();
			player.update();
			enemy.update();
			//enemyManager.update();
		} else {				// paused
			pauseOverlay.update();
		}
	}

	@Override
	// draw level, player, background, and pause overlay
	public void draw(Graphics g) {
		g.drawImage(playingBGImg, 0, 0, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT, null);
		levelManager.draw(g);
		player.render(g);
		enemy.render(g);
		//enemyManager.draw(g);
		
		if (paused) {		// if paused game
			g.setColor(new Color(0,0,0, 100));
			g.fillRect(0, 0, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver)	// if game over
			gameOverOverlay.draw(g);
	}
	
	// reset all entities
	public void resetAll() {
		gameOver = false;
		paused = false;
		player.resetAll();
		enemy.resetAll();
		//enemyManager.resetAllEnemies();
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//if (!gameOver)
			//if (e.getButton() == MouseEvent.BUTTON1) 
			// player.setAttack(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
			// get code of pressed key
			switch (e.getKeyCode()) {
				
			// change character directions based on inputs
			case KeyEvent.VK_A:
				System.out.println("Pressed A");
				player.setLeft(true);				// set player direction to left				
				break;
			case KeyEvent.VK_LEFT:
				enemy.setLeft(true);				// set enemy direction to left
				break;
				
			case KeyEvent.VK_S:
				System.out.println("Pressed S");
				player.setDown(true);				// set direction downwards
				break;
			case KeyEvent.VK_DOWN:
				enemy.setDown(true);				// set enemy direction downwards
				break;
				
			case KeyEvent.VK_W:
				System.out.println("Pressed W");
				player.setJump(true);				// player jumps
				break;
			case KeyEvent.VK_UP:
				enemy.setJump(true);				// enemy jumps
				break;
				
			case KeyEvent.VK_D:
				System.out.println("Pressed D");
				player.setRight(true);				// set player direction towards the right
				break;
			case KeyEvent.VK_RIGHT:					// set enemy direction to the right
				enemy.setRight(true);
				break;
				
			case KeyEvent.VK_C:
				System.out.println("Pressed C");	// player attacks
				player.setAttack(true);
				break;
			case KeyEvent.VK_SPACE:					// enemy attacks
				enemy.setAttack(true);
				break;
				
			case KeyEvent.VK_ESCAPE:				// if escape, pause and un-pause
				paused = !paused;
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);		// player not moving to the left
				break;
			case KeyEvent.VK_LEFT:
				enemy.setLeft(false);		// enemy not moving left
				break;
				
			case KeyEvent.VK_S:
				player.setDown(false);		// player not moving down
				break;
			case KeyEvent.VK_DOWN:
				enemy.setDown(false);		// enemy not moving down
				break;
				
			case KeyEvent.VK_W:	
				player.setJump(false);		// player not jumping
				break;
			case KeyEvent.VK_UP:
				enemy.setJump(false);		// enemy not jumping
				break;
			
			case KeyEvent.VK_D:
				player.setRight(false);		// player not moving right
				break;
			case KeyEvent.VK_RIGHT:			
				enemy.setRight(false);		// enemy not moving right
				break;
			}
	}
	
	// calls player booleans to reset to false since game has lost focus
	public void windowFocusLost() {
		player.resetBooleans();
	}
	
	public void unpauseGame() {
		paused = false;
	}
			
	// returns the player object
	public Player getPlayer() {
		return player;
	}
	
	// if player damages enemy reduce health by 5
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		if(attackBox.intersects(enemy.getHitBox()))
			enemy.changeHealth(-5);
	}
	
	// if player damages enemy reduce health by 5
	public void checkPlayerHit(Rectangle2D.Float attackBox) {
		if(attackBox.intersects(player.getHitBox()))
			player.changeHealth(-5);
	}
}
