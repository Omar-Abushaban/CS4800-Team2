package entities;

import static utilities.Constants.EnemyConstants.ATTACK;
import static utilities.Constants.EnemyConstants.DEAD;
import static utilities.Constants.EnemyConstants.IDLE;
import static utilities.Constants.EnemyConstants.RUNNING;
import static utilities.Constants.EnemyConstants.getAnimationCount;
import static utilities.HelpMethods.canMoveHere;
import static utilities.HelpMethods.getEntityXPosNextToWall;
import static utilities.HelpMethods.getEntityYPosUnderRoofOrAboveFloor;
import static utilities.HelpMethods.isEntityOnFloor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.GameClass;
import utilities.LoadnSave;

// represents the enemy entity
public class Enemy extends Entity implements Runnable{
	
	private BufferedImage[][] animations; // 2-D array to hold all character animations
	private int animCount = 0; 			// if animCount >= animSpeed go to next animation
	private int animSpeed = 25; 		// FPS = 120 / 20 = 5 (change animation 5 times per second)
	private int animIndex = 0;			// the current index in animation array
	
	// movement data
	private int playerAction = IDLE;	// determines the row in the animation array based on user input
	private boolean moving = false, attacking = false;		// determines whether or not the player is moving/attacking
	private boolean left, up, right, down, jump; 	// check which direction the player is moving
	private float playerSpeed = 1.0f * GameClass.SCALE;	// speed of player movement when character moves directions
	
	// collision data
	private int[][] lvlData;
	private float xDrawOffset = 21 * GameClass.SCALE;
	private float yDrawOffset = 4 * GameClass.SCALE;
	
	// gravity data
	private float airSpeed = 0f;
	private float gravity = 0.04f * GameClass.SCALE;
	private float jumpSpeed = -2.25f * GameClass.SCALE;
	private float fallSpeedAfterCollision = 0.5f * GameClass.SCALE;
	private boolean inAir = false;
	
	// Health Bar UI
	private BufferedImage statusBarImg;
	private int statusBarWidth = (int) (192 * GameClass.SCALE);
	private int statusBarHeight = (int) (58 * GameClass.SCALE);
	private int statusBarX = (int) (600 * GameClass.SCALE);
	private int statusBarY = (int) (10 * GameClass.SCALE);
	private int healthBarWidth = (int) (150 * GameClass.SCALE);
	private int healthBarHeight = (int) (4 * GameClass.SCALE);
	private int healthBarXStart = (int) (34 * GameClass.SCALE);
	private int healthBarYStart = (int) (14 * GameClass.SCALE);
	private int maxHealth = 100;
	private int currentHealth = 100;
	private int healthWidth = healthBarWidth;
	
	// AttackBox data
	private Rectangle2D.Float attackBox;
	private int flipX = 0;
	private int flipW = 1;
	private boolean attackChecked;
	private Playing playing;
	
