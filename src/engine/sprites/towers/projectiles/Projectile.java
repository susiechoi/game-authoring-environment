package engine.sprites.towers.projectiles;

import engine.sprites.FrontEndSprite;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import javafx.scene.image.Image;

/**
 * Projectile class is a sprite that is launched from the tower
 * and can intersect with enemies to destroy them. 
 * 
 * @author Katherine Van Dyk
 *
 */
public class Projectile extends Sprite implements FrontEndSprite{

	private DamageProperty myDamage;
	private double mySpeed;
	private double mySize; 
	private Sprite myTarget;
	
	/**
	 * Constructor that takes in a damage value and image, and creates a projectile
	 * class.
	 * 
	 * @param damage: Damage property objects that illustrates how much damage a projectile exerts on enemy
	 * @param image: image of projectile
	 */
	public Projectile(String name, DamageProperty damage, double size, String image, double speed) {
	    	super(name, image, size);
		myDamage = damage;
		mySpeed = speed;
		mySize = size; 
	}
	
	public Projectile(Projectile myProjectile, Sprite target, double shooterX, double shooterY) {
	    super(myProjectile.getName(),myProjectile.getImageString(), myProjectile.getSize());
	    myTarget = target;
	    mySpeed = 300;
	    this.place(shooterX, shooterY);
	    this.rotateImage();
	    
	    myTarget = target;
	}

	/**
	 * Moves image in direction of it's orientation
	 */
	public void move(double elapsedTime) {
	    	myTarget.place(100, 100);
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
	    	double xDifference = myTarget.getX() - this.getX();
	    	double yDifference = myTarget.getY() - this.getY();
	    	double angleToRotateRads = Math.tan(xDifference/yDifference);
	    	this.setRotate(Math.toDegrees(angleToRotateRads));
	}
	
	/**
	 * Upgrades damage value of projectile
	 * 
	 * @param balance: New user's balance of money
	 * @return double representing new balance of user
	 */
	public double upgradeDamage(double balance) {
	    return myDamage.upgrade(balance);
	}

	/**
	 * 
	 * @return : the amount of damage this Projectile does
	 */
	public Double getDamage() {
	    return myDamage.getProperty();
	}
	
	public double getSpeed() {
	    return mySpeed;
	}
	
	
	public String getDamageName() {
		return myDamage.getName();
	}
	
	public double getSize() {
		return mySize; 
	}


}
