package doc.api;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 * Interface used to determine which event should trigger a tower to fire
 */

public interface Trigger {

    /**
     * Method to trigger the tower to fire
     */
    public void launch();
}
