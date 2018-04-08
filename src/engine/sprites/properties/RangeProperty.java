package engine.sprites.properties;

/**
 * Range property implemented by launcher. Determines how far projectiles move after
 * being launched by Launcher object.
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public class RangeProperty extends UpgradeProperty {
    
    private double myRange;

    /**
     * Constructor that takes in cost of range property, upgrade value, and 
     * initial range value.
     * 
     * @param cost
     * @param value
     * @param range
     */
    public RangeProperty(double cost, double value, double range) {
	super(cost, value);
	myRange = range;
    }

    /**
     * Upgrades range property and returns user's balance
     */
    @Override
    public double upgrade(double balance) {
	if(canUpgrade(balance)) {
	    myRange += upgradeValue;
	    return balance - upgradeCost;
	}
	return balance;
    }

    /**
     * Returns the current range value
     */
    @Override
    public double getProperty() {
	return myRange;
    }

}
