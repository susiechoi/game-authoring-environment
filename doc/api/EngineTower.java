package api;

/**
 * This interface has all of the methods that will be called on a tower object, through
 * an event triggered either by the user or by the GameLoop
 * @author ryanpond
 *
 */
public interface Tower {

	/**
	 * Called when the tower is hit by an Enemy (could be projectile or enemy)
	 * @param attacker -- This 
	 */
	public void getHitBy(Enemy attacker);

	/**
	 * Updates the X and Y coordinates of the tower
	 * @param newX
	 * @param newY
	 */
	public void move(int newX, int newY);
	
	/**
	 * Called when the tower is sold. Will return how much this costs.
	 * @return
	 */
	public Integer sell(); // all methods dealing with money return cost
	
	/**
	 * Every attribute of the tower is upgraded. Upgrade object is used
	 */
	public void upgradeGeneral();

	/**
	 * Health attribute is upgraded by a specific upgrade amount
	 */
	public void upgradeHealth();
	
	/**
	 * Rate of fire is upgraded by a certain amount
	 */
	public void upgradeRateOfFire();
	
	/**
	 * Damage is upgraded by a specific amount, specified in myUpgrade object (created by xml)
	 */
	public void upgradeDamage();

	
}
