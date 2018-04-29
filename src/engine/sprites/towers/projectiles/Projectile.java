package engine.sprites.towers.projectiles;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.MovingProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.SpeedProperty;
import engine.sprites.enemies.Enemy;

/**
 * Projectile class is a sprite that is launched from the tower
 * and can intersect with enemies to destroy them. 
 * 
 * @author Katherine Van Dyk
 * @author Ryan Pond
 *
 */
public class Projectile extends Sprite implements FrontEndSprite{

    private static final double mySpeedFactor = 1.5;
    private double mySize; 
    private ShootingSprites myTarget;
    private List<Sprite> hitTargets;
    private int myHits = 1;
    private MovingProperty myMovingProperty;

    /**
     * Constructor that takes in a damage value and image, and creates a projectile
     * class.
     * 
     * @param damage: Damage property objects that illustrates how much damage a projectile exerts on enemy
     * @param image: image of projectile
     */
    public Projectile(String name, double size, String image, List<Property> properties) {
	super(name, image, size, properties);
	mySize = size; 
	hitTargets = new ArrayList<>();
	for (Property p: properties) {
	    System.out.println(p.getName() + " in projectile property list");
	}
    }

    /**
     * Moves projectile and accelerates according to enemy speed
     * 
     * @param myProjectile
     * @param target
     * @param shooterX
     * @param shooterY
     */
    public Projectile(Projectile myProjectile, ShootingSprites target, double shooterX, double shooterY) {
	super(myProjectile.getName(),myProjectile.getImageString(), myProjectile.getSize(), myProjectile.getProperties());
	myTarget = target;
	if (target instanceof Enemy) {
	    Enemy myEnemy = (Enemy) target;
	    Double speed = myEnemy.getProperty("SpeedProperty").getProperty();
	    this.addProperty(new SpeedProperty(0,0, mySpeedFactor*speed));
	}
	this.place(shooterX, shooterY);
	this.rotateImage();
	hitTargets = new ArrayList<>();
	myMovingProperty = (MovingProperty) this.getPropertySuperclassType("MovingProperty");
    }

    /**
     * Moves image in direction of it's orientation
     */
    public void move(double elapsedTime) {
	try {
	    myMovingProperty.move(this, elapsedTime);
	}catch(NullPointerException e) {
	    //this means there is not movement property defined for the projectile, so don't move them
	    System.out.println("no movement property");
	    return;
	}
	
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
	return getValue("DamageProperty");
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
    
    public void setImage(String image) {
	super.updateImage(image);
    }
    
    public ShootingSprites getTarget() {
	return myTarget;
    }

    public double getSpeed() {
	return this.getProperty("SpeedProperty").getProperty();
    }
}
