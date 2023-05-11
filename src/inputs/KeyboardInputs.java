package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.GameStates;
import main.GamePanel;
import static utilities.Constants.MovementConstants.*;


// Handles inputs from the keyboard using the KeyListener interface
public class KeyboardInputs implements KeyListener {
	
	private GamePanel panel;	// user inputs will change the character in the game panel
	
	//constructor takes the Game's Jpanel as a parameter to get the input through the panel
	public KeyboardInputs(GamePanel panel) {
		this.panel = panel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override  
	public void keyPressed(KeyEvent e) {
		switch(GameStates.state) {
		case MENU:
			panel.getGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			panel.getGame().getPlaying().keyPressed(e);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(GameStates.state) {
		case MENU:
			panel.getGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			panel.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;
		}
	}

}
