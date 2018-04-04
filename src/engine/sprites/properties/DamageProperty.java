package engine.sprites.properties;

public class DamageProperty extends Property {

    private double myDamage;
    
    public DamageProperty(double damage) {
	myDamage = damage;
    }

    @Override
    public double upgrade(double balance) {
	if(balance - upgradeCost < 0) {
	    return balance;
	}
	else {
	    myDamage += upgradeValue;
	    return balance - upgradeCost;
	}
    }

    public double getProperty() {
	return myDamage;
    }


}
