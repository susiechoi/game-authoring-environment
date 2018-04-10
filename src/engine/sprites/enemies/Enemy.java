package engine.sprites.enemies;

import engine.path.Path;
import engine.physics.ImageIntersecter;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import javafx.geometry.Point2D;

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
public class Enemy extends ShootingSprites{

	private String myName; 
	private Image myImage; 
    private HealthProperty myHealth;
    private double myInitialHealth; 
    private DamageProperty myDamage;
    private double myHealthImpact; 
    private ValueProperty myValue;
    private ImageIntersecter myIntersecter;
    private Path myPath;
    private double mySpeed;
    private double myKillReward;
//    private double myKillUpgradeCost;
//    private double myKillUpgradeValue; 

    public Enemy(String name, Image image, double speed, double size, Launcher launcher, HealthProperty health, DamageProperty damage, ValueProperty value, Path path) {
	super(name, image, size, launcher);
	myName = name; 
	myImage = image; 
	myHealth = health;
	myInitialHealth = myHealth.getProperty();
	myDamage = damage;
	myHealthImpact = myDamage.getDamage();
	myValue = value;
	myIntersecter = new ImageIntersecter(this.getImageView()); 
	myPath = path;
	mySpeed = speed; 
	myKillReward = value.getProperty();
	System.out.println("NEW ENEMY OBJ MADE WITH NAME "+name+" AND A FEW ATTRIBUTES: "+myHealthImpact+", "+mySpeed+", "+myKillReward);
    }
    
    /**
     * Copy constructor
     */
    public Enemy(Enemy copiedEnemy) {
    	super("", copiedEnemy.getImageView().getImage());
    	myName = copiedEnemy.getName(); 
    	myImage = copiedEnemy.getImageView().getImage(); 
    	myIntersecter = copiedEnemy.getIntersecter(); 
    	myHealth = copiedEnemy.getHealth(); 
    	myDamage = copiedEnemy.getDamageProperty();
    	myHealthImpact = myDamage.getDamage(); 
    	myValue = copiedEnemy.getValue();
    	myPath = copiedEnemy.getPath(); 
    	mySpeed = copiedEnemy.getSpeed();
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
    public void move(double elapsedTime) {
	Point2D newPosition = myPath.nextPosition(elapsedTime, mySpeed);
	myPath.nextPosition(elapsedTime, mySpeed);
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
    
    private Path getPath() {
    	return myPath; 
    } 
    
    private double getSpeed() {
    	return mySpeed; 
    }
    
}
