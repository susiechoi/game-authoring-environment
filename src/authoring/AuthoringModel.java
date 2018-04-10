/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/8/18
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller to determine what object to create/adjust
 * 
 */

package authoring;

import java.awt.geom.Point2D;
import java.lang.Double; 
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
import engine.builders.PathBuilder;
import engine.Settings;
import engine.builders.EnemyBuilder;
import engine.builders.ProjectileBuilder;
import engine.builders.SettingsBuilder;
import engine.builders.TowerBuilder;
import engine.level.Level;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import frontend.PropertiesReader;
import javafx.scene.layout.GridPane;

public class AuthoringModel implements GameData {


	public static final String DEFAULT_ENEMY_IMAGES = "images/EnemyImageNames.properties";
	public static final String DEFAULT_TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final String DEFAULT_IMAGES_PREFIX = "images/";
	public static final String DEFAULT_IMAGES_SUFFIX = "ImageNames.properties";
	public static final String DEFAULT_PROJECTILE_IMAGES = "images/ProjectileImageNames.properties";
	public static final String DEFAULT_TOWER_FILEPATH = "default_objects/GenericTower.properties";
	public static final String DEFAULT_ENEMY_FILEPATH = "default_objects/GenericEnemy.properties";
	public static final String DEFAULT_PROMPTS = "languages/English/Prompts.properties";
	public static final String DEFAULT_CONSTANT_FILEPATH = "src/frontend/Constants.properties";
	private final String myDefaultName; 

	private String myGameName; 
	private final PropertiesReader myPropertiesReader;
	private Settings mySettings; 
	private Map<Integer, Level> myLevels;
	private Tower myDefaultTower;
	private Enemy myDefaultEnemy;
	private GridPane myGrid;
	private Path myPath;

