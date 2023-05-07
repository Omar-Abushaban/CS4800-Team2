package entity;

//diana: putting my name just to keep tabs on 
//what I put in case I need to go back and change something
import java.awt.image.BufferedImage;


public class Entity{
	int x, y;
	int hp;
	int xVelocity, yVelocity;
	
	public int width = 100;
	public int height = 200;
	
	//diana: for player1 character and actions
	public BufferedImage attack1, attack2, attack3, dead, hurt, idle, jump, protection, run, walk;
	public String direction;
}
