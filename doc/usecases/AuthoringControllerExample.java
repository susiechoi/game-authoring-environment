package usecases;

import java.io.File;
import javafx.scene.Scene;

/**
 * 
 * @author Ben Hodgson 3/27/18
 *
 * Class that handles mediating program functionality specific to authoring. Adheres to the 
 * model-view-controller design patter. 
 */

class AuthoringControllerExample implements ControllerExample {

    /**
     * Loads a new Scene object in the program's Stage to display the authoring environment 
     * screen.
     * 
     * @return Scene: the authoring screen to be displayed to the user
     */
    public Scene loadAuthoringStage() {
	
    }  
    
    /**
     * Locates the file in the program file system that contains data required to load 
     * a game. Uses the FileIO objects methods to loadState().
     * 
     * @return File: the file containing information required to load the start of a game
     */
    public File loadStartState() {
	
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

}
