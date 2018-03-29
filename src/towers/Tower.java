package towers;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 * Interface for Tower objects in the game. 
 */

public interface Tower {
    
    /**
     * Sets the tower's health
     * 
     * @param health: the health to give the tower
     */
    public void setHealth(double health);
    
    /**
     * Sets the tower's cost
     * 
     * @param cost: the cost for the tower     
     */
    public void setCost(double cost);
    
    /**
     * handle upgrading the tower
     */
    public void upgrade();

}
