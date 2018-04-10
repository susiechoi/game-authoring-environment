package engine.sprites.enemies;

import engine.path.Path;
import engine.physics.ImageIntersecter;
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
    private ImageIntersecter myIntersecter;
    private Path myPath;
    private double mySpeed;

    public Enemy(String name, Image image, HealthProperty health, DamageProperty damage, ValueProperty value, Path path) {
	super(name, image);
	myIntersecter = new ImageIntersecter(this.getImageView()); 
	myHealth = health;
	myDamage = damage;
	myValue = value;
	myPath = path;
    }
    
    /**
     * Copy constructor
     */
    public Enemy(Enemy copiedEnemy) {
    	super("", copiedEnemy.getImageView().getImage());
    	myIntersecter = copiedEnemy.getIntersecter(); 
    	myHealth = copiedEnemy.getHealth(); 
    	myDamage = copiedEnemy.getDamage();
    	myValue = copiedEnemy.getValue();
    	myPath = copiedEnemy.getPath(); 
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
     * Moves the enemy along the path according to how much time has passed
     * @param elapsedTime
     */
    public void move(double elapsedTime) {
	Point2D newPosition = myPath.nextPosition(elapsedTime, mySpeed);
	myPath.nextPosition(elapsedTime, mySpeed);
	this.getImageView().setX(newPosition.getX());
	this.getImageView().setY(newPosition.getY());
    }

    /**
     * Handles returning an enemy's damage after hitting a tower
     * 
     * @return Double: damage that Enemy incurs on the tower
     */
    public Double damage() {
	return myDamage.getProperty();
    }
    
    private ImageIntersecter getIntersecter() {
    	return myIntersecter; 
    }
    
    private HealthProperty getHealth() {
    	return myHealth; 
    }
    
    private DamageProperty getDamage() {
    	return myDamage; 
    }
    
    private ValueProperty getValue() {
    	return myValue; 
    }
    
    private Path getPath() {
    	return myPath; 
    } 
    
}
