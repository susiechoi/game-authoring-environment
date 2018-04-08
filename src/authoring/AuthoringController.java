/**
 * 
 * @author susiechoi 
 *
 * Class that handles mediating creation of authoring environment objects (towers, enemies, path). 
 * Represents Controller in MVC of the authoring environment. 
 * 
 */

package authoring;

import java.util.List;

import authoring.frontend.AuthoringView;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import frontend.StageManager;
import javafx.scene.image.Image;

public class AuthoringController {
	
	private AuthoringView myAuthoringView; 
	private AuthoringModel myAuthoringModel; 
	
	public AuthoringController(StageManager stageManager, String languageIn) {
		myAuthoringModel = new AuthoringModel();
		myAuthoringView = new AuthoringView(stageManager, languageIn, this);
	}
	
	/**
	 * Method through which information about object fields can be requested
	 * Invoked when populating authoring frontend screens used to edit existing objects
	 */
	public String getObjectAttribute(int level, String objectType, String name, String attribute) {
		return myAuthoringModel.getObjectAttribute(level, objectType, name, attribute); 
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit an enemy object in Authoring Model;
	 */
	public void makeEnemy(int level, boolean newObject, String name, Image image, double speed, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) {
		myAuthoringModel.makeEnemy(level, newObject, name, image, speed, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit a tower object in Authoring Model;
	 * @throws NoDuplicateNamesException 
	 */
	public void makeTower(int level, boolean newObject, String name, Image image, double health, double healthUpgradeCost, double healthUpgradeValue,
							Image projectileImage, double projectileDamage, double projectileUpgradeCost, double projectileUpgradeValue,
							double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) throws NoDuplicateNamesException {
		myAuthoringModel.makeTower(level, newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, projectileDamage, projectileUpgradeCost, projectileUpgradeValue, 
				launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit the Resources object in Authoring Model;
	 */
	public void makeResources(double startingHealth, double starting$) {
		myAuthoringModel.makeResources(startingHealth, starting$);
	}
	
	// TODO
	/**
	 * Method through which information can be sent to instantiate or edit a Path in Authoring Model
	 */
	public void makePath(int level) {
		myAuthoringModel.makePath(level); 
	}
	
	/**
	 * Method through which information can be retrieved from AuthoringMOdel re: the current objects of a given type are available for editing
	 */
	public List<String> getCurrentObjectOptions(int level, String objectType) {
		return myAuthoringModel.getCurrentObjectOptions(level, objectType);
	}
	
    /**
     * Instatiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }

}

