package entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.Semaphore;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity implements Runnable{
	KeyHandler keyH;
	GamePanel gp;
	String name;
	Semaphore mySem;
	Semaphore nextSem;
	int playerNum;
	boolean gameOver;
	public int healthBarWidth;
	public int healthBarHeight;
	public int healthBarStart;
	int remainingWidth;
	
	public Player(String playerName, GamePanel gp, KeyHandler kh, Semaphore mySem, Semaphore nextSem) {
		this.name = playerName;
		this.gp = gp;
		this.keyH = kh;
		this.mySem = mySem;
		this.nextSem = nextSem;
		
		if (nextSem == gp.player2Sem) {
			playerNum = 1;
			healthBarStart = 0;
		}
		else {
			playerNum = 2;
			healthBarStart = gp.screenWidth / 2 + 75;
		}
	}
	
	public void setStartValues(int x, int y) {
		this.x = x;
		this.y = y;
		this.hp = 5;
		this.xVelocity = 5;
		
		healthBarWidth = gp.screenWidth / 2 - 75;
		healthBarHeight = 50;
	}
	
	public void setMovementAnimations(int frame) {
		// Code for movement animations here, probably load some frame from a file and render?
	}
	
	public void setAttackAnimations(int frame) {
		// Code for movement animations here, probably load some frame from a file and render?
	}
	
	public int getHealth() {
		return this.hp;
	}
	
	private void evaluateInput() {
		if (keyH.moveRight) {
			x = min(x + xVelocity, gp.screenWidth-width);
		}
		
		if (keyH.moveLeft) {
			x = max(x - xVelocity, 0);
		}
		
		//TODO add crouch/jump function
	}
	
	private int min(int i, int j){
		return (i < j) ? i : j;
	}
	
	private int max(int i, int j) {
		return (i > j) ? i : j;
	}
	
	private void cleanup() {
		
	}
	
	@Override
	public void run(){
		while (!gp.gameOver()) {
			try{
				mySem.acquire();
				//System.out.println(name);
				evaluateInput();
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} finally {
					nextSem.release();
				}
		}
	}
	
	public void draw(Graphics2D g2) {
		// Player 
		g2.setColor(Color.cyan);
		g2.fillRect(x, y, width, height);
		
		// Health bar
		g2.setColor(Color.black);
		g2.drawRect(healthBarStart, 0, healthBarWidth-1, healthBarHeight-1);
		
		int red, blue;
		if (hp >= 50) {
			blue = 255;
			red = (int)((255.0/50.0) * (100-hp));
		}
		else {
			blue = (int)(hp/100.0 * 255.0);
			red = 255;
		}
		remainingWidth = hp*(healthBarWidth-2)/100;
		System.out.println(red + " " + blue);
		g2.setColor(new Color(red, 0, blue));
		
		if (playerNum == 2)
			g2.fillRect(healthBarStart+1, 1, remainingWidth, healthBarHeight-2);
		else
			g2.fillRect(healthBarWidth-1-remainingWidth, 1, remainingWidth, healthBarHeight-2);
	}
}