package engine.properties;

public class HealthProperty extends Property {

    private double myHealth;
    
    public HealthProperty(double health) {
	myHealth = health;
    }

    @Override
    public double upgrade(double balance) {
	if(canUpgrade(balance)) {
	    myHealth += upgradeValue;
	    return balance - upgradeCost;
	}
	return balance;
    }

    public double getProperty() {
	return myHealth;
    }
    
    public void change(double change) {
	myHealth += change;
    }
}
