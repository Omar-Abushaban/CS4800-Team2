package entity;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity implements Runnable{
	KeyHandler keyH;
	GamePanel gp;
	String name;
	
	public Player(String playerName, GamePanel gp, KeyHandler kh) {
		this.name = playerName;
		this.gp = gp;
		this.keyH = kh;
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
		// Evaluates input based on keyHandler variables
	}
	
	private void cleanup() {
		// Cleans up (Closes open resources) if the game is suddenly closed
	}
	
	@Override
	public void run(){
		while (!gameOver()) {
			// User input will be checked for
			evaluateInput();
			
		}
	}

	public void update(){
		// TODO Auto-generated method stub
		
	}
	
	public void render(Graphics2D g2) {
		
	}
}