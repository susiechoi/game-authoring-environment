/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/8/18
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller to determine what object to create/adjust
 * 
 */

package authoring;


import java.io.File;
import java.lang.Double; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import data.GameData;
import engine.builders.LauncherBuilder;
import engine.builders.EnemyBuilder;
import engine.builders.ProjectileBuilder;
import engine.builders.TowerBuilder;
import engine.level.Level;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import frontend.PropertiesReader;
import javafx.scene.image.Image;

public class AuthoringModel implements GameData {

	public static final String DEFAULT_ENEMY_IMAGES = "images/EnemyImageNames.properties";
	public static final String DEFAULT_TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final String DEFAULT_PROJECTILE_IMAGES = "images/ProjectileImageNames.properties";
	public static final String DEFAULT_TOWER_FILEPATH = "default_objects/GenericTower.properties";
	public static final String DEFAULT_ENEMY_FILEPATH = "default_objects/GenericEnemy.properties";
	public static final String DEFAULT_NAME = "Default";

	private final PropertiesReader myPropertiesReader;
	protected AuthoringResources myResources;
	private Map<Integer, Level> myLevels;
	private Tower myDefaultTower;
	private Enemy myDefaultEnemy;

	public AuthoringModel() throws MissingPropertiesException {
		myLevels = new HashMap<Integer, Level>();
		myPropertiesReader = new PropertiesReader();
		try {
			myDefaultTower = generateGenericTower();
			myDefaultEnemy = generateGenericEnemy();
		} catch (NumberFormatException | FileNotFoundException e) {
			throw new MissingPropertiesException(DEFAULT_NAME);
		}
	}

