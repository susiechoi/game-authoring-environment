/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/8/18
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller to determine what object to create/adjust
 * 
 */

package authoring;

import java.awt.Point;
import java.lang.Double; 
import java.io.FileNotFoundException;
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
import engine.sprites.enemies.wave.Wave;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import frontend.PropertiesReader;

public class AuthoringModel implements GameData {
	public static final String DEFAULT_ENEMY_IMAGES = "images/EnemyImageNames.properties";
	public static final String DEFAULT_ENEMY_IMAGE = "Zombie";
	public static final String DEFAULT_TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final String DEFAULT_IMAGES_SUFFIX = "ImageNames.properties";
	public static final String DEFAULT_PROJECTILE_IMAGES = "images/ProjectileImageNames.properties";
	public static final String DEFAULT_TOWER_IMAGE = "Pisa";
	public static final String DEFAULT_PROJECTILE_IMAGE = "Bullet";
	public static final String DEFAULT_TOWER_FILEPATH = "default_objects/GenericTower.properties";
	public static final String DEFAULT_ENEMY_FILEPATH = "default_objects/GenericEnemy.properties";
	public static final String DEFAULT_PROMPTS = "languages/English/Prompts.properties";
	public static final String DEFAULT_CONSTANT_FILEPATH = "src/frontend/Constants.properties";
	public static final String DEFAULT_PATH_START = "images/brick.png";
	public static final String DEFAULT_PATH_MIDDLE = "images/cobblestone.png";
	public static final String DEFAULT_PATH_END = "images/darkstone.png";
	public static final String DEFAULT_BACKGROUND_IMAGE = "images/generalbackground.jpg";
	private final String myDefaultName; 

	private String myGameName; 
	private final PropertiesReader myPropertiesReader;
	private Settings mySettings; 
	private Map<Integer, Level> myLevels;
	private Tower myDefaultTower;
	private Enemy myDefaultEnemy;
	private Path myDefaultPath;
	//	private Path myPath;
	protected Map<String, List<Point>> myImageMap = new HashMap<String, List<Point>>();
	protected String myBackgroundImage = new String();
	protected List<Point> myPathCoordinates = new ArrayList<Point>();


	public AuthoringModel() throws MissingPropertiesException {
		myLevels = new HashMap<Integer, Level>();
		myPropertiesReader = new PropertiesReader();
		myDefaultName = myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "DefaultObjectName");
		setupDefaultSettings(); 
		try {
			myDefaultTower = generateGenericTower();
			myDefaultEnemy = generateGenericEnemy();
			myDefaultPath = generateGenericPath();
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
		Enemy testEnemy = new Enemy(myDefaultEnemy);
		firstLevel.addEnemy(myDefaultName, testEnemy);
		firstLevel.addPath(myDefaultPath);
		myLevels.put(1, firstLevel);
	}

	/**
	 * Class to make a wave to be used in a specified level
	 * @throws ObjectNotFoundException 
	 */
	public void makeWave(int level, Wave wave) throws ObjectNotFoundException {
		Level currentLevel = levelCheck(level);
		currentLevel.addWave(wave);
	}

	// TODO 
	/**
	 * Method through which information can be sent to instantiate or edit a path object
	 * Wraps constructor in case of new object creation
	 * @throws ObjectNotFoundException 
	 */

