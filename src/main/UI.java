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
			drawCharacterSelect();
			break;
		case GameOver:
			drawGameOver();
			break;
		case InGame:
			drawInGame();
			break;
		case MainMenu:
			drawMainMenu();
			break;
		case AchievementsMenu:
			drawAchievementsMenu();
			break;
		case Options:
			drawOptionsMenu();
			break;
		default:
			break;
		}
		
		// Checks for recent achievement notification to overlay
		if (gp.achievements.getIsDisplaying()) {
			drawAchievementOverlay();
		}
	}
	
	
	private void drawInGame(){
		g2.setColor(new Color(51, 56, 166));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
	}

	private void drawAchievementOverlay(){
		g2.setColor(new Color(33, 33, 33));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight/5);
		// ACHIEVEMENT TEXT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F)); // Bold and 96 Font Size
		String text = "ACHIEVEMENT GET";
		int x = centerX(text);
		int y = (int)(gp.screenHeight / 7.5);
		// GAMEOVER COLORS
		g2.setColor(Color.black);
		g2.drawString(text, x+3, y+3); // Offset for black shadow
		g2.setColor(Color.white);
		g2.drawString(text, x, y); // Render white text on top
	}

	private void drawGameOver(){
		// BG COLOR
		g2.setColor(new Color(33, 33, 33));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// GAMEOVER TEXT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F)); // Bold and 96 Font Size
		String text = gp.winner == null ? "NULL" : gp.winner;
		text.toUpperCase();
		int x = centerX(text);
		int y = (gp.screenHeight / 2) - (96/2);
		// GAMEOVER COLORS
		g2.setColor(Color.black);
		g2.drawString(text, x+3, y+3); // Offset for black shadow
		g2.setColor(Color.white);
		g2.drawString(text, x, y); // Render white text on top
		
		// PLAYER WINS
		text = "WINS!";
		x = centerX(text);
		y += 96 + 10;
		g2.setColor(Color.black);
		g2.drawString(text, x+3, y+3); // Offset for black shadow
		g2.setColor(Color.white);
		g2.drawString(text, x, y); // Render white text on top
	}

	private void drawCharacterSelect() {
		// BG COLOR
		g2.setColor(new Color(33, 33, 33));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE TEXT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F)); // Bold and 96 Font Size
		String text = "CHARACTER SELECT";
		int x = centerX(text);
		int y = (int) (gp.screenHeight / 7.5);
		// TITLE COLORS
		g2.setColor(Color.black);
		g2.drawString(text, x+3, y+3); // Offset for black shadow
		g2.setColor(Color.white);
		g2.drawString(text, x, y); // Render white text on top
	}
	
	
	private void drawOptionsMenu(){
		// BG COLOR
		g2.setColor(new Color(33, 33, 33));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE TEXT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F)); // Bold and 96 Font Size
		String text = "OPTIONS";
		int x = centerX(text);
		int y = (int) (gp.screenHeight / 7.5);
		// TITLE COLORS
		g2.setColor(Color.black);
		g2.drawString(text, x+3, y+3); // Offset for black shadow
		g2.setColor(Color.lightGray);
		g2.drawString(text, x, y); // Render white text on top
	}

	private void drawMainMenu() {
		// BG COLOR
		g2.setColor(new Color(25, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE TEXT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F)); // Bold and 96 Font Size
		String text = "FIGHT";
		int x = centerX(text);
		int y = (int) (gp.screenHeight / 7.5);
		
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
	
	
	private void drawAchievementsMenu(){
		// BG COLOR
		g2.setColor(new Color(33, 33, 33));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE TEXT
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F)); // Bold and 96 Font Size
		String text = "ACHIEVEMENTS";
		int x = centerX(text);
		int y = (int) (gp.screenHeight / 7.5);
		// TITLE COLORS
		g2.setColor(Color.black);
		g2.drawString(text, x+3, y+3); // Offset for black shadow
		g2.setColor(Color.decode("#FFC700"));
		g2.drawString(text, x, y); // Render white text on top
	}
	
	
	public int centerX(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
