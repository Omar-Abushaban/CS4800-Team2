package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import utilities.LoadnSave;

import static utilities.Constants.AnimationConstants.*;
import static utilities.Constants.MovementConstants.*;
import static main.GameClass.GAME_HEIGHT;
import static main.GameClass.GAME_WIDTH;


// The class for designing the contents in the game frame. The game panel can be thought of as
// the painting held inside the frame.
public class GamePanel extends JPanel {
	
	private MouseInputs mouse;			// single MouseInputs object that handles mouse inputs
	
	private GameClass game;
	
	// constructor 
	public GamePanel(GameClass game) {
		mouse = new MouseInputs(this);

		this.game = game;
		setPanelSize();
		
		// input listeners inside the game panel to receive inputs 
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
	}

	// sets the dimensions of the game panel
	public void setPanelSize() { 
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);		// sets the size of this component (our panel)
		System.out.println("size:" + GAME_WIDTH + ": " + GAME_HEIGHT);
		
	}
	
	// Updates all things concerned with the game logic
	public void updateGame() {
		
	}
	
	// never called directly ,used by the JPanel constructor
	// Graphics is the tool to draw on the panel
	// updates all things concerned with game rendering/graphics via repaint()
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
			
		// calls the GameClass render function, which call the player render function to update player graphics
		game.render(g);
	}

	// returns the game object
	public GameClass getGame() {
		return game;
	}
	
}
