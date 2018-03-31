package authoring.frontend;
import javafx.scene.Scene;

/**
 * 
 * @author API - Ben Hodgson 
 * 
 * Interface used for generating Screen objects, which represent the entirety of the view 
 * displayed to the user. 
 */

public interface Screen {
    
    /**
     * Creates the Screen
     */
    public void makeScreen();
    
    /**
     * Returns the Screen object to be loaded on the screen
     */
    public Scene getScreen();

}