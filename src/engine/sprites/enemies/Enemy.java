package engine.sprites.enemies;

import engine.physics.IIntersecter;
import engine.physics.Intersecter;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * This is used for the Enemy object in the game. It will use composition to implement
 * intersectable methods.
 * 
 * @author ryanpond
 *
 */
public class Enemy extends Sprite{

	private HealthProperty myHealth;
	private DamageProperty myDamage;
	private ValueProperty myValue;
	private IIntersecter myIntersecter;

	public Enemy(ImageView myImage, HealthProperty health, DamageProperty damage, ValueProperty value) {
		super(myImage);
		myIntersecter = new Intersecter(myImage); 
		myHealth = health;
		myDamage = damage;
		myValue = value;
	}

	/**
	 * Tests to see if another ImageView overlaps with the Enemy
	 * @param otherImage : other image (projectile, tower, etc)
	 * @return boolean, yes or no
	 */
	public boolean overlap(Node otherImage) {
		return myIntersecter.overlaps(otherImage); 
	}

	/**
         * Handles when the Enemy is hit by a tower
         * 
         * @param projectile: the projectile that hit the enemy
         * @return : returns true if the enemy is still alive, false if it is dead
         */
	public boolean getHitBy(Projectile projectile) { // I don't think this is supposed to return a boolean -bma
		myHealth.loseHealth(projectile.getDamage());
		return myHealth.isAlive();
	}

	/**
     * Handles updating the enemy position to follow the path
     */
	public void followPath() {
		// TODO Auto-generated method stub

	}

	/**
     * Handles returning an enemy's damage after hitting a tower
     * 
     * @return Double: damage that Enemy incurs on the tower
     */
	public Double damage() {
		return myDamage.getProperty();
	}



}
