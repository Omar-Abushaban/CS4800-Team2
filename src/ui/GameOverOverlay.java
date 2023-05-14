package ui;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gamestates.GameStates;
import gamestates.Playing;
import main.GameClass;
public class GameOverOverlay {

	private Playing playing;
	String text;
	int textWidth;
	
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
	}

	public void draw(Graphics g, String winner) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);

		g.setColor(Color.white);
		g.setFont(g.getFont().deriveFont(32F));
		int textHeight = 0;
		
		text = "Game Over";
		textWidth = g.getFontMetrics().stringWidth(text);
		textHeight += 150;
		g.drawString(text, (GameClass.GAME_WIDTH - textWidth) / 2, textHeight);
		
		text = winner + " wins";
		textWidth = g.getFontMetrics().stringWidth(text);
		textHeight += 75;
		g.drawString(text, (GameClass.GAME_WIDTH - textWidth) / 2, textHeight);
		
		text = "Press esc to enter Main Menu!";
		textWidth = g.getFontMetrics().stringWidth(text);
		textHeight += 100;
		g.drawString(text, (GameClass.GAME_WIDTH - textWidth) / 2, textHeight);

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.resetAll();
			GameStates.state = GameStates.MENU;
		}
	}
}
