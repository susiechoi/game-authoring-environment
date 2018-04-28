package doc.api;

/**
 * Class is used when a projectile is launched from the Launcher (in Tower and possibly enemy)
 * @author ryanpond
 *
 */
public interface EngineProjectile {

	
	/**
	 * Called when this projectile hits an enemy. It will destroy itself, as well as
	 * @return the amount of damage it does. 
	 */
	public Integer hitsEnemy();

}
