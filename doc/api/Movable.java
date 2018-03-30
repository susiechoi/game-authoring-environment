package api;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Interface to handle moving an enemy or game object
 */
public interface Movable {

    /**
     * Moves the object that implements the interface to the specified newX and newY 
     * positions
     * 
     * @param newX: the new x position to move the enemy/object to
     * @param newY: the new y position to move the enemy/object to
     */
    public void move(int newX, int newY);
    
    /**
     * 
     * @param angle
     */
    public void rotate(double angle);
}
