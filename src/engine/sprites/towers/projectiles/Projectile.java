package engine.sprites.towers.projectiles;

import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import javafx.scene.image.ImageView;

/**
 * Projectile class is a sprite that is launched from the tower
 * and can intersect with enemies to destroy them. 
 * 
 * @author Katherine Van Dyk
 *
 */
public class Projectile extends Sprite {

	private DamageProperty myDamage;
	
	/**
	 * Constructor that takes in a damange value and image, and creates a projectile
	 * class.
	 * 
	 * @param damage: Damage property objects that illustrates how much damage a projectile exerts on enemy
	 * @param image: image of projectile
	 */
	public Projectile(DamageProperty damage, ImageView image) {
	    	super(image);
		myDamage = damage;
	}
	
	/**
	 * Moves image along a curve on the screen
	 * 
	 * @param newX: new X-coordinate of projectile
	 * @param newY: new Y-coordinate of projectile
	 */
	public void move(double newX, double newY) {
		// TODO fill this out with delegation to a mover
	}
	
	/**
	 * Upgrades damage value of projectile
	 * 
	 * @param balance: New user's balance of money
	 * @return double representing new balance of user
	 */
	public double upgradeDamage(double balance) {
	    return myDamage.upgrade(balance);
	}

}
