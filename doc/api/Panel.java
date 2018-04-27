package doc.api;

import javafx.scene.Node;

/**
 * 
 * @author Ben Hodgson 3/27/18
 *
 * Interface for generating Panel objects, displays that make up only a part of the Screen 
 * object displayed to the user. For example, the menu pop up window would be considered a 
 * Panel.
 */

public interface Panel {

    /**
     * Returns the panel object to be loaded on the screen
     */
    public Node getPanel();
    
    /**
     * Creates the panel
     */
    public void makePanel();
}
