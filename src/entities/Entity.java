package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

// The class outlining both player and enemy entities
public abstract class Entity {
	
	protected float x,y;			// starting x/y position in game
	protected int width, height;	// width and height of entity
	protected Rectangle2D.Float hitbox;
	
	public Entity (float x, float y, int width, int height ) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;  
		
	}
	
	protected void drawHitBox(Graphics g) {
		// for debugging hitbox
		g.setColor(Color.PINK);
		g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}

	// create entity hitbox
	protected void initHitBox(float x, float y, int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}
	
	// update entity hitbox
	/*protected void updateHitBox() {
		 hitbox.x = (int) x;
		 hitbox.y = (int) y;
	}*/
	
	public Rectangle2D.Float getHitBox() {
		return hitbox;
	}
}
