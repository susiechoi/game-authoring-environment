/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/8/18
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller to determine what object to create/adjust
 * 
 */

package authoring;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.frontend.exceptions.NoDuplicateNamesException;
import engine.builders.LauncherBuilder;
import engine.builders.ProjectileBuilder;
import engine.builders.TowerBuilder;
import engine.sprites.enemies.Enemy;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.Image;

class AuthoringModel {

    protected AuthoringResources myResources;
    private Map<String, Tower> myTowers;
    private Map<String, Enemy> myEnemies;

    public AuthoringModel() {
	myTowers = new HashMap<String, Tower>();
	myEnemies = new HashMap<String, Enemy>();
    }


    /**
     * Method through which information can be sent to instantiate or edit an enemy object
     * Wraps constructor in case of new object creation
     */
    public void makeEnemy(int level, boolean newObject, String name, Image image, double speed, double healthImpact,
	    double killReward, double killUpgradeCost, double killUpgradeValue) {
	if (newObject) {
	   
	}
	else {
	    // find the enemy in the enemies map with the name parameter
	    // edit its values to conform to the parameterized ones 
	}
    }

    /**
     * Method through which information can be sent to instantiate or edit a tower object
     * Wraps constructor in case of new object creation
     * @throws NoDuplicateNamesException: if the user tries to make an already existing
     * tower, throw exception.
     */
    public void makeTower(int level, boolean newObject, String name, Image image, double health, double healthUpgradeCost, double healthUpgradeValue,
	    Image projectileImage, double projectileDamage, double projectileUpgradeCost, double projectileUpgradeValue,
	    double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) throws NoDuplicateNamesException {
	if (myTowers.containsKey(name)) {
	    // build projectile, launcher, then tower using builder objects
	    throw new NoDuplicateNamesException(name);
	}
	else {
	    Projectile towerProjectile = new ProjectileBuilder().construct(name, 
		    projectileImage, projectileDamage, projectileUpgradeCost, 
		    projectileUpgradeValue);
	    Launcher towerLauncher = new LauncherBuilder().construct(launcherSpeed,  
		    launcherUpgradeCost, launcherValue, launcherRange, launcherUpgradeCost, 
		    launcherValue, towerProjectile); 
	    // TODO set default image size for towers SOMEWHERE ELSE
	    double size = 20; 
	    Tower newTower = new TowerBuilder().construct(name, image, size, health, 
		    healthUpgradeValue, healthUpgradeCost, towerLauncher);
	    myTowers.put(name, newTower);
	}
    }

    // TODO 
    /**
     * Method through which information can be sent to instantiate or edit a path object
     * Wraps constructor in case of new object creation
     */
    public void makePath(int level) {
	//TODO: still empty;
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
	    if (myEnemies.containsKey(name)) {
		Enemy enemy = myEnemies.get(name);
		// field = enemy.getField(attribute);
		// fieldValue = field.get(enemyObject)
	    }
	}
	else if (objectType.equals("Tower")) {
	    if (myTowers.containsKey(name)) {
		// field = tower.getField(attribute) 
		// fieldValue = field.get(towerObject)
	    }
	}
	return (String) fieldValue; 
    }

}
