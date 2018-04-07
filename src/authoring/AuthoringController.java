package authoring;

import authoring.frontend.AuthoringView;
import authoring.frontend.CustomizeLevelScreen;
import frontend.StageManager;

/**
 * 
 * @author susiechoi 
 *
 * Class that handles mediating program functionality specific to authoring. 
 * Represents Controller in MVC of the authoring environment. 
 */

public class AuthoringController {
	
	private AuthoringView myAuthoringView; 
	
	public AuthoringController(StageManager stageManager, String languageIn) {
		myAuthoringView = new AuthoringView(stageManager, languageIn);
	}
	
	// TODO is int level needed as a parameter? 
	public void getObjectAttribute(String objectType, String objectName, String attribute) {
		// TODO wrap a call to get the info from AuthoringModel maps
	}
	
	public void changeExistingObject(String objectType, String objectName, String attribute) {
		
	}
	
	public void addNewTower() {
		
	}
	
	public void addNewEnemy() {
		
	}
	
	public void addNewPath() {
		
	}
	
    /**
     * Instatiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }

}