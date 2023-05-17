package main;

import static main.GameClass.GAME_HEIGHT;
import static main.GameClass.GAME_WIDTH;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;


// The class for designing the contents in the game frame. The game panel can be thought of as
// the painting held inside the frame.
public class GamePanel extends JPanel {
	
	private MouseInputs mouse;			// single MouseInputs object that handles mouse inputs
	
	private GameClass game;
	
	// constructor 
	public GamePanel(GameClass game) {
		mouse = new MouseInputs(this);

		this.game = game;
		setPanelSize(1248, 672);
		
		// input listeners inside the game panel to receive inputs 
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
	}

	// sets the dimensions of the game panel
	public void setPanelSize(int width, int height) { 
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		revalidate();
	}	
	// Updates all things concerned with the game logic
	public void updateGame() {
		
	}
	
	// never called directly ,used by the JPanel constructor
	// Graphics is the tool to draw on the panel
	// updates all things concerned with game rendering/graphics via repaint()
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		requestFocus(true);
		// calls the GameClass render function, which call the player render function to update player graphics
		game.render(g);
	}

	// returns the game object
	public GameClass getGame() {
		return game;
	}
	
}
