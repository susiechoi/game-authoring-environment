package engine.sprite.properties;

public class ValueProperty extends Property {
    
    private double myValue;
    
    public ValueProperty(double value) {
	myValue = value;
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
