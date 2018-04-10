package controller;

import authoring.AuthoringController;
import frontend.MainScreen;
import frontend.StageManager;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Ben Hodgson 3/27/18
 * @author Katherine Van Dyk 4/6/18
 *
 * Class that handles mediating program functionality between other, more specified controller
 * objects. 
 */

public class ChiefController {
    
    private final StageManager STAGE_MANAGER;
   // private AuthoringController AUTHORING;

    public ChiefController(Stage stage) {
	// TODO instantiate instance variables in the constructor
	STAGE_MANAGER = new StageManager(stage);
    }
    
    /**
     * Starts the application. Launches the user interfaces and waits for user input
     */
    public void start() {
	MainScreen mainScreen  = new MainScreen(STAGE_MANAGER);
	Scene scene = new Scene(mainScreen.getScreen());
	STAGE_MANAGER.switchScene(scene);
    }

    /**
     * Instantiate a new EngineController object to handle the Game engine
     */
    public void play() {
    }
    
    /**
     * Instantiate a new AuthoringController object to handle the authoring environment
     */
    public void author() {
	new AuthoringController(STAGE_MANAGER, DEFAULT_LANGUAGE);
    }

}
