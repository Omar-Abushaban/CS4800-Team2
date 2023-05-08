package entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;

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
	boolean isIdle, isAttacking, isJumping, isCrouching, lookingLeft;
	int jumpCounter = 0;
	public int attackCounter = 0;
	int[] attackHitbox = new int[4];
	int[] playerHitbox = new int[4];
	int floorY;
	
	public Player(String playerName, GamePanel gp, KeyHandler kh, Semaphore mySem, Semaphore nextSem) {
		this.name = playerName;
		this.gp = gp;
		this.keyH = kh;
		this.mySem = mySem;
		this.nextSem = nextSem;
		
		//diana
		getPlayer1Image();
		
		floorY = gp.screenHeight/2;
		
		if (nextSem == gp.player2Sem) {
			playerNum = 1;
			healthBarStart = 0;
			lookingLeft = false;
		}
		else {
			playerNum = 2;
			healthBarStart = gp.screenWidth / 2 + 75;
			lookingLeft = true;
		}
	}
	
	public void setStartValues(int x) {
		this.x = x;
		this.y = floorY;
		this.hp = 100;
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
	
	public void takeDamage() {
		hp -= 10;
	}
	
	private void evaluateInput() {
		
		// Moving/attacking
		if (keyH.moveRight) {
			x = min(x + xVelocity, gp.screenWidth-width);
			lookingLeft = false;
		}
		if (keyH.moveLeft) {
			x = max(x - xVelocity, 0);
			lookingLeft = true;
		}
		if (keyH.jump) {
			if (y == floorY) {
				isJumping = true;
			}
		}
		if (keyH.attack) {
			isAttacking = true;
			if (lookingLeft) {
				attackHitbox[0] = max(0, x-100);
				attackHitbox[1] = y+height/4;
				attackHitbox[2] = min(100, x);
				attackHitbox[3] = 40;
			} else {
				attackHitbox[0] = x+width;
				attackHitbox[1] = y+height/4;
				attackHitbox[2] = min(gp.screenWidth-width-x, 100);
				attackHitbox[3] = 40;				
			}
		}
		
		// Gravity
		y = min(y+10, floorY);
		
	}
	
	private void attack() {
		attackCounter += 1;
		
		if (attackCounter == 30) {
			isAttacking = false;
			attackCounter = 0;
		}
	}

	public int[] getAttackHitbox() {
		return attackHitbox;
	}
	
	public int[] getHitbox() {
		playerHitbox[0] = x;
		playerHitbox[1] = y;
		playerHitbox[2] = width;
		playerHitbox[3] = height;
		
		return playerHitbox;
	}
	
	private void performActions() {
		if (isJumping)
			jump();
		if (isCrouching)
			crouch();
		if (isAttacking())
			attack();
	}
	
	private void jump() {
		y -= 50;
		
		jumpCounter += 1;
		if (jumpCounter == 6) {
			isJumping = false;
			jumpCounter = 0;
		}
	}
	
	private void crouch() {
		// change hitboxes/animation?/etc
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
				evaluateInput();
				performActions();
				
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} finally {
					nextSem.release();
				}
		}
	}
	
	private void drawAttackHitbox(Graphics2D g2) {
		g2.setColor(Color.red);
		g2.fillRect(attackHitbox[0], attackHitbox[1], attackHitbox[2], attackHitbox[3]);
	}
	
	public void draw(Graphics2D g2) {
		// Player 
		g2.setColor(Color.cyan);
		g2.fillRect(x, y, width, height);
		
		// Attack DEBUG
//		if (isAttacking() && attackCounter <= 6) {
//			drawAttackHitbox(g2);
//		}
		
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
		g2.setColor(new Color(red, 0, blue));
		
		if (playerNum == 2)
			g2.fillRect(healthBarStart+1, 1, remainingWidth, healthBarHeight-2);
		else
			g2.fillRect(healthBarWidth-1-remainingWidth, 1, remainingWidth, healthBarHeight-2);
	}
	
	//diana
	public void getPlayer1Image()
	{
		try{
			attack1 = ImageIO.read(getClass().getResourceAsStream("/player1/Attack_1.png"));
			attack2 = ImageIO.read(getClass().getResourceAsStream("/player1/Attack_2.png"));
			attack3 = ImageIO.read(getClass().getResourceAsStream("/player1/Attack_3.png"));
			dead = ImageIO.read(getClass().getResourceAsStream("/player1/Dead.png"));
			hurt = ImageIO.read(getClass().getResourceAsStream("/player1/Hurt.png"));
			idle = ImageIO.read(getClass().getResourceAsStream("/player1/Idle.png"));
			jump = ImageIO.read(getClass().getResourceAsStream("/player1/Jump.png"));
			protection = ImageIO.read(getClass().getResourceAsStream("/player1/Protection.png"));
			run = ImageIO.read(getClass().getResourceAsStream("/player1/Run.png"));
			walk = ImageIO.read(getClass().getResourceAsStream("/player1/Walk1.png"));
			//attack1 = ImageIO.read(getClass().getResourceAsStream("/player1/attack_1.png"));
		}
		catch(IOException e) {
		e.printStackTrace();
	}

	} //end getPlayer1Image

	public boolean isAttacking(){
		return isAttacking;
	}

	public String getUserName(){
		return name;
	}
}
