package engine.sprites.properties;

/**
 * A property describes any attribute held by an object that can be upgraded.
 * 
 * @author Katherine Van Dyk
 *
 */
public abstract class Property implements PropertyI {
    
    protected double upgradeCost;
    protected double upgradeValue;
    
    public Property(double cost, double value) {
	upgradeCost = cost;
	upgradeValue = value;
    }
    
    /**
     * Abstract method to upgrade an object and @return user's remaining balance
     */
    @Override
    public abstract double upgrade(double balance);
    
    /**
     * Abstract method to get an immutable copy of the specific property
     */
    @Override
    public abstract double getProperty();
    
    /**
     * Method to check if its possible to upgrade a property based on the user's current balance
     * 
     * @param balance: User's current balance
     * @return boolean: True if user can afford the upgrade, false otherwise
     */
    protected boolean canUpgrade(double balance) {
	return (balance - upgradeCost) < 0;
    }

}
