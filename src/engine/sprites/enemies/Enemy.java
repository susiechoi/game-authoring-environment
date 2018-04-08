package engine.sprites.enemies;

import engine.path.Path;
import engine.physics.IIntersecter;
import engine.physics.Intersecter;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.projectiles.Projectile;
import java.awt.geom.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * This is used for the Enemy object in the game. It will use composition to implement
 * intersectable methods.
 * 
 * @author ryanpond
 * @author Katherine Van Dyk
 * @date 4/8/18
 *
 */
public class Enemy extends Sprite{

    private HealthProperty myHealth;
    private DamageProperty myDamage;
    private ValueProperty myValue;
    private IIntersecter myIntersecter;
    private Path myPath;
    private double mySpeed;

    public Enemy(Image myImage, HealthProperty health, DamageProperty damage, ValueProperty value, Path path) {
	super(myImage);
	myIntersecter = new Intersecter(this.getImage()); 
	myHealth = health;
	myDamage = damage;
	myValue = value;
	myPath = path;
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
    public void move(double elapsedTime) {
	Point2D newPosition = myPath.nextPosition(elapsedTime, mySpeed);
	myPath.nextPosition(elapsedTime, mySpeed);
	this.getImage().setX(newPosition.getX());
	this.getImage().setY(newPosition.getY());
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
