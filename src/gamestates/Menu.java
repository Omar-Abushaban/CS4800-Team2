package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.GameClass;
import ui.MenuButton;
import utilities.LoadnSave;

// the menu state of the game
public class Menu extends State implements StateMethods{
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg, menuBackground;
	private int menuX, menuY, menuWidth, menuHeight;

	public Menu(GameClass game) {
		super(game);
		loadButtons();
		loadBackground();
		menuBackground = LoadnSave.getSpriteAtlas(LoadnSave.MENU_BACKGROUND_IMG);
	}
	
	// get menu background image
	private void loadBackground() {
		backgroundImg = LoadnSave.getSpriteAtlas(LoadnSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth()*GameClass.SCALE);
		menuHeight = (int) (backgroundImg.getHeight()*GameClass.SCALE);
		menuX = GameClass.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int)(45 * GameClass.SCALE);
	}

	// load positions of buttons into array
	private void loadButtons() {
		buttons[0] = new MenuButton(GameClass.GAME_WIDTH / 2, (int)(150*GameClass.SCALE), 0, GameStates.PLAYING);
		buttons[1] = new MenuButton(GameClass.GAME_WIDTH / 2, (int)(220*GameClass.SCALE), 1, GameStates.OPTIONS);
		buttons[2] = new MenuButton(GameClass.GAME_WIDTH / 2, (int)(290*GameClass.SCALE), 2, GameStates.QUIT);
	}

	@Override
	// update menu
	public void update() {
		for(MenuButton mb: buttons)
			mb.update();
	}

	@Override
	// draw menu buttons
	public void draw(Graphics g) {
		g.drawImage(menuBackground, 0, 0, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT, null);
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		for(MenuButton mb: buttons)
			mb.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb: buttons) {
			if(isIn(e, mb)) 
				mb.setMousePressed(true);	
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb: buttons) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed())
					mb.applyGameState();
				break;
			}
		}
		resetButtons();
	}

	private void resetButtons() {
		for(MenuButton mb: buttons) 
			mb.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb: buttons) 
			mb.setMouseOver(false);
		
		for(MenuButton mb: buttons) 
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			GameStates.state = GameStates.PLAYING;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
