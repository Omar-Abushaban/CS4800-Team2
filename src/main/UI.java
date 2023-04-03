package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class UI{
	
	GamePanel gp;
	Graphics2D g2;
	public boolean gameOver = false;
	public int menuIterator = 0;
	final int mainMenuItems = 4;
	
	
	public UI(GamePanel gamePanel){
		this.gp = gamePanel;
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setColor(Color.white);
		
		switch(gp.gameState) {
		case CharacterSelect:
			break;
		case GameOver:
			break;
		case InGame:
			break;
		case MainMenu:
			drawMainMenu();
			break;
		default:
			break;
		}
	}
	
	public void drawMainMenu() {
		// BG COLOR
		g2.setColor(new Color(25, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE TEXT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F)); // Bold and 96 Font Size
		String text = "FIGHT";
		int x = centerX(text);
		int y = (int)(gp.screenHeight / 4.75);
		
		g2.setColor(Color.black);
		g2.drawString(text, x+3, y+3); // Offset for black shadow
		g2.setColor(Color.white);
		g2.drawString(text, x, y); // Render white text on top
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
		Point[] mainMenuXYs = new Point[mainMenuItems]; // Start/options/achievements/exit is 4
		
		text = "START";
		x = centerX(text);
		y += gp.screenHeight / 2.8;
		mainMenuXYs[0] = new Point(x, y);
		g2.drawString(text, x, y);
		
		text = "OPTIONS";
		x = centerX(text);
		y += gp.screenHeight / 10;
		mainMenuXYs[1] = new Point(x, y);
		g2.drawString(text, x, y);
		
		text = "ACHIEVEMENTS";
		x = centerX(text);
		y += gp.screenHeight / 10;
		mainMenuXYs[2] = new Point(x, y);
		g2.drawString(text, x, y);
		
		text = "EXIT";
		x = centerX(text);
		y += gp.screenHeight / 10;
		mainMenuXYs[3] = new Point(x, y);
		g2.drawString(text, x, y);
		
		
		// DRAW "Selection Icon"
		g2.drawString(">", (int)mainMenuXYs[menuIterator].getX()-30, (int)mainMenuXYs[menuIterator].getY());
	}
	public int centerX(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
