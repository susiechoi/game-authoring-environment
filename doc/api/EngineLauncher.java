package api;

/**
 * This class is held in the Tower and possibly Enemy classes (Composition). 
 * It holds information to create Projectiles (speed, damage, etc)
 * It launches projectiles when called upon
 * @author ryanpond
 *
 */
public interface Launcher {

	/**
	 * Will create a projectile and then return it, like a 'launch'
	 * @return
	 */
	public Projectile launch();
	
}
