package levels;

// holds the 2-d of data for the specific level
public class Level {

	private int[][] lvlData;
	
	public Level(int[][] lvlData) {
		this.lvlData = lvlData;
	}
	
	// returns a specific tile in the level sprite array
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	// return the 2d array holding the level's data
	public int[][] getLevelData(){
		return lvlData;
	}
}
