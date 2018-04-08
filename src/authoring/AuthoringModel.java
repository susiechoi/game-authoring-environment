

package authoring;

import java.util.List;

import engine.level.Level;
import engine.path.Path;

/**
 * 
 * @author Susie Choi 
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller through applyChange method regarding what/how to change an object
 * 
 */
<<<<<<< HEAD


package authoring;

import java.lang.reflect.Field;

import authoring.frontend.exceptions.NoDuplicateNamesException;

class AuthoringModel {
=======
public class AuthoringModel {
    
    public AuthoringModel() {
>>>>>>> 2a8a4bf02a4135fd2cf2ab3ce517d07f39df9e65
	
	protected AuthoringResources myResources;

	public void makeEnemy(int level, boolean newObject, String name, String image, double speed, double healthImpact,
			double killReward, double killUpgradeCost, double killUpgradeValue) {
		if (newObject) {
			// if the enemies map already contains key with the name parameter, throw NoDuplicateNamesException
			// else add to map 
		}
		else {
			// find the enemy in the enemies map with the name parameter
			// edit its values to conform to the parameterized ones 
		}
	}

	public void makeTower(int level, boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
			String projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue,
			double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
		if (newObject) {
			// if the tower map already contains key with the name parameter, throw NoDuplicateNamesException
			// else add to map 
		}
		else {
			// find the tower in the towers map with the name parameter
			// edit its values to conform to the parameterized ones 
		}
	}

	// TODO 
	public void makePath(int level) {

	}
	
	public void makeResources(double startingHealth, double starting$) {
		myResources = new AuthoringResources(startingHealth, starting$);
	}

	// TODO once maps have been made 
	public String getObjectAttribute(String objectType, String name, String attribute) {
		Field field; 
		Object fieldValue = null; 
		if (objectType.equals("Enemy")) {
			// get the enemy object with the appropriate name
			// field = enemy.getField(attribute) 
			// fieldValue = field.get(enemyObject)
		}
		else if (objectType.equals("Tower")) {
			// get the tower object with the appropriate name
			// field = tower.getField(attribute) 
			// fieldValue = field.get(towerObject)
		}
		return (String) fieldValue; 
	}

}
