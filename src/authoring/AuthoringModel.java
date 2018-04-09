/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/8/18
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller to determine what object to create/adjust
 * 
 */

package authoring;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import data.GameData;
import engine.builders.LauncherBuilder;
import engine.builders.EnemyBuilder;
import engine.builders.ProjectileBuilder;
import engine.builders.TowerBuilder;
import engine.level.Level;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.properties.HealthProperty;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import frontend.PropertiesReader;
import javafx.scene.image.Image;

class AuthoringModel {

    private final PropertiesReader READER = new PropertiesReader();
    private final String DEFAULT = "default";
    private final String defaultTowerPath = "GenericTower.properties";
    private final String defaultEnemyPath = "GenericEnemy.properties";
    protected AuthoringResources myResources;
    private Map<Integer, Level> myLevels;
    private Map<String, Enemy> myEnemies;
    private final Tower myDefaultTower;
    private final Enemy myDefaultEnemy;

    public AuthoringModel() throws NumberFormatException, FileNotFoundException {
	myLevels = new HashMap<Integer, Level>();
	myEnemies = new HashMap<String, Enemy>();
	myDefaultTower = generateGenericTower();
	myDefaultEnemy = generateGenericEnemy();
    }

    /**
     * Method through which information can be sent to instantiate or edit an enemy object
     * Wraps constructor in case of new object creation
     */
    public void makeEnemy(int level, boolean newObject, String name, Image image, double speed, double initialHealth, double healthImpact,
	    double killReward, double killUpgradeCost, double killUpgradeValue) {
	if (newObject) {
	    Enemy newEnemy = new EnemyBuilder().construct(name, image, speed, initialHealth, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
	    myEnemies.put(name, newEnemy);
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
	Level thisLevel;
	if (myLevels.containsKey(level)) {
	    thisLevel = myLevels.get(level);
	}
	else {
	    Path levelPath = makePath(level);
	    thisLevel = new Level(level, levelPath);
	}
	if (thisLevel.containsTower(name)) {
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
	    thisLevel.addTower(name, newTower);
	}
    }

    /**
     * Class to make a wave to be used in a specified level
     */
    public void makeWave() {

    }

    // TODO 
    /**
     * Method through which information can be sent to instantiate or edit a path object
     * Wraps constructor in case of new object creation
     */
    public Path makePath(int level) {
	return null;
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
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public String getObjectAttribute(int level, String objectType, String name, String attribute) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	Field field; 
	Object fieldValue = null; 
	if (objectType.equals("Enemy")) {
	    if (myEnemies.containsKey(name)) {
		Enemy enemy = myEnemies.get(name);
		Class enemyClass = enemy.getClass(); 
		field = enemyClass.getField(attribute);
		fieldValue = field.get(enemy);
	    }
	    else {

	    }
	}
	// TODO: fix!
	else if (objectType.equals("Tower")) {
	    if (myTowers.containsKey(name)) {
		Tower tower = myTowers.get(name);
		Class towerClass = tower.getClass(); 
		field = towerClass.getField(attribute);
		fieldValue = field.get(tower);
	    }
	}
	return (String) fieldValue; 
    }

    /**
     * Reads information from GenericTower.properties file to create a default
     * Tower object to be used to populate user input fields.
     * 
     * @return Tower: a generic tower with attribute read in from .properties file
     */
    private Tower generateGenericTower() throws NumberFormatException, FileNotFoundException {
	try {
	    Properties towerProperties = READER.loadProperties(defaultTowerPath);
	    Map<String, String> propertiesMap = READER.read(towerProperties);
	    double projectileSize = Double.parseDouble(propertiesMap.get("projectileSize"));
	    Projectile towerProjectile = new ProjectileBuilder().construct(
		    DEFAULT,  
		    new Image(new FileInputStream(propertiesMap.get("projectileImage")), 
			    projectileSize, projectileSize, false, false),
		    // TODO add projectile speed !!!!
		    Double.parseDouble(propertiesMap.get("projectileDamage")), 
		    Double.parseDouble(propertiesMap.get("projectileUpgradeCost")), 
		    Double.parseDouble(propertiesMap.get("projectileUpgradeValue")));
	    Launcher towerLauncher = new LauncherBuilder().construct(
		    Double.parseDouble(propertiesMap.get("launcherSpeed")),  
		    Double.parseDouble(propertiesMap.get("launcherUpgradeCost")), 
		    Double.parseDouble(propertiesMap.get("launcherValue")), 
		    Double.parseDouble(propertiesMap.get("launcherRange")), 
		    Double.parseDouble(propertiesMap.get("launcherUpgradeCost")), 
		    Double.parseDouble(propertiesMap.get("launcherValue")), towerProjectile);  
	    double towerSize = Double.parseDouble(propertiesMap.get("towerSize"));
	    Tower newTower = new TowerBuilder().construct(
		    DEFAULT, 
		    new Image(new FileInputStream(propertiesMap.get("towerImage")), 
			    towerSize, towerSize, false, false), 
		    towerSize, 
		    Double.parseDouble(propertiesMap.get("towerHealth")), 
		    Double.parseDouble(propertiesMap.get("towerHealthUpgradeValue")), 
		    Double.parseDouble(propertiesMap.get("towerHealthUpgradeCost")), 
		    towerLauncher);
	    return newTower;

	} 
	catch (MissingPropertiesException e) {
	    // TODO Auto-generated catch block
	    System.out.println("Could not load GenericTower object!");
	}
	return null;
    }

    /**
     * Reads information from GenericEnemy.properties file to create a default
     * Enemy object to be used to populate user input fields.
     * 
     * @return Enemy: a generic enemy with attribute read in from .properties file
     * @throws FileNotFoundException 
     * @throws NumberFormatException 
     */
    private Enemy generateGenericEnemy() throws NumberFormatException, FileNotFoundException {
	try {
	    Properties towerProperties = READER.loadProperties(defaultEnemyPath);
	    Map<String, String> propertiesMap = READER.read(towerProperties);
	    double enemySize = Double.parseDouble(propertiesMap.get("enemySize"));
	    Enemy newEnemy = new EnemyBuilder().construct(
		    DEFAULT, 
		    new Image(new FileInputStream(propertiesMap.get("enemyImage")), 
			    enemySize, enemySize, false, false), 
		    Double.parseDouble(propertiesMap.get("enemySpeed")), 
		    Double.parseDouble(propertiesMap.get("enemyHealth")), 
		    Double.parseDouble(propertiesMap.get("enemyHealthImpact")), 
		    Double.parseDouble(propertiesMap.get("enemyKillReward")), 
		    Double.parseDouble(propertiesMap.get("enemyKillUpgradeCost")), 
		    Double.parseDouble(propertiesMap.get("enemyKillUpgradeValue")));
	    return newEnemy;

	} catch (MissingPropertiesException e) {
	    // TODO Auto-generated catch block
	    System.out.println("Could not load GenericTower object!");
	}
	return null;
    }
}