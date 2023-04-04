package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import main.GamePanel.GameState;

public class Achievements implements Runnable {
	final int numTotalAchievements = 5;
	String filePath;
	File file;
	Scanner scanner;
	FileWriter writer;
	boolean[] achievementsArray;
	GamePanel gp;
	Thread achievementsThread;
	boolean isDisplaying;
	
	public Achievements(GamePanel gp){
		this.gp = gp;
		achievementsArray = new boolean[numTotalAchievements];
		filePath = "data/achievementsData.txt";
		file = new File(filePath);
		isDisplaying = false;
		
		try {
			// Populate achievementsArray and erase progress if the file is corrupt
			scanner = new Scanner(file);
			for (int i = 0; i < numTotalAchievements; i++) {
				if (scanner.hasNextInt()) {
					int cur = scanner.nextInt();
					if (cur == 0 || cur == 1) {
						achievementsArray[i] = cur == 1 ? true : false;
					}
					else {
						eraseProgress();
					}
				} else {
					eraseProgress();
				}
			}
			scanner.close();
			startThread();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	
	public void startThread() {
		achievementsThread = new Thread(this);
		achievementsThread.start();
	}
	
	
	private void eraseProgress() {
		try{
			file.delete();
			file.createNewFile();
			writer = new FileWriter(filePath);
			writer.write("00000");
			writer.close();
			for (int i = 0; i < numTotalAchievements; i++) {
				achievementsArray[i] = false;
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	public void checkForMidGameAchievements() {
		if (!achievementsArray[4]) {
			// Start a game for the first time!
			achievementGet(4);
			achievementsArray[4] = true;
		}
	}
	
	
	public void checkForEndGameAchievements() {
		if (!achievementsArray[0]) {
			// Complete your first game!
			achievementGet(0);
		}
		if (!achievementsArray[1]) {
			// Win a game standing in starting spot
		}
		if (!achievementsArray[2]) {
			// Kill a player who hasn't moved/made any inputs all game
		}
		if (!achievementsArray[3]) {
			// Win without taking damage
		}
	}
	
	
	public boolean getIsDisplaying() {
		return isDisplaying;
	}
	
	
	private void achievementGet(int i) {
		isDisplaying = true;
		achievementsArray[i] = true;
		try{
			Thread.sleep(5000);
		} catch (InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isDisplaying = false;
	}


	@Override
	public void run(){
		System.out.println(achievementsThread);
		while(achievementsThread != null) {
			if (gp.gameState == GameState.InGame) {
				checkForMidGameAchievements();
			}
			else if (gp.gameState == GameState.GameOver) {
				checkForEndGameAchievements();
			}			
		}
	}
}