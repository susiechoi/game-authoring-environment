package usecases;

import java.io.File;
import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;

/**
 * 
 * @author (populated methods) Susie Choi 
 * @author (interface) Ben Hodgson
 *
 * Class that handles mediating program functionality specific to authoring. Adheres to the 
 * model-view-controller design patter. 
 */

class AuthoringControllerExample {
	
	private AuthoringViewExample myView; 
	private AuthoringModelExample myModel; 

	public AuthoringControllerExample() {
		FileIOExample fileIO = new FileIOExample(); 
		AuthoringViewExample myView = new AuthoringViewExample(fileIO);
		AuthoringModelExample myModel = new AuthoringModelExample(); 
		setUpListeners(); 
	}
	
	private void setUpListeners() {
		myView.myUserActed().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean t1, Boolean t2) {
				myModel.applyChange(myView.getMyAffectedObject(), myView.getChangedField(), myView.getUserSelection());
			}
		});
	}
	
    /**
     * Loads a new Scene object in the program's Stage to display the authoring environment 
     * screen.
     * 
     * @return Scene: the authoring screen to be displayed to the user
     */
    public Scene loadAuthoringStage() {
	return null;	
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

}