package entity;
import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity implements Runnable{
	KeyHandler keyH;
	GamePanel gp;
	String name;
	int playerNum;
	
	public Player(String playerName, GamePanel gp, KeyHandler kh, int playerNum) {
		this.name = playerName;
		this.gp = gp;
		this.keyH = kh;
		this.playerNum = playerNum;
	}
	
	public void setStartValues(int x, int y) {
		this.x = x;
		this.y = y;
		this.hp = 100;
		this.xVelocity = 5;
		//this.yVelocity = 2;
	}
	
	public void setMovementAnimations(int frame) {
		// Code for movement animations here, probably load some frame from a file and render?
	}
	
	public void setAttackAnimations(int frame) {
		// Code for movement animations here, probably load some frame from a file and render?
	}
	
	private boolean gameOver() {
		// Checks if game is over or if there is a tie
		return false; // put some logic around returning true/false
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
		// Cleans up (Closes open resources) if the game is suddenly closed
	}
	
	@Override
	public void run(){
		while (!gameOver()) {
			if (playerNum == 1) {
				try{
					gp.player1Sem.acquire();
					//System.out.println(name);
					evaluateInput();
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					} finally {
						gp.player2Sem.release();
					}
			} 
			else if (playerNum == 2) {
				try{
					gp.player2Sem.acquire();
					//System.out.println(name);
					evaluateInput();
					
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} finally {
					gp.achieveSem.release();
				}				
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.white);
		
		g2.fillRect(x, y, width, height);
	}
}