package controller;

import authoring.AuthoringController;
import engine.EngineController;
import javafx.stage.Stage;

/**
 * 
 * @author Ben Hodgson 3/27/18
 *
 * Class that handles mediating program functionality between other, more specified controller
 * objects. 
 */

public class ChiefController {

    private Stage STAGE;
    private EngineController ENGINE;
    private AuthoringController AUTHORING;

    public ChiefController(Stage stage) {
	// TODO instantiate instance variables in the constructor
	STAGE = stage;
    }
    
    /**
     * Starts the application. Launches the user interfaces and waits for user input
     */
    public void start() {
	
    }

    /**
     * Instantiate a new EngineController object to handle the Game engine
     */
    public void play() {
	//ENGINE = new EngineController();
    }
    
    /**
     * Instantiate a new AuthoringController object to handle the authoring environment
     */
    public void author() {
	AUTHORING = new AuthoringController(STAGE);
    }
}
