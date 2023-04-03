package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel.GameState;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		
		// Handle keys based on game state (probably import keybinds w/ constructor and player later)
		switch(gp.gameState) {
		case CharacterSelect:
			break;
		case GameOver:
			break;
		case InGame:
			break;
		case MainMenu:
			if (keycode == KeyEvent.VK_DOWN || keycode == KeyEvent.VK_S)
				gp.ui.menuIterator = (gp.ui.menuIterator + 1) % gp.ui.mainMenuItems;
			if (keycode == KeyEvent.VK_UP || keycode == KeyEvent.VK_W) {
				if (gp.ui.menuIterator <= 0)
					gp.ui.menuIterator = gp.ui.mainMenuItems - 1;
				else
					gp.ui.menuIterator = gp.ui.menuIterator - 1;
			}
			if (keycode == KeyEvent.VK_ENTER) {
				switch(gp.ui.menuIterator) {
				case 0: // START
					gp.gameState = GameState.CharacterSelect;
					break;
				case 1: // OPTIONS
					break;
				case 2: // ACHIEVEMENTS
					break;
				case 3: // EXIT
					System.exit(0);
					break;
				default:
					break;
				}
			}
				
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e){
		// TODO Auto-generated method stub
		
	}
	
}
