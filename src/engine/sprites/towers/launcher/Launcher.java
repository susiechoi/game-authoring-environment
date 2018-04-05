package engine.sprites.towers.launcher;

import engine.sprites.properties.FireRateProperty;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Class to manage different projectiles. Takes a defined range and trigger to determine when to
 * fire projectiles
 * 
 * @author Ben Hodgson 
 * @author Ryan Pond
 * @date 3/28/18
 */
public class Launcher {

    private FireRateProperty myFireRate;
    private Projectile myProjectile;
    private long timeLastFired;

    public Launcher(FireRateProperty fireRate, Projectile projectile) {
	myFireRate = fireRate;
	myProjectile = projectile;
	timeLastFired = System.nanoTime();
    }

    /**
     * Sets the current projectile type managed by the ProjectileManager
     */
    public void setProjectile() {

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


}
