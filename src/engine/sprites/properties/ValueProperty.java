package engine.sprites.properties;

public class ValueProperty extends Property {
    
    private double myValue;
    
    public ValueProperty(double cost, double upgradeValue, double propertyValue) {
	super(cost, upgradeValue);
	myValue = propertyValue;
    }

    @Override
    public double upgrade(double balance) {
	if(canUpgrade(balance)) {
	    myValue += upgradeValue;
	    return balance - upgradeCost;
	}
	return balance;
    }

    public double getProperty() {
	return myValue;
    }
}
