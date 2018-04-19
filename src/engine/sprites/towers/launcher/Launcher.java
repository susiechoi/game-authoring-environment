package engine.sprites.towers.launcher;

import engine.managers.Manager;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.FireRateProperty;
import engine.sprites.properties.RangeProperty;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.Image;

/**
 * Class to manage different projectiles. Takes a defined range and trigger to determine when to
 * fire projectiles
 * 
 * @author Ben Hodgson 
 * @author Ryan Pond
 * @author Katherine Van Dyk
 * @author Miles Todzo 4/9
 * @date 3/28/18
 */
public class Launcher extends Manager<Projectile>{

    private RangeProperty myRange;
    private FireRateProperty myFireRate;
    private Projectile myProjectile;
    private double timeSinceLastShot;

    public Launcher(FireRateProperty fireRate, Projectile projectile, RangeProperty range) {
	myFireRate = fireRate;
	System.out.println("fire rate is " + myFireRate.getProperty());
	myProjectile = projectile;
	myRange = range;
	timeSinceLastShot = 0;
    }
    
    

    public Launcher(Launcher copiedLauncher) {
	myFireRate = copiedLauncher.getFireRateProperty();
	myProjectile = copiedLauncher.getProjectile();
	myRange = copiedLauncher.getRangeProperty();
	timeSinceLastShot = 0;
    }



    /**
     * Returns the projectile object associated with the Launcher
     * @return
     */
    public Projectile getProjectile() {
	return myProjectile;
    }



    /**
     * Sets the current projectile type managed by the ProjectileManager
     */
    public void setProjectile(Projectile projectile) {
	myProjectile = projectile;
    }

    /**
     * Updates the tower's projectile fire rate
     * 
     * @param rate: the new fire rate 
     * @return double: the user's remaining balance
     */
    public double upgradeFireRate(double balance) {
	return myFireRate.upgrade(balance);
    }


    /**
     * Launch method will make sure that enough time has passed since last shot and then fire a new projectile
     * 
     */
    //TODO implement to shoot at where enemy is going
    public Projectile launch(ShootingSprites target, double shooterX, double shooterY) {
    	Projectile launchedProjectile = new Projectile(myProjectile, target,shooterX, shooterY);
    	this.addToActiveList(launchedProjectile);
    	return launchedProjectile;
    }
    
    /**
     * Checks to see if the rate of fire is less than the time elapsed since the last shot
     * @return 
     */
    public boolean hasReloaded(double elapsedTime) {
	System.out.println("elapsedTime is " + elapsedTime);
     	if(timeSinceLastShot >= myFireRate.getProperty()) {
     		timeSinceLastShot=0;
     		return true;
     	}
     	timeSinceLastShot+=elapsedTime;
	return false;
    }

    public double upgradeDamage(double balance) {
	return myProjectile.upgradeDamage(balance);
    }
    
    public double getRange() {
    	return myRange.getProperty(); 
    }

    public Image getProjectileImage() {
    	return myProjectile.getImageView().getImage(); 
    }
    
    public double getProjectileDamage() {
    	return myProjectile.getDamage(); 
    }
    
    public double getProjectileSpeed() {
    	return myProjectile.getSpeed(); 
    }
    
    public double getProjectileSize() {
    	return myProjectile.getSize(); 
    }

    public String getDamageName() {
    	return myProjectile.getDamageName();
    }
    public FireRateProperty getFireRateProperty() {
    	return myFireRate;
    }
    public RangeProperty getRangeProperty() {
    	return myRange;
    }
    
    public double getDamage() {
    	return myProjectile.getDamage();
    }
    public double getFireRate() {
    	return myFireRate.getProperty();
    }

    public String getFireRateName() {
    	return myFireRate.getName();
    }
}
