package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.GameClass;
import utilities.LoadnSave;


// Handles drawing the level
public class LevelManager {

	private GameClass game;
	private BufferedImage[] levelSprite;	// array that holds the tiles of level
	private Level level;					// level to be loaded
	
	public LevelManager(GameClass game) {
		this.game = game;
		importOutsideSprites();
		level = new Level(LoadnSave.getLevelData());
	}
	
	// load level tiles into array 
	private void importOutsideSprites() {
		BufferedImage img = LoadnSave.getSpriteAtlas(LoadnSave.LEVEL_ATLAS_IMAGE);
		levelSprite = new BufferedImage[48];
		
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 12; i++) {
				int index = j*12 + i;
				levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
			}
		}
	}

	// draw the level
	public void draw (Graphics g) {
		
		for (int j = 0; j < GameClass.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < GameClass.TILES_IN_WIDTH; i++) {
				int index = level.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], GameClass.TILES_SIZE*i , GameClass.TILES_SIZE*j, GameClass.TILES_SIZE, GameClass.TILES_SIZE, null);	// draw level
			}
		}
	}
	
	public void update() {
		
	}
	
	// returns the current level the manager is handling
	public Level getCurrentLevel() {
		return level;
	}
}
