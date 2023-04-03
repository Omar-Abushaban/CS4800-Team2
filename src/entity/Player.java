package entity;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Hashtable;

public class Player extends FightingGameEntity implements Runnable{
	Hashtable<String, Integer> keybinds = new Hashtable<String, Integer>();
	
	public Player(int playerNumber) {
		// Check if there exist saved KeyBinds and load if so
		File f = new File("keybind_" + Integer.toString(playerNumber));
		if (f.isFile()) {
			// Code to iterate over file until each delimiter, and load key value into corresponding Hashmap key
		}
	}
	
	public boolean setKeybind(String action, int keyCode) {
		// Return false if already binded key or action is not valid
		if (!keybinds.containsKey(action) || keybinds.containsValue(keyCode)) {
			return false;
		}
		else {
			keybinds.put(action, keyCode);
			return true;
		}
	}

	public Hashtable<String, Integer> getKeybinds(){
		return keybinds;
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
	
	private void updateGameState() {
		// Updates the positions and status of both players based on their inputs and the game rules
	}
	
	private void checkForInput() {
		// Checks for input from both players and updates their respective inputs
	}
	
	private void synchronizePlayers() {
		// Ensures that both players are updating the game state simultaneously
	}
	
	private void cleanup() {
		// Cleans up (Closes open resources) if the game is suddenly closed
	}
	
	@Override
	public void run(){
		while (!gameOver()) {
			// Game state will be updated
			updateGameState();
			
			// User input will be checked for
			checkForInput();
			
			// Players actions/movements will be synchronized
			synchronizePlayers();
		}
	}

	public void update(){
		// TODO Auto-generated method stub
		
	}
	
	public void render(Graphics2D g2) {
		
	}
}