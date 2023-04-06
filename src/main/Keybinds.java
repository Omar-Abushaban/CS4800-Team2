package main;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Keybinds{
	int[] keybinds = new int[5]; // array of KeyEvent.VK_[key] values (aka ints)
	Hashtable<String, Integer> actionToIndex = new Hashtable<String, Integer>();
	String playername;
	String filePath;
	File file;
	Scanner scanner;
	FileWriter writer;
	
	public Keybinds(String playerName){
		filePath = "data/keybind_" + playerName + ".txt";
		file = new File(filePath);
		if (file.exists())
			loadKeybinds(playerName);
		else {
			generateDefaultKeybinds();
			saveKeybinds();
		}
		initializeActionToIndex();
	}
	
	private void initializeActionToIndex() {
		actionToIndex.put("moveLeft", 0);
		actionToIndex.put("moveRight", 1);
		actionToIndex.put("crouch", 2);
		actionToIndex.put("jump", 3);
		
		actionToIndex.put("attack", 4);
	}
	
	public void generateDefaultKeybinds() {
		//FIXME BAD DEFAULT KEYBINDS, CHANGE IF YOU KNOW WHAT FIGHTING GAME KEYBINDS ARE LIKE LOL
		keybinds[0] = KeyEvent.VK_A; // moveLeft
		keybinds[1] = KeyEvent.VK_D; // moveRight
		keybinds[2] = KeyEvent.VK_S; // crouch
		keybinds[3] = KeyEvent.VK_W; // jump
		
		keybinds[4] = KeyEvent.VK_R; // attack
	}
	
	public void loadKeybinds(String playerName) {
		try{
			scanner = new Scanner(file);
			for (int i = 0; i < keybinds.length; i++) { 
				if (scanner.hasNextInt()) {
					keybinds[i] = scanner.nextInt();
				} else {
					generateDefaultKeybinds();
					saveKeybinds();
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int getIndex(String action) {
		if (actionToIndex.get(action) != null)
			return actionToIndex.get(action);
		
		return -1;
	}
	
	public int getKeybind(String action) {
		int i = getIndex(action);
		if (i != -1) {
			return keybinds[i];
		}
		
		return -1;
	}
	
	private void saveKeybinds() {
		file.delete();
		try{
			file.createNewFile();
			writer = new FileWriter(filePath);
			for (int i = 0; i < keybinds.length; i++) {
				String data = Integer.toString(keybinds[i]) + " ";
				writer.write(data);
			}
			writer.close();
		} catch (IOException e){
			e.printStackTrace();
		}			
	}
	
	public void setKeybind(String action, int keyCode) {
		int actionIndex = getIndex(action);
		if (actionIndex != -1) {
			keybinds[actionIndex] = keyCode;			
		}
	}
}
