package engine.sprites.properties;

public class FireRateProperty extends Property {
    
    private double myFireRate;
    
    public FireRateProperty(double fireRate) {
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
