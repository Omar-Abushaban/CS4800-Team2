package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilities.LoadnSave;
import static utilities.Constants.UI.URMButtons.*;

// represents buttons in paused menu screen
public class URMButtons extends PauseButton {
	
	private BufferedImage[] imgs;	// array to hold button images
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;

	public URMButtons(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadImgs();
	}
	
	// load images
	private void loadImgs() {
		BufferedImage temp = LoadnSave.getSpriteAtlas(LoadnSave.PAUSED_BUTTONS);
		imgs = new BufferedImage[3];
		for(int i = 0; i < imgs.length; i++) {
			imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE , rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
			
		}
	}

	public void update() {
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed)
			index = 2;
	}
	
	// draw the buttons
	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
	}
	
	// reset mouse booleans
	public void resetBooleans() {
		mouseOver = false;
		mousePressed = false;
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
}
