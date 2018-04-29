package engine.sprites.towers.projectiles;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
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

    private double mySize; 
    private ShootingSprites myTarget;
    private List<Sprite> hitTargets;
    private int myHits = 1;

    /**
     * Constructor that takes in a damage value and image, and creates a projectile
     * class.
     * 
     * @param damage: Damage property objects that illustrates how much damage a projectile exerts on enemy
     * @param image: image of projectile
     */
    public Projectile(String name, double size, String image, List<Property<Object>> properties) {
	super(name, image, size, properties);
	mySize = size; 
	hitTargets = new ArrayList<>();
    }

    /**
     * Moves projectile and accelerates according to enemy speed
     * 
     * @param myProjectile
     * @param target
     * @param shooterX
     * @param shooterY
     */
    @SuppressWarnings("unchecked")
    public Projectile(Projectile myProjectile, ShootingSprites target, double shooterX, double shooterY) {
	super(myProjectile.getName(),myProjectile.getImageString(), myProjectile.getSize(), myProjectile.getProperties());
	myTarget = target;
	if (target instanceof Enemy) {
	    Enemy myEnemy = (Enemy) target;
	    Double speed = myEnemy.getProperty("SpeedProperty").getProperty();
	    this.addProperty(new SpeedProperty(0, 0, speed + myEnemy.getSpeed()));	    
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
	double totalDistanceToMove = getValue("ConstantSpeedProperty")*elapsedTime;
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
}
