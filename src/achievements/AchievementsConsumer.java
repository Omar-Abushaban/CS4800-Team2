package achievements;

import main.GamePanel;

public class AchievementsConsumer extends Thread {
	GamePanel gp;
	int achievNum;
	
	AchievementsConsumer(GamePanel gp, int i){
		this.gp = gp;
		this.achievNum = i;
	}
	
	@Override
	public void run(){ // RUNS ONCE PER ACHIEVEMENT, DONT MAKE LOOP
		gp.achievements.isDisplayingLock.lock();
		gp.achievements.startDisplayingUnsafe();
		gp.achievements.displayingAchievementLock.lock();
		gp.achievements.setDisplayingNum(achievNum);
		gp.achievements.displayingAchievementLock.unlock();
		
		try{
			Thread.sleep(2500);
		} catch (InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gp.achievements.displayingAchievementLock.lock();
		gp.achievements.setDisplayingNum(-1); // "No longer displaying any achievement so -1"
		gp.achievements.displayingAchievementLock.unlock();
		
		gp.achievements.isDisplayingLock.unlock();
	}

}
