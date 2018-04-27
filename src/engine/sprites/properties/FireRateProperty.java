package engine.sprites.properties;

/**
 * Fire Rate property that determines how often a projectile is launched from
 * the launcher class.
 * 
 * @author Katherine Van Dyk
 * @date 4/5/18
 *
 */
<<<<<<< HEAD
public class FireRateProperty extends Property {
    
    /**
     * Constructor that takes in cost, value and rate of fire
     * 
     * @param cost: Cost of the projectile object upgrade
     * @param value: How much the fire rate is incremented per upgrade
     * @param fireRate: Rate of fire
     */
    public FireRateProperty(double firerate) {
	super(firerate);
    }
=======
public class FireRateProperty extends UpgradeProperty<Boolean> {

	/**
	 * Constructor that takes in cost, value and rate of fire
	 * 
	 * @param cost: Cost of the projectile object upgrade
	 * @param value: How much the fire rate is incremented per upgrade
	 * @param fireRate: Rate of fire
	 */
	public FireRateProperty(double cost, double value, double fireRate) {
		super(cost, value, fireRate);
	}

	@Override
	public Boolean execute(Object...args) {
		Integer timeLastFired = (Integer)args[0];
		if((Integer)timeLastFired >= 100/this.getProperty()) {
     		return true;
     	}
	return (Boolean)false;
	}
>>>>>>> ffe5b4bd3672d27fac8132a5ecde323bc94b7bff

    public FireRateProperty(Property p) {
	super(p);
    }
}
