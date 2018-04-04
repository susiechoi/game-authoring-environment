package engine.sprites.towers.launcher;

import engine.sprites.towers.projectiles.Projectile;

/**
 * 
 * @author Ben Hodgson 3/28/18
 * @author Ryan Pond
 * Class to manage different projectiles. Takes a defined range and trigger to determine when to
 * fire projectiles
 */
public class Launcher {
	
	private double myFireRate;
	private Projectile myProjectile;
	private long timeLastFired;
	
	public Launcher(double fireRate, Projectile projectile) {
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
     */
    public void updateFireRate(double rate) {
	
    }

    public Projectile launch() {
    		long currTime = System.nanoTime();
    		long timeSinceLastShot = currTime - timeLastFired;
    		if(timeSinceLastShot>=myFireRate) {
    			timeSinceLastShot = currTime;
    			return myProjectile;
    		}
    		else {
    			return null;
    		}
    }

    public double upgrade(double balance) {
	return myProjectile.upgradeDamage(balance);
    }


}