	/**
	 * Method through which information can be sent to instantiate or edit an enemy object
	 * Wraps constructor in case of new object creation
	 * @throws MissingPropertiesException 
	 * @throws NoDuplicateNamesException 
	 * @throws ObjectNotFoundException 
	 */
	public void makeEnemy(int level, boolean newObject, String name, String image, double speed, double initialHealth, double healthImpact,
			double killReward, double killUpgradeCost, double killUpgradeValue) throws MissingPropertiesException, NoDuplicateNamesException, ObjectNotFoundException {
		Level currentLevel = levelCheck(level);
		if (currentLevel.containsEnemy(name) && newObject) {
			throw new NoDuplicateNamesException(name);
		}
		else {
			if (!newObject) {
				throw new ObjectNotFoundException(name);
			}
		}
		Image enemyImage = new Image((new File(myPropertiesReader.findVal(DEFAULT_ENEMY_IMAGES, image)).toURI().toString()), 50, 50, false, false);
		Enemy newEnemy = new EnemyBuilder().construct(name, enemyImage, speed, initialHealth, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
		currentLevel.addEnemy(name, newEnemy);
	}

	/**
	 * Method through which information can be sent to instantiate or edit a tower object
	 * Wraps constructor in case of new object creation
	 * @throws NoDuplicateNamesException: if the user tries to make an already existing
	 * tower, throw exception.
	 * @throws MissingPropertiesException 
	 * @throws ObjectNotFoundException 
	 */
	public void makeTower(int level, boolean newObject, String name, String imagePath, double health, double healthUpgradeCost, double healthUpgradeValue,
			String projectileImagePath, double projectileDamage, double projectileUpgradeCost, double projectileUpgradeValue, double projectileSpeed, 
			double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) throws NoDuplicateNamesException, MissingPropertiesException, ObjectNotFoundException {
		Level currentLevel = levelCheck(level);
		if (currentLevel.containsTower(name) && newObject) {
			throw new NoDuplicateNamesException(name);
		}
		else {
			if (!newObject) {
				throw new ObjectNotFoundException(name);
			}
		}
		Image projectileImage = new Image((new File(myPropertiesReader.findVal(DEFAULT_PROJECTILE_IMAGES, projectileImagePath)).toURI().toString()), 50, 50, false, false);
		Projectile towerProjectile = new ProjectileBuilder().construct(name, 
				projectileImage, projectileDamage, projectileUpgradeCost, 
				projectileUpgradeValue);
		Launcher towerLauncher = new LauncherBuilder().construct(launcherSpeed,  
				launcherUpgradeCost, launcherValue, launcherRange, launcherUpgradeCost, 
				launcherValue, towerProjectile); 
		Image image = new Image((new File(myPropertiesReader.findVal(DEFAULT_ENEMY_IMAGES, imagePath)).toURI().toString()), 50, 50, false, false);
		Tower newTower = new TowerBuilder().construct(name, image, 50, health,  // TODO put size SOMEWHERE
				healthUpgradeValue, healthUpgradeCost, towerLauncher);
		currentLevel.addTower(name, newTower);
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
	 * Method through which SpecifyScreens can get information about existing objects that designers may have the option of editing
	 * @param level - level that the user wants to edit
	 * @param objectType - type of object that the user wants to edit
	 * @return List of String names of objects 
	 * @throws ObjectNotFoundException 
	 */
	public List<String> getCurrentObjectOptions(int level, String objectType) throws ObjectNotFoundException {
		List<String> listToReturn = new ArrayList<String>(); 
		Level currentLevel = levelCheck(level);
		if (objectType.equals("Enemy")) {
			listToReturn = currentLevel.getAllEnemies();  // TODO replace with implementation of enemies within levels
		} else if (objectType.equals("Tower")) {
			listToReturn = currentLevel.getAllTowers();
		}
		return listToReturn; 
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
	 * @throws ObjectNotFoundException 
	 */
	public String getObjectAttribute(int level, String objectType, String name, String attribute) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
		Level currentLevel = levelCheck(level);
		Field field; 
		Object fieldValue = null; 
		if (objectType.equals("Enemy")) {
			if (currentLevel.containsEnemy(name)) {
				Enemy enemy = currentLevel.getEnemy(name);
				Class enemyClass = enemy.getClass(); 
				field = enemyClass.getField(attribute);
				fieldValue = field.get(enemy);
			}
			else {
				throw new ObjectNotFoundException(name);
			}
		}
		else if (objectType.equals("Tower")) {
			if (currentLevel.containsTower(name)) {
				Tower tower = currentLevel.getTower(name);
				Class towerClass = tower.getClass(); 
				field = towerClass.getField(attribute);
				fieldValue = field.get(tower);
			}
		}
		if (fieldValue.getClass() == Double.class) {
			return Double.toString((double) fieldValue); 
		}
		else return (String) fieldValue; 
	}

	public Level levelCheck(int level) throws ObjectNotFoundException {
		Level currentLevel = myLevels.get(level);
		if (currentLevel == null) {
			throw new ObjectNotFoundException("Level "+level);
		}
		return currentLevel;
	}

	/**
	 * Method through which information can be sent to instantiate or edit a path object
	 * Wraps constructor in case of new object creation
	 */
	public void makeResources(double startingHealth, double starting$) {
		myResources = new AuthoringResources(startingHealth, starting$);
	}

	/**
	 * Reads information from GenericTower.properties file to create a default
	 * Tower object to be used to populate user input fields.
	 * 
	 * @return Tower: a generic tower with attribute read in from .properties file
	 */
	private Tower generateGenericTower() throws NumberFormatException, FileNotFoundException {
		try {
			double projectileSize = Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileSize"));
			Projectile towerProjectile = new ProjectileBuilder().construct(
					DEFAULT_NAME,  
					new Image(new FileInputStream(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileImage")), 
							projectileSize, projectileSize, false, false),
					// TODO add projectile speed !!!!
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileDamage")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileUpgradeCost")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileUpgradeValue")));
			Launcher towerLauncher = new LauncherBuilder().construct(
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherSpeed")),  
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherUpgradeCost")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherValue")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherRange")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherUpgradeCost")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherValue")), towerProjectile);  
			double towerSize = Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerSize"));
			Tower newTower = new TowerBuilder().construct(
					DEFAULT_NAME, 
					new Image(new FileInputStream(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerImage")), 
							towerSize, towerSize, false, false), 
					towerSize, 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealth")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealthUpgradeValue")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealthUpgradeCost")), 
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
			double enemySize = Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH, "enemySize"));
			Enemy newEnemy = new EnemyBuilder().construct(
					DEFAULT_NAME, 
					new Image(new FileInputStream(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH, "enemyImage")), 
							enemySize, enemySize, false, false), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemySpeed")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyHealth")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyHealthImpact")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyKillReward")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyKillUpgradeCost")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyKillUpgradeValue")));
			return newEnemy;

		} catch (MissingPropertiesException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not load GenericTower object!");
		}
		return null;
	}
}