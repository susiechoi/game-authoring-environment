package engine.sprites.enemies;

import java.awt.Point;

import engine.physics.ImageIntersecter;
import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;


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
    private String myImage; 
    private HealthProperty myHealth;
    private double myInitialHealth; 
    private DamageProperty myDamage;
    private double myHealthImpact; 
    private ValueProperty myValue;
    private ImageIntersecter myIntersecter;
    private double mySpeed;
    private double mySize;
    private double myKillReward;
    private int pathIndex;
    private double pathAngle;
    private Point targetPosition;

    public Enemy(String name, String image, double speed, double size, Launcher launcher, HealthProperty health, DamageProperty damage, ValueProperty value) {
	super(name, image, size, launcher);
	myName = name; 
	myImage = image; 
	myHealth = health;
	myInitialHealth = myHealth.getProperty();
	myDamage = damage;
	myHealthImpact = myDamage.getProperty();
	myValue = value;
	myIntersecter = new ImageIntersecter(this); 
	mySpeed = speed; 
	myKillReward = value.getProperty();
	pathIndex = 0;
	pathAngle = 0;
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
	myValue = new ValueProperty(900);
    }
    /**
     * Sets the initial spawning point of the enemy
     * @param initialPoint
     */
    public void setInitialPoint(Point initialPoint) {
	targetPosition = initialPoint;
	this.getImageView().setX(initialPoint.getX());
	this.getImageView().setY(initialPoint.getY());
    } 

    /**
     * Moves the enemy along the path according to how much time has passed
     * @param elapsedTime
     */
    public void move(double elapsedTime) {
	rotateImage();
	double totalDistanceToMove = this.mySpeed*elapsedTime;
	double xMove = Math.sin(Math.toRadians(this.getRotate()))*totalDistanceToMove;
	double yMove = Math.cos(Math.toRadians(this.getRotate()))*totalDistanceToMove;
	this.getImageView().setX(this.getX()+xMove);
	this.getImageView().setY(this.getY()+yMove);
    }
    /**
     * Rotates the image to face the target
     */
    private void rotateImage() {
	double xDifference = targetPosition.getX() - this.getX();
	double yDifference = targetPosition.getY() - this.getY();
	double angleToRotateRads = Math.atan2(xDifference,yDifference);

	this.setRotate(Math.toDegrees(angleToRotateRads));
    }

    /**
     * Returns the current position in a Point of the enemy
     * @return
     */
    public Point currentPosition() {
	Point position = new Point();
	position.setLocation(this.getImageView().getX(), this.getImageView().getY());
	return position;
    }

    /**
     * Returns the next target position it is going after
     * @return
     */
    public Point targetPosition() {
	return targetPosition;
    }

    public void setNewPosition(Point newPosition) {
	targetPosition = newPosition;
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
    public double getDamage() {
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

    @Override
    public int getPointValue() {
	return (int) myValue.getProperty();
    }


    private double getSpeed() {
	return mySpeed; 
    }

    public void setIndex(int i) {
	pathIndex = i;
    } 

    public int getIndex() {
	return pathIndex;
    }
    @Override
    protected HealthProperty getHealthProp() {
	return this.myHealth;
    }

    public double getAngle() {
	return pathAngle;
    }

    public void setAngle(double a) {
	pathAngle = a;
    }

    public void updateProperties() {
		myHealth = new HealthProperty(0, 0, myInitialHealth);
		myDamage = new DamageProperty(0, 0, myHealthImpact); 
		myValue = new ValueProperty(myKillReward);
}


}