	public void makePath(int level, List<Point> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage, int pathSize) throws ObjectNotFoundException {
		myImageMap = imageCoordinates;
		myBackgroundImage = backgroundImage;
		myPathCoordinates = coordinates;
		Level currentLevel = levelCheck(level);
		Path newPath = new PathBuilder().construct(coordinates, imageCoordinates, backgroundImage, pathSize);
		currentLevel.addPath(newPath);
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
					Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileSize")),
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

	public Path getPathFromName(int name, int levelNum) throws ObjectNotFoundException {
		return levelCheck(levelNum).getPaths().get(name-1);
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
		if(objectType.equals("Wave")) {
			int size = currentLevel.getHighestWaveNumber();
			for(Integer k = 1; k<=(size+1); k+=1) {
				listToReturn.add("Wave " + k.toString());
			}
		}
		//		if(objectType.equals("Path")) {
		//			listToReturn.add(currentLevel.getPath());
		//			if (listToReturn.size() == 0) {
		//				listToReturn.add(myDefaultPath.getName());
		//			}
		//		}
		listToReturn.remove(myDefaultName);
		return listToReturn; 
	}

	public Integer getHighestWaveNumber(int level) throws ObjectNotFoundException {
		Level currentLevel = levelCheck(level);
		return currentLevel.getHighestWaveNumber();
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
	public Object getObjectAttribute(int level, String objectType, String name, String attribute) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
		Object attributeValue = null;

		AttributeFinder attributeFinder = new AttributeFinder(); 
		if (objectType.equals("Enemy")) {
			Level currentLevel = levelCheck(level);
			if (currentLevel.containsEnemy(name)) {
				Enemy enemy = currentLevel.getEnemy(name);
				attributeValue = attributeFinder.retrieveFieldValue(attribute, enemy);
				System.out.println("GETTING ENEMY INFO AFTER SAVE?");
			}
		}
		else if (objectType.equals("Tower")) {
			Level currentLevel = levelCheck(level);
			if (currentLevel.containsTower(name)) {
				Tower tower = currentLevel.getTower(name);
				attributeValue = attributeFinder.retrieveFieldValue(attribute, tower);
			}
		}
		else if (objectType.equals("Settings")) {
			attributeValue = attributeFinder.retrieveFieldValue(attribute, mySettings);
		}
		else if (objectType.equals("Path")) {
			Level currentLevel = levelCheck(level);
			//			if (currentLevel.containsTower(name)) {
			Path path = currentLevel.getPath();
			attributeValue = attributeFinder.retrieveFieldValue(attribute, path);
			System.out.println("PATH INFO: " +attributeValue);
			//			}
		}

		else if(objectType.equals("Wave")) {
			Level currentLevel = levelCheck(level);
			if (currentLevel.containsWaveNumber(Integer.parseInt(name))) {
				Wave wave = currentLevel.getWaves().get(Integer.parseInt(name));
				attributeValue = attributeFinder.retrieveFieldValue(attribute, wave);
			}
		}
		if (attributeValue == null) {
			throw new ObjectNotFoundException(name);
		}
		if (attributeValue.getClass() == Double.class) {
			return Double.toString((double) attributeValue); 
		} 
		else return attributeValue; 
	}

	/**
	 * Returns Level object corresponding to an integer level number
	 * @param level is number of level desired
	 * @return Level object of that number
	 * @throws ObjectNotFoundException
	 */
	public Level levelCheck(int level) throws ObjectNotFoundException {
		Level currentLevel = myLevels.get(level);
		if (currentLevel == null) {
			throw new ObjectNotFoundException("Level "+level);
		}
		return currentLevel;
	}

	/**
	 * Returns a list of Level objects corresponding to all Levels that have been previously made
	 * @return a list of all Levels currently made in this game
	 */
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
		myGameName = mySettings.getGameName();
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

	private Path generateGenericPath() throws NumberFormatException, FileNotFoundException {
		List<Point> dummyPathPoints = new ArrayList<>();
		dummyPathPoints.add(new Point(2, 2));
		dummyPathPoints.add(new Point(2, 3));
		HashMap<String, List<Point>> pathImages = new HashMap<>();
		List<Point> dummyPathStartPoints = new ArrayList<>();
		dummyPathStartPoints.add(new Point(2, 2));
		List<Point> dummyPathEndPoints = new ArrayList<>();
		dummyPathEndPoints.add(new Point(2, 3));
		pathImages.put(DEFAULT_PATH_START, dummyPathStartPoints);
		pathImages.put(DEFAULT_PATH_END, dummyPathEndPoints);
		Path newPath = new PathBuilder().construct(dummyPathPoints, pathImages, DEFAULT_BACKGROUND_IMAGE, 60);
		return newPath;
	}

	/**
	 * Adds a new level to the authored game, based on the previous level that the user has created
	 * (or the default level if the user has not customized any level) 
	 */
	public int addNewLevel() {
		int newLevelNumber = autogenerateLevel(); 
		//		int newLevelNumber = myLevels.size()+1; 
		//		myLevels.put(newLevelNumber, new Level(newLevelNumber));
		return newLevelNumber; 
	}

	/**
	 * Returns a list of level numbers as strings currently in the authored game
	 * Useful in displaying the levels available for edit by the user
	 * @return List<String>: A list of level numbers as strings
	 */
	public List<String> getLevels() {
		List<String> listToReturn = new ArrayList<String>(); 
		for (Integer level : myLevels.keySet()) {
			listToReturn.add(Integer.toString(level));
		}
		return listToReturn; 
	}

	//	/**
	//	 * Auto generates a new level for the authored game and puts it in the
	//	 * levels map. 
	//	 * 
	//	 * @return int: the number of the new, auto generated level
	//	 */
	//	public int autogenerateLevel() {
	////		System.out.println(newLevelNumber+" IS OUR NEW LEVEL NUMBER");
	//		int newLevelNumber = myLevels.size();
	//		Level copiedLevel = myLevels.get(myLevels.size()-1);
	//		myLevels.put(newLevelNumber, new Level(copiedLevel));
	//		//	return newLevelNumber; 
	//		return newLevelNumber;
	//	}

	/**
	 * Autogenerates a new level based on the previous Level's settings (enemies, towers, etc.)
	 * @return int corresponding to level number of level generated
	 */
	public int autogenerateLevel() {
		int newLevelNumber = myLevels.size()+1;
		Level copiedLevel = myLevels.get(myLevels.size());
		myLevels.put(newLevelNumber, new Level(copiedLevel));
		return newLevelNumber; 
	}

	/**
	 * Sets the game name of this game so it can be saved/loaded correctly
	 * @param gameName is new name of this game
	 */
	public void setGameName(String gameName) {
		myGameName = gameName; 
		mySettings.setGameName(myGameName);
	}

	/**
	 * @return String name of this game
	 */
	public String getGameName() {
		return myGameName; 
	}

	/**
	 * Returns a Map of String image names to Lists of points where those images should
	 * be found in the path
	 * @return Map of image names to Point lists
	 */
	public Map<String, List<Point>> getImageMap() {
		System.out.println("IS MAP NULL: " +myImageMap);
		return myImageMap;
	}

	public void deleteObject(int level, String objectType, String name) throws ObjectNotFoundException {
		Level currentLevel = levelCheck(level);
		if (objectType.equals("Tower")) {
			currentLevel.removeTower(name);
		}
		if (objectType.equals("Enemy")) {
			currentLevel.removeEnemy(name);
		}
		if (objectType.equals("Wave")) {
		    	currentLevel.removeWave(name.split(" ")[1]);
		}
	}

	public void makeTower(int level, String name) throws NoDuplicateNamesException, MissingPropertiesException {
		Level currentLevel = myLevels.get(level);
		if (currentLevel.containsTower(name)) {
			throw new NoDuplicateNamesException(name);
		}
		Projectile towerProjectile = new ProjectileBuilder().construct(name, 
				myPropertiesReader.findVal(DEFAULT_PROJECTILE_IMAGES, DEFAULT_PROJECTILE_IMAGE), 0, 0, 
				0, 0, 0);
		Launcher towerLauncher = new LauncherBuilder().construct(0, 0, 0, 0, 0, 0, towerProjectile); 
		Tower newTower = new TowerBuilder().construct(name, myPropertiesReader.findVal(DEFAULT_TOWER_IMAGES, DEFAULT_TOWER_IMAGE), 50, 0, 
				0, 0, towerLauncher, 0, 0, 0);
		currentLevel.addTower(name, newTower);
	}
	
	public void makeEnemy(int level, String name) throws NoDuplicateNamesException, MissingPropertiesException {
		Level currentLevel = myLevels.get(level);
		if (currentLevel.containsEnemy(name)) {
			throw new NoDuplicateNamesException(name);
		}
		Enemy newEnemy = new EnemyBuilder().construct(name, myPropertiesReader.findVal(DEFAULT_ENEMY_IMAGES, DEFAULT_ENEMY_IMAGE), 0, 0, 0, 0, 0, 0);
		currentLevel.addEnemy(name, newEnemy);
		System.out.println(level+" "+name);
	}
	
	public void setObjectAttribute(int level, String objectType, String name, String attribute, Object attributeValue) throws ObjectNotFoundException, IllegalArgumentException, IllegalAccessException {
		AttributeFinder attributeFinder = new AttributeFinder();
		if (objectType.equals("Enemy")) {
			Level currentLevel = levelCheck(level);
			if (currentLevel.containsEnemy(name)) {
				Enemy enemy = currentLevel.getEnemy(name);
				attributeFinder.setFieldValue(attribute, enemy, attributeValue);
			}
		}
		else if (objectType.equals("Tower")) {
			Level currentLevel = levelCheck(level);
			if (currentLevel.containsTower(name)) {
				Tower tower = currentLevel.getTower(name);
				attributeFinder.setFieldValue(attribute, tower, attributeValue);
			}
		}
		else if (objectType.equals("Settings")) {
			attributeFinder.setFieldValue(attribute, mySettings, attributeValue);
		}
	}
	
}

