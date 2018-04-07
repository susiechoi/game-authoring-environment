package engine.sprites.properties;

/**
 * Used as the interface for the Property abstract class. Has upgrade and getProperty methods
 * @author Katie Van Dyk
 * @author ryanpond
 *
 */
public interface PropertyI {
    
    /**
     * Upgrades the property
     * @param balance: current monetary balance
     * @return : returns the new balance (same if not enough money to upgrade)
     */
    public double upgrade(double balance);
    
    public double getProperty();
    
}
