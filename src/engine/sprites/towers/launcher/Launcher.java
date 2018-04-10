package engine.sprites.towers.launcher;

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
 * @date 3/28/18
 */
public class Launcher {

    private RangeProperty myRange;
    private FireRateProperty myFireRate;
    private Projectile myProjectile;
    private long timeLastFired;

    public Launcher(FireRateProperty fireRate, Projectile projectile, RangeProperty range) {
	myFireRate = fireRate;
	myProjectile = projectile;
	myRange = range;
	timeLastFired = System.nanoTime();
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
     * @return : the projectile that was fired
     */
    public Projectile launch() {
	long currTime = System.nanoTime();
	long timeSinceLastShot = currTime - timeLastFired;
	if(timeSinceLastShot >= myFireRate.getProperty()) {
	    timeSinceLastShot = currTime;
	    return myProjectile;
	}
	else {
	    return null;
	}
    }

    public double upgradeDamage(double balance) {
	return myProjectile.upgradeDamage(balance);
    }
    
    public double getRange() {
    	return myRange.getProperty(); 
    }
    
    public double getFireRate() {
    	return myFireRate.getProperty(); 
    }
    
    public Image getProjectileImage() {
    	return myProjectile.getImage().getImage(); 
    }
    
    public double getProjectileDamage() {
    	return myProjectile.getDamage(); 
    }
    
    public double getProjectileSpeed() {
    	return myProjectile.getSpeed(); 
    }


}
