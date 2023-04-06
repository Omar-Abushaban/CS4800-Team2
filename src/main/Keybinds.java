package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Keybinds{
	final int numKeybinds = 5; //TODO Change me if we add more keybinds!
	String[] keybinds = new String[5];
	Hashtable<String, Integer> actionToIndex = new Hashtable<String, Integer>();
	String playername;
	String filePath;
	File file;
	Scanner scanner;
	FileWriter writer;
	
	public Keybinds(String playerName){
		filePath = "data/keybind_" + playerName+ ".txt";
		file = new File(filePath);
		if (file.exists())
			loadKeybinds(playerName);
		else {
			generateDefaultKeybinds();
			saveKeybinds();
		}
	}
	
	public void loadKeybinds(String playerName) {
		
	}
	
	private int getActionIndex(String action) {
		return actionToIndex.get(action);
	}
	
	private void saveKeybinds() {
		file.delete();
		try{
			file.createNewFile();
			writer = new FileWriter(filePath);
			for (int i = 0; i < numKeybinds; i++) {
				String data = "";
				writer.write(data);
			}
			writer.close();
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public void generateDefaultKeybinds() {
		//FIXME BAD DEFAULT KEYBINDS, CHANGE IF YOU KNOW WHAT FIGHTING GAME KEYBINDS ARE LIKE LOL
		
	}
	
	public boolean setKeybindUnsafe(String action, int keyCode) {
		
		return true;
	}
}
