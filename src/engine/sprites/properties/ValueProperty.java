package engine.sprites.properties;

public class ValueProperty extends Property {
    
    private double myValue;
    
    /**
     * Returns a value of the sprite (how much it sells for)
     * 
     * @param cost
     * @param upgradeValue
     * @param propertyValue
     */
    public ValueProperty(double cost, double upgradeValue, double propertyValue) {
	super(cost, upgradeValue);
	myValue = propertyValue;
    }

    /**
     * Upgrades an object's value 
     */
    @Override
    public double upgrade(double balance) {
	if(canUpgrade(balance)) {
	    myValue += upgradeValue;
	    return balance - upgradeCost;
	}
	return balance;
    }

    /**
     * Returns an object's value
     */
    public double getProperty() {
	return myValue;
    }
}
