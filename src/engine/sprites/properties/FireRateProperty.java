package engine.sprites.properties;

/**
 * Fire Rate property that determines how often a projectile is launched from
 * the launcher class.
 * 
 * @author Katherine Van Dyk
 * @date 4/5/18
 *
 */
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

    public FireRateProperty(Property p) {
	super(p);
    }
}
