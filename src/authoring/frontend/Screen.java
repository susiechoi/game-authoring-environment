package authoring.frontend;
import javafx.scene.Scene;

/**
 * 
 * @author API - Ben Hodgson 
 * 
 * Interface used for generating Screen objects, which represent the entirety of the view 
 * displayed to the user. 
 */

public abstract class Screen {
	private Scene myScreen;
    
	public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
	
    /**
     * Creates the Screen
     */
    public abstract void makeScreen();
    
    /**
     * Returns the Scene object to be loaded on the screen
     */
    public Scene getScreen() {
    	if (myScreen == null) {
			makeScreen(); 
		}
		return myScreen; 
    }
    protected void setScreen(Scene newScreen) {
    		myScreen = newScreen;
    }
}