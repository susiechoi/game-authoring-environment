package authoring;

import java.io.File;
import controller.Controller;
import javafx.scene.Scene;

/**
 * 
 * @author Ben Hodgson 3/29/18
 * @author susiechoi 
 *
 * Class that handles mediating program functionality specific to authoring. 
 * Represents Controller in MVC of the authoring environment. 
 */

class AuthoringController implements Controller {

    private final AuthoringView VIEW = new AuthoringView();
   
    public AuthoringController() {
	
    }
    
    /**
     * Loads a new Scene object in the program's Stage to display the authoring environment 
     * screen.
     * 
     * @param Scene: the authoring screen to be displayed to the user
     */
    public void loadAuthoringStage(Scene screen) {
	// TODO load this onto the stage. How do we receive access to the stage?	
    }  

    /**
     * Locates the file in the program file system that contains data required to load 
     * a game. Uses the FileIO objects methods to loadState().
     * 
     * @return File: the file containing information required to load the start of a game
     */
    public File loadStartState() {
	return null;	
    }

    /**
     * Saves user data from the authoring environment in a temporary file to avoid 
     * overwriting data before the user is ready to save completely. 
     * Uses the FileIO objects methods to saveState().
     */
    public void saveTemporaryState() {

    }

    /**
     * Instatiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }
    
    private void setUpListeners() {

    }

}