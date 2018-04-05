package gameplayer.screen;

import javafx.scene.Parent;

/**
 * 
 * @author Ben Hodgson 3/27/18
 *
 * Interface used for generating Screen objects, which represent the entirety of the view 
 * displayed to the user. 
 */

public abstract class Screen {
    
    /**
     * Returns the Screen object to be loaded on the screen
     */
    public abstract Parent getScreenRoot();
    
    /**
     * Creates the Screen 
     */
    public abstract void makeScreen();

}
