package engine.sprites.properties;

/**
 * A property describes any attribute held by an object that can be upgraded.
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public abstract class UpgradeProperty extends Property {
    
    protected double upgradeCost;
    protected double upgradeValue;
    
    public UpgradeProperty(double cost, double value) {
	upgradeCost = cost;
	upgradeValue = value;
    }
    
    /**
     * Abstract method to upgrade an object and @return user's remaining balance
     */
    public abstract double upgrade(double balance);

    /**
     * Method to check if its possible to upgrade a property based on the user's current balance
     * 
     * @param balance: User's current balance
     * @return boolean: True if user can afford the upgrade, false otherwise
     */
    protected boolean canUpgrade(double balance) {
	return (balance - upgradeCost) < 0;
    }
    
    /**
     * Method to return the cost of an upgrade (for calculating an object's worth)
     * 
     * @return double: representing cost of upgrade
     */
    public double getCost() {
	return upgradeCost;
    }
    

}
