package engine.sprites.enemies;

import java.awt.geom.Point2D;

import engine.path.Path;
import engine.physics.ImageIntersecter;
import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;

import javafx.scene.Node;

/**
 * This is used for the Enemy object in the game. It will use composition to implement
 * intersectable methods.
 * 
 * @author ryanpond
 * @author Katherine Van Dyk
 * @date 4/8/18
 *
 */
public class Enemy extends ShootingSprites implements FrontEndSprite{

    private String myName; 
    private HealthProperty myHealth;
    private double myInitialHealth; 
    private DamageProperty myDamage;
    private double myHealthImpact; 
    private ValueProperty myValue;
    private ImageIntersecter myIntersecter;
    private double mySpeed;
    private double mySize;
    private double myKillReward;
    private String myImage; 
    //    private double myKillUpgradeCost;
    //    private double myKillUpgradeValue; 

    public Enemy(String name, String image, double speed, double size, Launcher launcher, HealthProperty health, DamageProperty damage, ValueProperty value) {
	super(name, image, size, launcher);
	myImage = image; 
	myName = name; 
	myHealth = health;
	myInitialHealth = myHealth.getProperty();
	myDamage = damage;
	myHealthImpact = myDamage.getProperty();
	myValue = value;
	myIntersecter = new ImageIntersecter(this); 
	mySpeed = speed; 
	myKillReward = value.getProperty();
    }

    /**
     * Copy constructor
     */
    public Enemy(Enemy copiedEnemy) {
	super("", copiedEnemy.getImageString(), copiedEnemy.mySize, copiedEnemy.getLauncher());
	myName = copiedEnemy.getName(); 
	setImage(copiedEnemy.getImageView().getImage()); 
	myIntersecter = copiedEnemy.getIntersecter(); 
	myHealth = copiedEnemy.getHealth(); 
	myDamage = copiedEnemy.getDamageProperty();
	myHealthImpact = myDamage.getProperty(); 
	myValue = copiedEnemy.getValue();
	mySpeed = copiedEnemy.getSpeed();
    }

    /**
     * Used for debugging/demo purposes, should not actually be used
     * @param string
     * @param string2
     * @param i
     */
    public Enemy(String name, String image, double size) {
	super(name, image, size, null);
	myHealth = new HealthProperty(10000,10000,100);
	myDamage = new DamageProperty(10000, 10000, 10000);
	myValue = new ValueProperty(300);
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
     * Moves the enemy along the path according to how much time has passed
     * @param elapsedTime
     */
    public void move(Path path) {
	Point2D newPosition = path.nextPosition(mySpeed);
	this.getImageView().setX(newPosition.getX());
	this.getImageView().setY(newPosition.getY());
    }

    public String getName() {
	return myName; 
    }

    /**
     * Handles returning an enemy's damage after hitting a tower
     * 
     * @return Double: damage that Enemy incurs on the tower
     */
    @Override
    public Double getDamage() {
	return myDamage.getProperty();
    }

    /**
     * Returns true if this Enemy is still alive
     */
    @Override
    public boolean handleCollision(Sprite collider) {
	System.out.println("health is " + myHealth.getProperty());
	myHealth.loseHealth(collider.getDamage());
	return myHealth.isAlive();
    }

    private ImageIntersecter getIntersecter() {
	return myIntersecter; 
    }

    private HealthProperty getHealth() {
	return myHealth; 
    }

    private DamageProperty getDamageProperty() {
	return myDamage; 
    }

    private ValueProperty getValue() {
	return myValue; 
    }
    public int getPointValue() {
    	return 0;
   // 	return (int)this.myValue.getProperty();
    }

    
    private double getSpeed() {
	return mySpeed; 
    }
    
    private String getImage() {
    	return myImage; 
    }

}