	// enemy images data
	public static int CRABBY_WIDTH_DEFAULT = 72;
	public static int CRABBY_HEIGHT_DEFAULT = 32;
	public static int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * GameClass.SCALE);
	public static int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * GameClass.SCALE);

	// constructor
	public Enemy(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadAnimations();
		initHitBox(x, y, (int)(20 * GameClass.SCALE), (int)(27 * GameClass.SCALE));
		initAttackBox();
	}

	// create player attack box
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(230, 100, (int) (25 * GameClass.SCALE), (int) (25 * GameClass.SCALE));
		}
	
	// Updates player in-game logic
	public void update() {
		if (hitbox.y + hitbox.height > 670)
			currentHealth = 0;
		updateHealthBar();
		if (currentHealth <= 0) {	// a player had died
			setAnimation();
			updateAnimation();
			playing.setGameOver(true);
			return;
		}
		updateAttackBox();
		updatePosition();
		if(attacking)
			checkAttack();
		updateAnimation();
		setAnimation();
		}
		
	// if enemy attacks player
	private void checkAttack() {
		if (attackChecked || animIndex != 1)
			return;
		attackChecked = true;
		playing.checkPlayerHit(attackBox);
	}

	// update player attack box
	private void updateAttackBox() {
		if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (GameClass.SCALE * 10);
		else if (left)
			attackBox.x = hitbox.x - hitbox.width - (int) (GameClass.SCALE * 10);
		
		attackBox.y = hitbox.y + (GameClass.SCALE * 10);
	}

	// update player health bar status
	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	// updates all things concerned with player rendering/graphics
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][animIndex], (int) (hitbox.x - xDrawOffset) + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
		//call drawHitBox() in Entity class
		//drawHitBox(g);
		//drawAttackBox(g);
		drawHealthBar(g);
	}
		
	// draw player attack box
	private void drawAttackBox(Graphics g) {
		g.setColor(Color.red);
		g.drawRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	// draw health bar
	private void drawHealthBar(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	// updates animation by looping through animation array the specified
	// times per second.
	public void updateAnimation() {
		animCount ++;
		if (animCount >= animSpeed) {
			animCount = 0;			// reset every for every new animation
			animIndex ++;			// go to next animation in animation array
			if (animIndex >= getAnimationCount(playerAction)) {
				animIndex = 0;		// go back to first animation
				attacking = false;	// if attacking, stop animation
				attackChecked = false;
			}
		}
	}
		
	// Sets the character's current animation based on whether it is moving or not.
	// The character is moving if the user presses any of the control keys
	private void setAnimation() {
		int startAnimation = playerAction;
			
		if(moving) 		// if user moves character set running animation
			playerAction = RUNNING;
		else 			// else set idle animation
			playerAction = IDLE;
			
		if (inAir) {
			if(airSpeed < 0)
				playerAction = RUNNING;
			else
				playerAction = IDLE;
		}
			
		if (attacking) {	// if user clicks left mouse set attacking animation
			playerAction = ATTACK;
			if (startAnimation != ATTACK) {
				animIndex = 1;
				animCount = 0;
				return;
			}
		}
		
		if(currentHealth <= 0)
			playerAction = DEAD;
			
		if (startAnimation != playerAction) {	// if user input requires new animation
			resetAnimationCount();
		}
	}
		
	// resets animation data for each new animation
	public void resetAnimationCount() {
		animCount = 0;
		animIndex = 0;
	}
		
	// Updates the character's x and y position in game panel based on user control inputs
	private void updatePosition() {
		moving = false;				// not moving by default
			
		if (jump) {					// if jumping
			jump();
		}
			
		if (!inAir)
			if ((!left && !right ) || (right && left))	// if not moving, leave method
				return;
			
		float xSpeed = 0 ;
				
		// if pressing left and right buttons do not move in x direction
		if (left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) {
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
		}
			
		if(!inAir) 
			if (!isEntityOnFloor(hitbox, lvlData)) 
				inAir = true;
			
		if(inAir) {
				
			if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}
			else {
				hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		}
		else {
			updateXPos(xSpeed);
		}
			
		moving = true;
	}
		
	private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if(canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		}
		else {
			hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
		}
	}
		
	// change player health
	public void changeHealth(int value) {
		currentHealth += value;

		if (currentHealth <= 0)
			currentHealth = 0;
			// game over
		else if (currentHealth >= maxHealth)
				currentHealth = maxHealth;
	}

	// loads the character animations 2-D array for all player animations
	private void loadAnimations() {
			
		BufferedImage spriteAtlas = LoadnSave.getSpriteAtlas(LoadnSave.ENEMY_ATLAS_IMAGE);		// get player atlas 
		animations = new BufferedImage[5][9];  		//initialize animations array (5 rows/7 columns)
		for (int j = 0; j < animations.length; j++) {				// loop through rows
			for (int i = 0; i < animations[j].length; i++) {		// loop through columns
				// 71=sprite width, 70=sprite height, 60=displayWidth, 65=displayHeight
				// insert distinct animations from sprite atlas into animations 2-D array
				animations[j][i] = spriteAtlas.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);	
			}
		}
			
		statusBarImg = LoadnSave.getSpriteAtlas(LoadnSave.HEALTH_BAR);
	}
		
	// stores the level data with the player in a 2d array
	public void loadLevelData(int [][] lvlData) {
		this.lvlData = lvlData;
		if (!isEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
		
	// resets all movement booleans to false
	public void resetBooleans() {
		up = false;
		down = false;
		left = false;
		right = false;
	}
		
	public void setAttack(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
		
	public void resetAll() {
		resetBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;

			hitbox.x = x;
			hitbox.y = y;

			if (!isEntityOnFloor(hitbox, lvlData))
				inAir = true;
		}
	
	// return the players hitbox
	public Rectangle2D.Float returnHitBox() {
		return hitbox;
	}
	
	public int getEnemyWidth() {
		return CRABBY_WIDTH;
	}
	public int getEnemyHeight() {
		return CRABBY_HEIGHT;
	}

	@Override
	public void run(){
		while(true) {
			try{
				playing.enemySem.acquire();
				update();
			} catch (InterruptedException e){
				e.printStackTrace();
			} finally {
				playing.gameSem.release();
			}			
		}
	}	
}
