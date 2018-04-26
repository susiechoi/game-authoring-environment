package engine.sprites.towers.projectiles;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.ConstantSpeedProperty;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;

/**
 * Projectile class is a sprite that is launched from the tower
 * and can intersect with enemies to destroy them. 
 * 
 * @author Katherine Van Dyk
 *
 */
public class Projectile extends Sprite implements FrontEndSprite{

    private double mySize; 
    private String myImage; 
    private ShootingSprites myTarget;
    private List<Sprite> hitTargets;
    private int myHits = 1;
    private List<Property> myProperties;

    /**
     * Constructor that takes in a damage value and image, and creates a projectile
     * class.
     * 
     * @param damage: Damage property objects that illustrates how much damage a projectile exerts on enemy
     * @param image: image of projectile
     */
    public Projectile(String name, DamageProperty damage, double size, String image, ConstantSpeedProperty speed) {
	super(name, image, size);
	mySize = size; 
	hitTargets = new ArrayList<>();
	myProperties = new ArrayList<Property>();
	myProperties.add(speed);
	myProperties.add(damage);
    }

    public Projectile(Projectile myProjectile, ShootingSprites target, double shooterX, double shooterY) {
	super(myProjectile.getName(),myProjectile.getImageString(), myProjectile.getSize());
	myTarget = target;
	myProperties = new ArrayList<Property>();
	for(Property p : myProjectile.getProperties()) {
	    myProperties.add(p.makeCopy());
	}
	this.place(shooterX, shooterY);
	this.rotateImage();
	hitTargets = new ArrayList<>();
    }

    /**
     * Moves image in direction of it's orientation
     */
    public void move(double elapsedTime) {
	if (this.myTarget.isAlive()) {
	    rotateImage();
	}
	double totalDistanceToMove = getProperty("ConstantSpeedProperty")*elapsedTime;
	double xMove = Math.sin(Math.toRadians(this.getRotate()))*totalDistanceToMove;
	double yMove = Math.cos(Math.toRadians(this.getRotate()))*totalDistanceToMove;
	this.getImageView().setX(this.getX()+xMove);
	this.getImageView().setY(this.getY()+yMove);
    }

    /**
     * Rotates the image to face the target
     */
    private void rotateImage() {

	double xDifference = myTarget.getX() - this.getX();
	double yDifference = myTarget.getY() - this.getY();
	double angleToRotateRads = Math.atan2(xDifference,yDifference);
	this.setRotate(Math.toDegrees(angleToRotateRads));
    }

    /**
     * 
     * @return : the amount of damage this Projectile does
     */
    @Override
    public double getDamage() {
	return getProperty("DamageProperty");
    }

    public String getImage() {
	return myImage; 
    }

    public double getSize() {
	return mySize; 
    }

    /**
     * @return true if should be removed
     */
    @Override
    public boolean handleCollision(Sprite sprite) {
	this.hitTargets.add(sprite);
	this.myHits--;
	return !(myHits > 0);
    }

    /**
     * This method prevents the same projectile from colliding with the same tower/enemy more than once
     * Important since projectiles will be able to hit multiple enemies/towers before being removed
     * @param target
     * @return
     */
    public boolean hasHit(ShootingSprites target) {
	return this.hitTargets.contains(target);
    }

    public void addProperty(Property property) {
	System.out.println("Property: " + property);
	System.out.println("Property Name: " + property.getName());
	Property toRemove = null;
	for(Property p : myProperties) {
	    if(property.getName().equals(p.getName())) {
		toRemove = p;
	    }
	}
	if(toRemove != null) myProperties.remove(toRemove);
	myProperties.add(property);
    }


    public void setImage(String image) {
	super.updateImage(image);
    }

    public double getProperty(String name) {
    	for(Property property : myProperties) {
    	    if(property.getName().equals(name)) {
    		return property.getProperty();
    	    }
    	}
    	return -1;
    }
   
    public List<Property> getProperties(){
	return myProperties;
    }
    
    /**
     * Handles upgrading the health of a tower
     */
    public double upgradeProperty(String name, double balance) {
	for(Property property : myProperties) {
	    if(property.getName() == name) {
		return ((UpgradeProperty) property).upgrade(balance);
	    }
	}
	return balance;
    }

}
