package achievements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import main.GamePanel;

public class Achievements {
	public final static int numTotalAchievements = 5;
	String filePath;
	File file;
	Scanner scanner;
	FileWriter writer;
	GamePanel gp;
	Thread producerThread;
	boolean isDisplaying;
	int displayingAchievement = -1; // KEEP THIS -1 (Means nothing is displaying)!
	public boolean[] achievementsArray; 
	public String[] achievementsTitles = {"Welcome", "The Noob", "Back to Square One", "The AFKiller", "Flawless"};
	public String[] achievementsDescriptions = {"Start your first game", "Complete your first game", "Win a game standing in starting spot", "Kill an AFK Player", 
												"Win without taking damage"};
	ReentrantLock isDisplayingLock 			= new ReentrantLock();
	ReentrantLock displayingAchievementLock = new ReentrantLock();
	ReentrantLock fileLock 					= new ReentrantLock();
	
	
	public Achievements(GamePanel gp){
		this.gp = gp;
		achievementsArray = new boolean[numTotalAchievements];
		filePath = "res/data/achievementsData.txt";
		fileLock.lock();
		file = new File(filePath);
		isDisplaying = false;
		
		try {
			// Populate achievementsArray and erase progress if the file is corrupt
			scanner = new Scanner(file);
			for (int i = 0; i < numTotalAchievements; i++) { 
				if (scanner.hasNextBoolean()) {
					achievementsArray[i] = scanner.nextBoolean();
				} else {
					eraseProgress();
					break;
				}
			}
			scanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		fileLock.unlock();
	}
	
	
	void writeProgress(String data) {
		fileLock.lock();
		try {
			file.delete();
			file.createNewFile();			
			writer = new FileWriter(filePath);
			writer.write(data);
			writer.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		fileLock.unlock();
	}
	
	
	private void eraseProgress() {
		fileLock.lock();
		try{
			file.delete();
			file.createNewFile();
			writer = new FileWriter(filePath);
			for (int i = 0; i < numTotalAchievements; i++) {
				writer.write("false ");
				achievementsArray[i] = false;
			}
			writer.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		fileLock.unlock();
	}
	
	public void startDisplaying() {
		isDisplayingLock.lock();
		isDisplaying = true;
		isDisplayingLock.unlock();
	}
	
	public void startDisplayingUnsafe() {
		isDisplaying = true;
	}
	
	public void stopDisplaying() {
		isDisplayingLock.lock();
		isDisplaying = false;
		isDisplayingLock.unlock();
	}
	
	public boolean getDisplaying() {
		isDisplayingLock.lock();
		boolean temp = isDisplaying;
		isDisplayingLock.unlock();
		return temp;
	}
	
	
	public int getDisplayingNum() {
		displayingAchievementLock.lock();
		int temp = displayingAchievement;
		displayingAchievementLock.unlock();
		return temp;
	}
	
	
	public void setDisplayingNum(int achievNum) {
		displayingAchievementLock.lock();
		displayingAchievement = achievNum;
		displayingAchievementLock.unlock();
	}
	
	
	public void startProducerThread() {
		AchievementsProducer producerObj = new AchievementsProducer(gp);
		producerThread = new Thread(producerObj);
		producerThread.start();
	}
	
	
	public String getAchievementDescription(int i) {
		return achievementsDescriptions[i];
	}
	
	
	public String getAchievementTitle(int i) {
		return achievementsTitles[i];
	}
	
	public boolean getHasUnlocked(int i) {
		synchronized(achievementsArray) {
			return achievementsArray[i];
		}
	}
}