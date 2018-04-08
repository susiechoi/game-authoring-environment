package engine.sprites.properties;

/**
 * Class that is used in every Sprite that has health. Can be upgraded with money
 * @author ryanpond
 * @author Katie Van Dyk
 */
public class HealthProperty extends UpgradeProperty {


    /**
     * Health property determines how much health a sprite has left
     * 
     * @param cost: cost of the upgrade
     * @param value: how much the user's health increments per upgrade
     * @param health: baseline health value
     */
    public HealthProperty(double cost, double value, double health) {
	super(cost, value, health);
    }
    
    /**
     * Called when health is lost
     * @param healthLost : how much health is lost
     */
    public void loseHealth(double healthLost) {
	double newValue = this.getProperty() - healthLost;
	this.setProperty(newValue);
    }
    
    /**
     * Return whether or not character is alive
     * 
     * @return boolean: True if character is alive, false otherwise
     */
    public boolean isAlive() {
	return this.getProperty() > 0;
    }
}
