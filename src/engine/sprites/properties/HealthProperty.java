package engine.sprites.properties;

public class HealthProperty extends Property {

    private double myHealth;

    /**
     * Health property determines how much health a sprite has left
     * 
     * @param cost: cost of the upgrade
     * @param value: how much the user's health increments per upgrade
     * @param health: baseline health value
     */
    public HealthProperty(double cost, double value, double health) {
	super(cost, value);
	myHealth = health;
    }

    /**
     * Upgrade's a user's health
     */
    @Override
    public double upgrade(double balance) {
	if(canUpgrade(balance)) {
	    myHealth += upgradeValue;
	    return balance - upgradeCost;
	}
	return balance;
    }

    /**
     * Returns a user's current health
     */
    @Override
    public double getProperty() {
	return myHealth;
    }

    /**
     * Change's a user's health by the double change
     * 
     * @param change: how much the health is being changed by
     */
    public void change(double change) {
	myHealth += change;
    }
    
    /**
     * Return whether or not character is alive
     * 
     * @return boolean: True if character is alive, false otherwise
     */
    public boolean isAlive() {
	return myHealth > 0;
    }
}