	public AuthoringModel() throws MissingPropertiesException {
		myLevels = new HashMap<Integer, Level>();
		myPropertiesReader = new PropertiesReader();
		myDefaultName = myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "DefaultObjectName");
		setupDefaultSettings(); 
		try {
			myDefaultTower = generateGenericTower();
			myDefaultEnemy = generateGenericEnemy();
		} catch (NumberFormatException | FileNotFoundException e) {
			throw new MissingPropertiesException(myDefaultName);
		}
		setupDefaultLevel();
	}

	private void setupDefaultSettings() throws MissingPropertiesException {
		String defaultGameName = myPropertiesReader.findVal(DEFAULT_PROMPTS, "NewGame");
		int startingHealth = Integer.parseInt(myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "StartingHealth"));
		int startingMoney = Integer.parseInt(myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "StartingMoney"));
		myGameName = defaultGameName; 
		mySettings = new SettingsBuilder().construct(defaultGameName, startingHealth, startingMoney);
	}

	private void setupDefaultLevel() {
		Level firstLevel = new Level(1);
		firstLevel.addTower(myDefaultName, new Tower(myDefaultTower));
		firstLevel.addEnemy(myDefaultName, new Enemy(myDefaultEnemy));
		myLevels.put(1, firstLevel);
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
		else if (!currentLevel.containsEnemy(name) && !newObject) {
			throw new ObjectNotFoundException(name);
		}
//		Image enemyImage = new Image((new File(myPropertiesReader.findVal(DEFAULT_ENEMY_IMAGES, image)).toURI().toString()), 50, 50, false, false);
		Enemy newEnemy = new EnemyBuilder().construct(name, myPropertiesReader.findVal(DEFAULT_ENEMY_IMAGES, image), speed, initialHealth, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
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
			double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange,
			double towerValue, double towerUpgradeCost, double towerUpgradeValue) throws NoDuplicateNamesException, MissingPropertiesException, ObjectNotFoundException {
		Level currentLevel = levelCheck(level);
		if (currentLevel.containsTower(name) && newObject) {
			throw new NoDuplicateNamesException(name);
		}
		else if (!currentLevel.containsTower(name) && !newObject) {
			throw new ObjectNotFoundException(name);
		}
//		Image projectileImage = new Image((new File(myPropertiesReader.findVal(DEFAULT_PROJECTILE_IMAGES, projectileImagePath)).toURI().toString()), 50, 50, false, false);
		Projectile towerProjectile = new ProjectileBuilder().construct(name, 
				myPropertiesReader.findVal(DEFAULT_PROJECTILE_IMAGES, projectileImagePath), projectileDamage, projectileUpgradeCost, 
				projectileUpgradeValue, projectileSpeed);
		Launcher towerLauncher = new LauncherBuilder().construct(launcherSpeed,  
				launcherUpgradeCost, launcherValue, launcherRange, launcherUpgradeCost, 
				launcherValue, towerProjectile); 
//		Image image = new Image((new File(myPropertiesReader.findVal(DEFAULT_ENEMY_IMAGES, imagePath)).toURI().toString()), 50, 50, false, false);
		Tower newTower = new TowerBuilder().construct(name, myPropertiesReader.findVal(DEFAULT_TOWER_IMAGES, imagePath), 50, health,  // TODO put size SOMEWHERE
				healthUpgradeValue, healthUpgradeCost, towerLauncher, towerValue, towerUpgradeCost, towerUpgradeValue);
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

	//parameters needed to get passed: background image, grid size, location of each image in grid 

	public void makePath(int level, List<Point2D> coordinates, GridPane grid) {
		myGrid = grid;
		myPath = new PathBuilder().construct(level, coordinates);
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
			listToReturn = currentLevel.getAllEnemies();  
			if (listToReturn.size() == 0) {
				listToReturn.add(myDefaultEnemy.getName());
			}
		} else if (objectType.equals("Tower")) {
			listToReturn = currentLevel.getAllTowers();
			if (listToReturn.size() == 0) {
				listToReturn.add(myDefaultTower.getName());
			}
		}
		listToReturn.remove(myDefaultName);
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
		Field field; 
		Object fieldValue = null; 
		if (objectType.equals("Enemy")) {
			Level currentLevel = levelCheck(level);
			if (currentLevel.containsEnemy(name)) {
				Enemy enemy = currentLevel.getEnemy(name);
				for (Field aField : enemy.getClass().getDeclaredFields()) {
					String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(".")+1); 
					if (fieldSimpleString.equals(attribute)) {
						aField.setAccessible(true);
						fieldValue = aField.get(enemy);
						break; 
					}
				}
			}
			if (fieldValue == null) {
				throw new ObjectNotFoundException(name);
			}
		}
		else if (objectType.equals("Tower")) {
			Level currentLevel = levelCheck(level);
			if (currentLevel.containsTower(name)) {
				Tower tower = currentLevel.getTower(name);
				for (Field aField : tower.getClass().getDeclaredFields()) {
					String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(".")+1); 
					if (fieldSimpleString.equals(attribute)) {
						aField.setAccessible(true);
						fieldValue = aField.get(tower);
						break; 
					}
				}
			}
			if (fieldValue == null) {
				throw new ObjectNotFoundException(name);
			}
		}
		else if (objectType.equals("Settings")) {
			for (Field aField : mySettings.getClass().getDeclaredFields()) {
				String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(".")+1); 
				if (fieldSimpleString.equals(attribute)) {
					aField.setAccessible(true);
					fieldValue = aField.get(mySettings);
					System.out.println(fieldValue);
					break; 
				}
			}
		}
		if (fieldValue.getClass() == Double.class) {
			return Double.toString((double) fieldValue); 
		} 
		//		else if (fieldValue.getClass() == Image.class) {
		//			return myPropertiesReader.findKey(DEFAULT_IMAGES_PREFIX+objectType+DEFAULT_IMAGES_SUFFIX, fieldValue.);
		//		}
		else return (String) fieldValue; 
	}

	public Level levelCheck(int level) throws ObjectNotFoundException {
		Level currentLevel = myLevels.get(level);
		if (currentLevel == null) {
			throw new ObjectNotFoundException("Level "+level);
		}
		return currentLevel;
	}

	public List<Level> allLevels() {
		List<Level> ret = new ArrayList<Level>();
		for(Level level : myLevels.values()) {
			ret.add(level);
		}
		return ret;
	}

	/**
	 * Method through which information can be sent to instantiate or edit a path object
	 * Wraps constructor in case of new object creation
	 */
	public void makeResources(String gameName, double startingHealth, double starting$) {
		mySettings = new SettingsBuilder().construct(gameName, startingHealth, starting$);
	}

	/**
	 * Reads information from GenericTower.properties file to create a default
	 * Tower object to be used to populate user input fields.
	 * 
	 * @return Tower: a generic tower with attribute read in from .properties file
	 */
	private Tower generateGenericTower() throws NumberFormatException, FileNotFoundException {
		try {
//			double projectileSize = Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileSize"));
			Projectile towerProjectile = new ProjectileBuilder().construct(
					myDefaultName,  
					myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileImage"), 
					// TODO add projectile speed !!!!
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileDamage")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileUpgradeCost")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileUpgradeValue")),
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileSpeed")));
			Launcher towerLauncher = new LauncherBuilder().construct(
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherSpeed")),  
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherUpgradeCost")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherValue")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherRange")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherUpgradeCost")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherValue")), towerProjectile);  
			double towerSize = Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerSize"));
			Tower newTower = new TowerBuilder().construct(
					myDefaultName, 
					myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerImage"), 
					towerSize, 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealth")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealthUpgradeValue")), 
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealthUpgradeCost")), 
					towerLauncher,
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerValue")),
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerUpgradeCost")),
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerUpgradeValue"))); 
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
					myDefaultName, 
					myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH, "enemyImage"), 
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

	/**
	 * Adds a new level to the authored game
	 */
	public int addNewLevel() {
		int newLevelNumber = autogenerateLevel(); 
		//		int newLevelNumber = myLevels.size()+1; 
		//		myLevels.put(newLevelNumber, new Level(newLevelNumber));
		return newLevelNumber; 
	}

	/**
	 * Returns a list of level numbers as strings currently in the authored game
	 * 
	 * @return List<String>: A list of level numbers as strings
	 */
	public List<String> getLevels() {
		List<String> listToReturn = new ArrayList<String>(); 
		for (Integer level : myLevels.keySet()) {
			listToReturn.add(Integer.toString(level));
		}
		return listToReturn; 
	}

	/**
	 * Auto generates a new level for the authored game and puts it in the
	 * levels map. 
	 * 
	 * @return int: the number of the new, auto generated level
	 */
	public int autogenerateLevel() {
		int newLevelNumber = myLevels.size()+1;
		Level copiedLevel = myLevels.get(myLevels.size());
		myLevels.put(newLevelNumber, new Level(copiedLevel));
		return newLevelNumber; 
	}

	public void setGameName(String gameName) {
		myGameName = gameName; 
		mySettings.setGameName(myGameName);
	}

	public String getGameName() {
		return myGameName; 
	}

}

