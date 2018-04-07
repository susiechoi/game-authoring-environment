/**
 * 
 * @author Susie Choi 
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller through applyChange method regarding what/how to change an object
 * 
 */

package authoring;

class AuthoringModel {

	public void makeEnemy(boolean newObject, String name, String image, int speed, int healthImpact, int moneyImpact, int killReward, int killUpgradeCost, int killUpgradeValue) {
		if (newObject) {
			// if the enemies map already contains key with the name parameter, throw NoDuplicateNamesException
			// else add to map 
		}
		else {
			// find the enemy in the enemies map with the name parameter
			// edit its values to conform to the parameterized ones 
		}
	}

	public void makeTower(boolean newObject, String name, String image, int health, int healthUpgradeCost, int healthUpgradeValue,
			String projectileImage, String ability, int projectileDamage, int projectileValue, int projectileUpgradeCost, int projectileUpgradeValue,
			int launcherValue, int launcherUpgradeCost, int launcherUpgradeValue, int launcherSpeed, int launcherRange) {
		if (newObject) {
			// if the tower map already contains key with the name parameter, throw NoDuplicateNamesException
			// else add to map 
		}
		else {
			// find the tower in the towers map with the name parameter
			// edit its values to conform to the parameterized ones 
		}
	}

}
