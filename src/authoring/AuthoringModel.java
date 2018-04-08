/**
 * 
 * @author susiechoi 
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller to determine what object to create/adjust
 * 
 */

package authoring;

import java.lang.reflect.Field;
import java.util.List;

import authoring.frontend.exceptions.NoDuplicateNamesException;

class AuthoringModel {
	
	protected AuthoringResources myResources;

	/**
	 * Method through which information can be sent to instantiate or edit an enemy object
	 * Wraps constructor in case of new object creation
	 */
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

	/**
	 * Method through which information can be sent to instantiate or edit a tower object
	 * Wraps constructor in case of new object creation
	 */
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
	/**
	 * Method through which information can be sent to instantiate or edit a path object
	 * Wraps constructor in case of new object creation
	 */
	public void makePath(int level) {

	}
	
	/**
	 * Method through which information can be sent to instantiate or edit a path object
	 * Wraps constructor in case of new object creation
	 */
	public void makeResources(double startingHealth, double starting$) {
		myResources = new AuthoringResources(startingHealth, starting$);
	}
	
	// TODO POPULATE RETURN LIST WITH EXISTING OBJECTS AT THAT LEVEL 
	/**
	 * Method through which SpecifyScreens can get information about existing objects that designers may have the option of editing
	 * @param level - level that the user wants to edit
	 * @param objectType - type of object that the user wants to edit
	 * @return List of String names of objects 
	 */
	public List<String> getCurrentObjectOptions(int level, String objectType) {
		return null; 
	}

	// TODO once maps have been made 
	/**
	 * Used in the case that the user wants to edit an existing object:
	 * Populates fields with current attributes of object 
	 * @param objectType - type of object being manipulated
	 * @param name - name of object being manipulated
	 * @param attribute - attribute/field of object being manipulated
	 * @return requested attribute in String form: used in populating textfield, finding correct dropdown option, etc.
	 */
	public String getObjectAttribute(int level, String objectType, String name, String attribute) {
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
