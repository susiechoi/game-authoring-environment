package doc.api;

import javafx.scene.Node;

/**
 * 
 * @author Ben Hodgson 3/27/18
 *
 * Interface used for generating Screen objects, which represent the entirety of the view 
 * displayed to the user. 
 */

public interface Screen {
    
    /**
     * Returns the Screen object to be loaded on the screen
     */
    public Node getScreen();
    
    /**
     * Creates the Screen
     */
    public void makeScreen();

}
