package engine.towers.launcher;

import engine.towers.projectiles.Projectile;
import engine.towers.trigger.Trigger;

/**
 * 
 * @author Ben Hodgson 3/28/18
 * 
 * Class to manage different projectiles. Takes a defined range and trigger to determine when to
 * fire projectiles
 */
public class Launcher implements Trigger {
	
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

    @Override
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

}
