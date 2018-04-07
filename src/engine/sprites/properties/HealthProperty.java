package engine.sprites.properties;

/**
 * Class that is used in every Sprite that has health. Can be upgraded with money
 * @author ryanpond
 * @author Katie Van Dyk
 */
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
     * Called when health is lost
     * @param healthLost : how much health is lost
     */
    public void loseHealth(double healthLost) {
	myHealth-=healthLost;
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
