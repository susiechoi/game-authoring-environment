package player;

/**
 * 
 * @author Ben Hodgson 3/28/18
 * 
 * Interface for a game player
 */

public interface Player {
    
    /**
     * Sets the player's health
     * 
     * @param health: the health to give the player
     */
    public void setHealth(double health);
    
    /**
     * Used to change the player's health by the specified amount. 
     * 
     * @param health: the amount to change the player's health
     */
    public void changeHealth(double health);
    
    /**
     * Adds points to the player's point total
     * 
     * @param points: the points to give the player
     */
    public void addPoints(int points);

}
