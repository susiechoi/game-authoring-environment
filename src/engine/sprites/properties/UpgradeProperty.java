package engine.sprites.properties;

/**
 * A property describes any attribute held by an object that can be upgraded.
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public abstract class UpgradeProperty<T> extends Property<Object> {

    private double upgradeCost;
    private double upgradeValue;

    /**
     * Constructor for upgrade property that takes in its value, upgrade cost, and value that the property
     * increments by when upgraded
     * 
     * @param cost: cost of the upgrade
     * @param value: value of the upgrade (how much it increments property)
     * @param property: initial property value
     */
    public UpgradeProperty(double cost, double value, double property) {
	super(property);
	upgradeCost = cost;
	
	//TODO: check this upgradeValue
	upgradeValue = value;
    }

    /**
     * Abstract method to upgrade an object and @return user's remaining balance
     */
    public double upgrade(double balance) {
	if(canUpgrade(balance)) {
	    double newValue = this.getProperty() + upgradeValue;
	    this.setProperty(newValue);
	    return balance - upgradeCost;
	}
	return balance;
    }

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
