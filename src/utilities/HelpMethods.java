package utilities;

import java.awt.geom.Rectangle2D;

import main.GameClass;

public class HelpMethods {
	
	// returns if the player position is legal
	public static boolean canMoveHere(float x, float y, float width, float height, int[][]lvlData) {
		
		if (!isSolid(x, y, lvlData))
			if(!isSolid(x + width, y + height, lvlData))
				if(!isSolid(x + width, y, lvlData))
					if(!isSolid(x, y + height, lvlData))
						return true;
		return false;
	}
	
	// checks if the player position is inside the game window and 
	// if the player is touching a tile
	private static boolean isSolid(float x, float y, int[][]lvlData) {
		if (x < 0 || x >= GameClass.GAME_WIDTH)	// cannot move here
			return true;
		if (y < 0 || y >= GameClass.GAME_HEIGHT)	// cannot move here
			return true;
		
		float xIndex = x / GameClass.TILES_SIZE;
		float yIndex = y / GameClass.TILES_SIZE;
		
		int value = lvlData[(int) yIndex][(int) xIndex];
		
		if (value >= 48 || value < 0 || value != 11)	// cannot move here
			return true;
		return false;			
	}
	
	// checks if player is touching walls
	public static float getEntityXPosNextToWall (Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / GameClass.TILES_SIZE);
		if (xSpeed > 0) {
			//right
			int tileXPos = currentTile * GameClass.TILES_SIZE;
			int xOffset = (int)(GameClass.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		}
		else {
			// left
			return currentTile * GameClass.TILES_SIZE;
		}
	}
	
	// checks vertical player position
	public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / GameClass.TILES_SIZE);
		if (airSpeed > 0) {
			// falling
			int tileYPos = currentTile * GameClass.TILES_SIZE;
			int yOffset = (int)(GameClass.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		}
		else {
			// jumping
			return currentTile * GameClass.TILES_SIZE;
		}
	}
	
	public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) 
			if(!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) 
				return false;
		return true;
	}
		
}
