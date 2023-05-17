package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

// The class for designing the game frame. The frame can be though as the outer frame that
// holds the painting.
public class GameWindow {
	
	// the frame for the game GIU
	private JFrame frame;
	
	// constructor creates game frame
	// param: GamePanel object  
	
	public GameWindow(GamePanel panel) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);	// terminate program upon clicking exit button
		frame.add(panel);					// set our panel inside our frame
		frame.setResizable(false);			// not resizable by user
		frame.pack();						// adjust frame to panel size
		frame.setLocationRelativeTo(null);	// spawn game window in center of screen


		frame.setVisible(true);
		
		frame.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {	// game window has gained focus
				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {	// game window has lost focus
				panel.getGame().windowFocusLost();
			}
			
		});
		
	}

	public void setSize(int parseInt, int parseInt2) {
		// TODO Auto-generated method stub
		
	}
}
