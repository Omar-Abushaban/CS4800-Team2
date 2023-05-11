package gamestates;

import java.awt.event.MouseEvent;

import main.GameClass;
import ui.MenuButton;

// the super class of all game states
public class State {

	protected GameClass game;
	
	public State(GameClass game) {
		this.game = game;
	}
	
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	
	public GameClass getGame() {
		return game;
	}
}
