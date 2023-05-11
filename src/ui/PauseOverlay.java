package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameStates;
import gamestates.Playing;
import main.GameClass;
import utilities.LoadnSave;
import static utilities.Constants.UI.URMButtons.*;


// represents the pause menu
public class PauseOverlay {
	
	private Playing playing;				// playing game state
	private BufferedImage backgroundImg;	// pause menu background
	private int bgX, bgY, bgW, bgH;			// position of image on screen
	private URMButtons menuB, replayB, unpauseB; 	// paused menu buttons

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		
		loadBackground();
		createURMButtons();
	}
	
	// create pause menu buttons
	private void createURMButtons() {
		int menuX = (int) (313 * GameClass.SCALE);
		int replayX = (int) (387 * GameClass.SCALE);
		int unpauseX = (int) (462 * GameClass.SCALE);
		int bY = (int) (325 * GameClass.SCALE);
		
		menuB = new URMButtons(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new URMButtons(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new URMButtons(unpauseX, bY, URM_SIZE, URM_SIZE, 0);


	}

	// load pause menu background image
	private void loadBackground() {
		backgroundImg = LoadnSave.getSpriteAtlas(LoadnSave.PAUSE_BACKGROUND);
		bgW = (int)(backgroundImg.getWidth() * GameClass.SCALE);
		bgH = (int)(backgroundImg.getHeight() * GameClass.SCALE);
		bgX = GameClass.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (25 * GameClass.SCALE);
	}

	// update buttons
	public void update() {
		menuB.update();
		replayB.update();
		unpauseB.update();
	}
	
	// draw pause menu background / buttons
	public void draw(Graphics g) {
		//background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		
		// pause buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
	}
	
	public void mouseDragged() {
		
	}
	
	// user pressed menu buttons
	public void mousePressed(MouseEvent e) {
		if(isIn(e, menuB))
			menuB.setMousePressed(true);	//pressed menu button
		else if (isIn(e, replayB))
			replayB.setMousePressed(true);	// pressed replay button
		else if (isIn(e, unpauseB))
			unpauseB.setMousePressed(true);	// pressed unpause button
	}

	// user releases pause button
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {	// change game state to menu
				GameStates.state = GameStates.MENU;
				playing.unpauseGame();
			}
		}
		else if (isIn(e, replayB)) {		// restart fight
			if (replayB.isMousePressed())
				System.out.println("Rematch!");
		} 
		else if (isIn(e, unpauseB)) {		// resume game
			if (unpauseB.isMousePressed())
				playing.unpauseGame();
		}
		
		menuB.resetBooleans();
		replayB.resetBooleans();
		unpauseB.resetBooleans();
	}
	
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		
		if(isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}


}
