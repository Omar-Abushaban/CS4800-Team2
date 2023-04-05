package achievements;

import main.GamePanel;
import main.GamePanel.GameState;

public class AchievementsProducer implements Runnable {
	GamePanel gp;
	boolean[] achievements;
	
	public AchievementsProducer(GamePanel gp){
		this.gp = gp;
	}
	
	@Override
	public void run(){
		while(true) { // RUNS IN LOOP, MAY ADD SEMAPHORES/ETC FOR CONTROL FLOW
			if (gp.gameState == GameState.InGame) {
				checkForInGameAchievements();
			} else if (gp.gameState == GameState.GameOver) {
				checkForEndGameAchievements();
			}
		}
	}
	
	private void checkForEndGameAchievements(){
		synchronized(gp.achievements.achievementsArray) {
			achievements = gp.achievements.achievementsArray;
		}
		
		if (!achievements[1]) {
			// Complete your first game!
			achievementGet(1);
			AchievementsConsumer achievement0 = new AchievementsConsumer(gp, 1);
			achievement0.start();
		}
		if (!achievements[2]) {
			// Win a game standing in starting spot
		}
		if (!achievements[3]) {
			// Kill a player who hasn't moved/made any inputs all game
		}
		if (!achievements[4]) {
			// Win without taking damage
		}
	}

	private void checkForInGameAchievements(){
		synchronized(gp.achievements.achievementsArray){
			achievements = gp.achievements.achievementsArray;
		}
			if (!achievements[0]) {
				// Start a game for the first time!
				achievementGet(0);
				AchievementsConsumer achievement4 = new AchievementsConsumer(gp, 0);
				achievement4.start();
			}			
	}

	private void achievementGet(int i) {
		String data = "";
		
		synchronized(gp.achievements.achievementsArray){
			gp.achievements.achievementsArray[i] = true;
			
			for(int j = 0; j < gp.achievements.numTotalAchievements; j++) {
				if (gp.achievements.achievementsArray[j] == true)
					data += "true ";
				else
					data += "false ";
			}
		}
		
		gp.achievements.writeProgress(data);
	}
}
