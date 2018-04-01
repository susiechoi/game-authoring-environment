package engine.path;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Interface for building a path.
 */
public interface Path {
    
    /**
     * Adds a start to the path. A path start point represents where enemies spawn.
     */
    public void addStart();

    /**
     * Adds an end to the path. A path end point represents where enemies progress towards.
     * Enemies that pass the end point negatively impact a player. 
     */
    public void setEnd();
    
    /**
     * Checks to make sure the path is valid by checking that the start points all connect
     * to the end of the path.
     * 
     * @return boolean: true if the path's start and end points connect, false otherwise
     */
    public boolean pathConnects();
}
