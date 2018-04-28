package api;

/**
 * This class is the interface for the enemy object. it can getHitBy( Projectile), and attack Tower. 
 * However, if there is an inter
 * @author ryanpond
 *
 */
public interface EngineEnemy {

	/**
	 * Called when this enemy is hit, will cause damage based on how much damage the projectile can do
	 */
	public void getHitBy(EngineProjectile myProjectile);
	
	/**
	 * Method called when the enemy comes up to a tower. 
	 * @return the amount of damage that this enemy does
	 */
	public Integer attackTower();
	
	/**
	 * Dependent on animation, but it will follow the path and update the enemy location
	 */
	public void followPath();

}
