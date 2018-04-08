package authoring;

import authoring.frontend.AuthoringView;
import frontend.StageManager;
import javafx.scene.image.ImageView;

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
	
    /**
     * Instatiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }

}