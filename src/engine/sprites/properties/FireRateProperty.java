package engine.sprites.properties;

/**
 * Fire Rate property that determines how often a projectile is launched from
 * the launcher class.
 * 
 * @author Katherine Van Dyk
 * @date 4/5/18
 *
 */
public class FireRateProperty extends UpgradeProperty {
    
    private double myFireRate;
    
    /**
     * Constructor that takes in cost, value and rate of fire
     * 
     * @param cost: Cost of the projectile object upgrade
     * @param value: How much the fire rate is incremented per upgrade
     * @param fireRate: Rate of fire
     */
    public FireRateProperty(double cost, double value, double fireRate) {
	super(cost, value);
	myFireRate = fireRate;
    }

    /**
     * Upgrade fire rate of object
     */
    @Override
    public double upgrade(double balance) {
	if(canUpgrade(balance)) {
	    myFireRate += upgradeValue;
	    return balance - upgradeCost;
	}
	return balance;
    }

    /**
     * Gets the current rate of fire as double
     */
    @Override
    public double getProperty() {
	return myFireRate;
    }
    
    

}
