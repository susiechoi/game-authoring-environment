package engine.sprites.properties;

public class FireRateProperty extends Property {
    
    private double myFireRate;
    
    public FireRateProperty(double cost, double value, double fireRate) {
	super(cost, value);
	myFireRate = fireRate;
    }

    @Override
    public double upgrade(double balance) {
	if(canUpgrade(balance)) {
	    myFireRate += upgradeValue;
	    return balance - upgradeCost;
	}
	return balance;
    }

    @Override
    public double getProperty() {
	return myFireRate;
    }
    
    

}
