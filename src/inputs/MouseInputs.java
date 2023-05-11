package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.GameStates;
import main.GamePanel;

// Handles inputs coming from the mouse
public class MouseInputs implements MouseListener, MouseMotionListener {
	
	private GamePanel panel;	// game panel that will reflect user mouse input commands
	
	// constructor
	public MouseInputs(GamePanel panel) {
		this.panel = panel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	// set position of player to position of mouse
	public void mouseMoved(MouseEvent e) {
		switch(GameStates.state) {
		case MENU:
			panel.getGame().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			panel.getGame().getPlaying().mouseMoved(e);
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(GameStates.state) {
		case PLAYING:
			panel.getGame().getPlaying().mouseClicked(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(GameStates.state) {
		case MENU:
			panel.getGame().getMenu().mousePressed(e);;
			break;
		case PLAYING:
			panel.getGame().getPlaying().mousePressed(e);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(GameStates.state) {
		case MENU:
			panel.getGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			panel.getGame().getPlaying().mouseReleased(e);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
