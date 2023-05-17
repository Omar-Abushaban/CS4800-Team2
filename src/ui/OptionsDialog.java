package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import gamestates.GameStates;
import main.GameWindow;
import main.MusicManager;


public class OptionsDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MusicManager musicManager;
    private final GameWindow gameWindow;

    public OptionsDialog(MusicManager musicManager, GameWindow gameWindow) {
        this.musicManager = musicManager;
        this.gameWindow = gameWindow;

        setTitle("Options");
        setModal(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Change game state to MENU when window is closing
                GameStates.state = GameStates.MENU;
            }
        });

        
        // Volume control
        add(new JLabel("Music Volume:"));
        JSlider volumeSlider = new JSlider(0, 100, 100);
        volumeSlider.addChangeListener(e -> musicManager.setVolume(volumeSlider.getValue() / 100f));
        add(volumeSlider);
        


        pack();
        setLocationRelativeTo(null);
    }
}