package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.GameStates;
import utilities.LoadnSave;
import static utilities.Constants.UI.Buttons.*;

// represents a button in the menu
public class MenuButton {
	
	private int xPos, yPos, rowIndex, index;
	private int xOffsetCenter = B_WIDTH/2;
	private GameStates state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	
	public MenuButton(int xPos, int yPos, int rowIndex, GameStates state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImgs();
		initBounds();
		
	}
	
	// initialize bounds of button
	private void initBounds() {
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
	}
	
	// return button bounds
	public Rectangle getBounds() {
		return bounds;
	}

	// load menu button images
	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = LoadnSave.getSpriteAtlas(LoadnSave.MENU_BUTTONS);
		for(int i = 0; i < imgs.length; i ++)
			imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
	}
	
	// draw button
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
	}
	
	// update button depending on mouse event
	public void update() {
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed)
			index = 2;
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	// when button is clicked, apply game state
	public void applyGameState() {
		GameStates.state = state;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	
}
