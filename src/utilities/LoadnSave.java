package utilities;

import static utilities.Constants.AnimationConstants.IDLE;
import static utilities.Constants.AnimationConstants.getAnimationCount;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GameClass;

// Class responsible for loading and returning any images or other resources needed.
// Will only have static methods, no constructor.
public class LoadnSave {
	
	public static final String PLAYER_ATLAS_IMAGE = "player_sprites.png";	// file containing player atlas
	public static final String LEVEL_ATLAS_IMAGE = "outside_sprites.png";	// file containing level atlas
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_background.png";
	public static final String PAUSED_BUTTONS = "paused_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "background.jpg";
	public static final String PLAYING_BACKGROUND_IMG = "images.jpg";
	public static final String ENEMY_ATLAS_IMAGE = "enemy_sprite.png";
	public static final String HEALTH_BAR = "health_bar.png";


	private static int animCount = 0; 			// if animCount >= animSpeed go to next animation
	private static int animSpeed = 80; 		// FPS = 120 / 20 = 5 (change animation 5 times per second)
	private static int animIndex = 0;
	private static int playerAction = IDLE;
	private static BufferedImage[] anim;

	// loads the image atlas into a BufferedImage object and returns the image
	// param: all game images will be static final fields of this class. Only these
	//		  fields will be valid parameters.
	public static BufferedImage getSpriteAtlas(String filename) {
		BufferedImage atlas = null;
		InputStream is = LoadnSave.class.getResourceAsStream("/" + filename);	// open inputStream to read picture file
		
		try {
			atlas = ImageIO.read(is);		// load sprite atlas from image file, save into BufferedImage object
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
				try {
					is.close();				// close inputStream
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
		return atlas;						// return BufferedImage object
	}
	
	// return a 2-d array of the level data
	public static int[][] getLevelData() {
		int[][] lvlData = new int[GameClass.TILES_IN_HEIGHT][GameClass.TILES_IN_WIDTH];
		BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);
		
		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48) {
					value = 0;
				}
				lvlData[j][i] = value;
			}
		}
		return lvlData;
	}
	
	
	
	
	// x 47, y 75 
	public static void load(Graphics g) {
		BufferedImage charat = getSpriteAtlas(PLAYER_ATLAS_IMAGE);
		int x = 10;
		anim = new BufferedImage[2];
		for (int i = 0; i < anim.length; i++) {	
				anim[i] = charat.getSubimage(25 +(i*50), 375, 45, 77);			// loop through rows
		}
		//anim[1][1] = charat.getSubimage(70, 80, 55, 75);
		//g.drawImage(anim[i], 0, 0, null);
		//g.drawImage((charat.getSubimage(68, 75, 59, 70)), 0, 0, null);
	}
	
	public static void draw(Graphics g) {
		g.drawImage(anim[animIndex], 0, 0, 120, 150, null);
	}
	public static void updateAnimation() {
		animCount ++;
		if (animCount >= animSpeed) {
			animCount = 0;			// reset every for every new animation
			animIndex ++;			// go to next animation in animation array
			if (animIndex >= getAnimationCount(playerAction)) {
				animIndex = 0;		// go back to first animation
			}
		}
	}
}
