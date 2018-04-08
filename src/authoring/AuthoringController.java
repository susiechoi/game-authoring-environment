package authoring;

import authoring.frontend.AuthoringView;
import frontend.StageManager;
import javafx.scene.image.ImageView;
import java.io.File;

import authoring.AuthoringModel;
import authoring.AuthoringView;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * 
 * @author susiechoi 
 *
 * Class that handles mediating program functionality specific to authoring. 
 * Represents Controller in MVC of the authoring environment. 
 */

public class AuthoringController {

	
	private AuthoringView myAuthoringView; 
	private AuthoringModel myAuthoringModel; 
	
	public AuthoringController(StageManager stageManager, String languageIn) {
		myAuthoringModel = new AuthoringModel();
		myAuthoringView = new AuthoringView(stageManager, languageIn, this);
	}
	
	public String getObjectAttribute(String objectType, String name, String attribute) {
		return myAuthoringModel.getObjectAttribute(objectType, name, attribute); 
	}
	
	public void makeEnemy(int level, boolean newObject, String name, String image, double speed, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) {
		myAuthoringModel.makeEnemy(level, newObject, name, image, speed, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
	}
	
	public void makeTower(int level, boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
							String projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue,
							double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
		myAuthoringModel.makeTower(level, newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, projectileDamage, projectileValue, projectileUpgradeCost, projectileUpgradeValue, 
				launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
	}
	
	public void makeResources(double startingHealth, double starting$) {
		myAuthoringModel.makeResources(startingHealth, starting$);
	}
	
	// TODO 
	public void makePath(int level) {
		myAuthoringModel.makePath(level); 
	}
	
=======

    private final Stage STAGE;
    private final AuthoringView VIEW = new AuthoringView();
    private final AuthoringModel MODEL = new AuthoringModel();
   
    public AuthoringController(Stage programStage) {
	STAGE = programStage;
	Scene startScreen = VIEW.loadAuthoringView();
	STAGE.setScene(startScreen);
    }
    
    public void show() {
	STAGE.show();
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

>>>>>>> 5e7fe5968ce3b60c06cee8f832af88e887074ac5
    /**
     * Instatiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }

}