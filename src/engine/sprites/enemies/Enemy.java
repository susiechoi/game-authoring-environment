package engine.sprites.enemies;

import java.awt.Point;
import java.util.List;

import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.CollisionProperty;
import engine.sprites.properties.Property;
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

    private double mySize;
    private int pathIndex;
    private double pathAngle;
    private Point targetPosition;
    private boolean freeze;

    public Enemy(String name, String image, double size, Launcher launcher, List<Property> properties) {
	super(name, image, size, launcher, properties);
	freeze = false;
	pathIndex = 0;
	pathAngle = 0;
    }

    /**
     * Copy constructor
     */
    public Enemy(Enemy copiedEnemy) {
	super(copiedEnemy.getName(), copiedEnemy.getImageString(), copiedEnemy.mySize, copiedEnemy.getLauncher(), copiedEnemy.getProperties());
	freeze = false;
	pathIndex = 0;
	pathAngle = 0;
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
	if(freeze) {
	    return;
	}
	rotateImage();
	double totalDistanceToMove = this.getProperty("SpeedProperty").getProperty()*elapsedTime; 
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

    public int getMoney() {
	return 0;
    }

    /**
     * Handles returning an enemy's damage after hitting a tower
     * 
     * @return Double: damage that Enemy incurs on the tower
     */
    @Override
    public double getDamage() {
	return getValue("DamageProperty");
    }

    /**
     * Returns true if this Enemy is still alive
     */
    @Override
    public boolean handleCollision(Sprite collider) {
	CollisionProperty myCollisionProperty = (CollisionProperty) collider.getPropertySuperclassType("CollisionProperty");
	myCollisionProperty.collidesWith(this);
	return this.isAlive();
    }

    @Override
    public int getPointValue() {
	return (int) getValue("ValueProperty");
    }
    
    public void setIndex(int i) {
	pathIndex = i;
    } 

    public int getIndex() {
	return pathIndex;
    }
    
    public double getAngle() {
	return pathAngle;
    }

    public void setAngle(double a) {
	pathAngle = a;
    }
    
    public void freeze(double duration) {
	freeze = true;
    }
   
//    public void updateProperties() {
//	myProperties = new ArrayList<>();
//	myProperties.add(new HealthProperty(0, 0, myInitialHealth));
//	myProperties.add(new DamageProperty(0, 0, myHealthImpact)); 
//	myProperties.add(new ValueProperty(myKillReward));
//	myProperties.add(new SpeedProperty(0, 0, 50));
//	updateImage(myImage);
//    }
}
