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
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import frontend.StageManager;

public class AuthoringController {
	
	private AuthoringView myAuthoringView; 
	private AuthoringModel myAuthoringModel; 
	
	public AuthoringController(StageManager stageManager, String languageIn) {
		myAuthoringView = new AuthoringView(stageManager, languageIn, this);
		try {
			myAuthoringModel = new AuthoringModel();
		} catch (MissingPropertiesException e) {
			myAuthoringView.loadErrorScreen("NoDefaultObject");
		}
		myAuthoringView.loadInitialScreen();
	}
	
	/**
	 * Method through which information about object fields can be requested
	 * Invoked when populating authoring frontend screens used to edit existing objects
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws ObjectNotFoundException 
	 */
	public String getObjectAttribute(int level, String objectType, String name, String attribute) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
		return myAuthoringModel.getObjectAttribute(level, objectType, name, attribute);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit an enemy object in Authoring Model;
	 * @throws MissingPropertiesException 
	 * @throws ObjectNotFoundException 
	 * @throws NoDuplicateNamesException 
	 */
	public void makeEnemy(int level, boolean newObject, String name, String image, double speed, double initialHealth, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) throws MissingPropertiesException, NoDuplicateNamesException, ObjectNotFoundException {
		myAuthoringModel.makeEnemy(level, newObject, name, image, speed, initialHealth, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit a tower object in Authoring Model;
	 * @throws NoDuplicateNamesException 
	 * @throws MissingPropertiesException 
	 * @throws ObjectNotFoundException 
	 */
	public void makeTower(int level, boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
							String projectileImage, double projectileDamage, double projectileUpgradeCost, double projectileUpgradeValue, double projectileSpeed,
							double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) throws NoDuplicateNamesException, MissingPropertiesException, ObjectNotFoundException {
		myAuthoringModel.makeTower(level, newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, projectileDamage, projectileUpgradeCost, projectileUpgradeValue, projectileSpeed,
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
	 * @throws ObjectNotFoundException 
	 */
	public List<String> getCurrentObjectOptions(int level, String objectType) throws ObjectNotFoundException {
		return myAuthoringModel.getCurrentObjectOptions(level, objectType);
	}
	
    /**
     * Instantiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }

	public int addNewLevel() {
		return myAuthoringModel.addNewLevel(); 
	}
	
	public List<String> getLevels() {
		return myAuthoringModel.getLevels(); 
	}

	public int autogenerateLevel() {
		return myAuthoringModel.autogenerateLevel(); 
	}

}

