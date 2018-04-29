/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/8/18
 * 
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller to determine what object to create/adjust
 */

package authoring;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import authoring.frontend.exceptions.DeleteDefaultException;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import data.GameData;
import engine.builders.PathBuilder;
import engine.Settings;
import engine.builders.SettingsBuilder;
import engine.level.Level;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import engine.sprites.towers.Tower;
import frontend.PropertiesReader;

public class AuthoringModel implements GameData {

	public static final String DEFAULT_SETTINGS_FILE = "default_objects/Settings.properties";
	public static final String DEFAULT_PROMPTS_FILE_KEY = "PromptsFile";
	public static final String DEFAULT_CONSTANTS_FILE_KEY = "ConstantFiles"; 
	public static final String DEFAULT_OBJECT_NAME_KEY = "DefaultObjectName"; 
	public static final String DEFAULT_GAME_NAME_KEY = "NewGame";
	public static final String DEFAULT_STARTINGHEALTH_KEY = "StartingHealth"; 
	public static final String DEFAULT_STARTINGMONEY_KEY = "StartingMoney";
	public static final String DEFAULT_STARTINGCSS_KEY = "StartingCSS";
	public static final String DEFAULT_STARTINGTHEME_KEY = "StartingTheme";
	public static final String DEFAULT_WAVENAME_SEPARATOR = " ";
	public static final int DEFAULT_FIRSTLEVEL_NUMBER = 1;

	private final GenericModel myGeneric = new GenericModel();
	private final AuthoredGame myGame;
	private String DEFAULT_CONSTANT_FILEPATH;
	private PropertiesReader myPropertiesReader;
	private String myDefaultName; 
	@XStreamOmitField
	private transient Tower myDefaultTower;
	@XStreamOmitField
	private transient Enemy myDefaultEnemy;
	private Path myDefaultPath;
	protected Map<String, List<Point>> myImageMap = new HashMap<String, List<Point>>();
	protected String myBackgroundImage = new String();
	protected List<Point> myPathCoordinates = new ArrayList<Point>();

	public AuthoringModel() throws MissingPropertiesException {
		this(new AuthoredGame());
		populateInstanceVariables();
	}

	public AuthoringModel(AuthoredGame game) throws MissingPropertiesException {
		myGame = game;
	}

	private void populateInstanceVariables() throws MissingPropertiesException {
		myPropertiesReader = new PropertiesReader();
		DEFAULT_CONSTANT_FILEPATH = myPropertiesReader.findVal(DEFAULT_SETTINGS_FILE, DEFAULT_CONSTANTS_FILE_KEY);
		myDefaultName = myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, DEFAULT_OBJECT_NAME_KEY);
		try {
			myDefaultTower = myGeneric.generateGenericTower();
			myDefaultEnemy = myGeneric.generateGenericEnemy();
			myDefaultPath = myGeneric.generateGenericPath();
			myGame.setSettings(myGeneric.generateGenericSettings());
			myGame.addLevel(DEFAULT_FIRSTLEVEL_NUMBER, myGeneric.generateGenericLevel(DEFAULT_FIRSTLEVEL_NUMBER, myDefaultTower, myDefaultEnemy, myDefaultPath));
		} catch (NumberFormatException | FileNotFoundException e) {
			throw new MissingPropertiesException(myDefaultName);
		}
	}

	/**
	 * Class to make a wave to be used in a specified level
	 * @throws ObjectNotFoundException 
	 */
	public void makeWave(int level, Wave wave) throws ObjectNotFoundException {
		Level currentLevel = myGame.levelCheck(level);
		currentLevel.addWave(wave);
	}

	public Level levelCheck(int level) throws ObjectNotFoundException {
		return myGame.levelCheck(level);
	}

	/**
	 * Method through which information can be sent to instantiate or edit a path object
	 * Wraps constructor in case of new object creation
	 * @throws ObjectNotFoundException 
	 */

	public void makePath(int level, List<List<Point>> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage, String pathImage, String startImage, String endImage, int pathSize, int col, int row) throws ObjectNotFoundException {
		myImageMap = imageCoordinates; //map (row/column), coordinates is absoluteCoordinates
		myBackgroundImage = backgroundImage;
		//				myPathCoordinates = coordinates;
		Level currentLevel = myGame.levelCheck(level);
		Path newPath = new PathBuilder().construct(coordinates, imageCoordinates, backgroundImage, pathImage, startImage, endImage, pathSize, col, row);
		currentLevel.addPath(newPath);
	}

	/**
	 * Method through which information can be sent to instantiate or edit a path object
	 * Wraps constructor in case of new object creation
	 */
	public void makeResources(String gameName, double startingHealth, double starting$, String css, String theme) {
		Settings newSettings = new SettingsBuilder().construct(gameName, startingHealth, starting$, css, theme);
		myGame.setSettings(newSettings);
	}

	public void makeTower(int level, String name) throws NoDuplicateNamesException, MissingPropertiesException, NumberFormatException, FileNotFoundException, ObjectNotFoundException {
		Level currentLevel = myGame.levelCheck(level);
		if (currentLevel.containsTower(name)) {
			throw new NoDuplicateNamesException(name);
		}
		Tower newTower = myGeneric.generateGenericTower(name);
		currentLevel.addTower(name, newTower);
	}

	public void makeEnemy(int level, String name) throws NoDuplicateNamesException, MissingPropertiesException, NumberFormatException, FileNotFoundException, ObjectNotFoundException {
		Level currentLevel = myGame.levelCheck(level);
		if (currentLevel.containsEnemy(name)) {
			throw new NoDuplicateNamesException(name);
		}
		Enemy newEnemy = myGeneric.generateGenericEnemy(name);
		currentLevel.addEnemy(name, newEnemy);
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
		Level currentLevel = myGame.levelCheck(level);
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
				listToReturn.add("Wave"+DEFAULT_WAVENAME_SEPARATOR+ k.toString());
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
			Level currentLevel = myGame.levelCheck(level);
			if (currentLevel.containsEnemy(name)) {
				Enemy enemy = currentLevel.getEnemy(name);
				attributeValue = attributeFinder.retrieveFieldValue(attribute, enemy);
			}
		}
		else if (objectType.equals("Tower")) {
			Level currentLevel = myGame.levelCheck(level);
			if (currentLevel.containsTower(name)) {
				Tower tower = currentLevel.getTower(name);
				attributeValue = attributeFinder.retrieveFieldValue(attribute, tower);
			}
		}
		else if (objectType.equals("Settings")) {
			attributeValue = attributeFinder.retrieveFieldValue(attribute, myGame.getSettings());
		}
		else if (objectType.equals("Path")) {
			Level currentLevel = myGame.levelCheck(level);
			Path path = currentLevel.getPath();
			attributeValue = attributeFinder.retrieveFieldValue(attribute, path);
		}

		else if(objectType.equals("Wave")) {
			Level currentLevel = myGame.levelCheck(level);
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

	public Integer getHighestWaveNumber(int level) throws ObjectNotFoundException {
		Level currentLevel = myGame.levelCheck(level);
		return currentLevel.getHighestWaveNumber();
	}

	/**
	 * Returns a Map of String image names to Lists of points where those images should
	 * be found in the path
	 * @return Map of image names to Point lists
	 */
	public Map<String, List<Point>> getImageMap() {
		return myImageMap;
	}

	/**
	 * 
	 * @return AuthoredGame: return the AuthoredGame object created with this model
	 */
	public AuthoredGame getGame() {
		return myGame;
	}

	/**
	 * 
	 * @return String: the name field in the AuthoredGame object
	 */
	public String getGameName() {
		return myGame.getGameName();
	}

	public void setGameName(String name) {
		myGame.setGameName(name);
	}

	/**
	 * Returns a Path object from the AuthoredGame object from the specified level with 
	 * the specified name.
	 * 
	 * @param name: path name
	 * @param levelNum: level number
	 * @return Path: the path with the specified name in the specified level
	 * @throws ObjectNotFoundException
	 */
	public Path getPathFromName(int name, int levelNum) throws ObjectNotFoundException {
		return myGame.levelCheck(levelNum).getPaths().get(name-1);
	}

	/**
	 * Returns a list of level numbers as strings currently stored in the 
	 * AuthoredGame object
	 * 
	 * Useful in displaying the levels available for edit by the user
	 * @return List<String>: A list of unmodifiable level numbers as strings
	 */
	public List<String> getLevels() {
		return myGame.unmodifiableLevelNums();
	}

	/**
	 * Returns Level object corresponding to an integer level number from the 
	 * AuthoredGame object
	 * 
	 * @param level is number of level desired
	 * @return Level object of that number
	 * @throws ObjectNotFoundException
	 */
	public Level getLevel(int level) throws ObjectNotFoundException {
		return myGame.levelCheck(level);
	}

	/**
	 * 
	 * @return List<Level>: an unmodifiable list of Level objects in the AuthoredGame object
	 */
	public List<Level> allLevels() {
		return myGame.unmodifiableLevels();
	}

	/**
	 * Adds a new level to the authored game, based on the previous level that the user has created
	 * (or the default level if the user has not customized any level) 
	 */
	public int addNewLevel() {
		int newLevelNumber = autogenerateLevel(); 
		return newLevelNumber; 
	}

	/**
	 * Autogenerates a new level based on the previous Level's settings (enemies, towers, etc.)
	 * @return int corresponding to level number of level generated
	 */
	public int autogenerateLevel() {
		List<Level> levels = myGame.unmodifiableLevels();
		int newLevelNumber = levels.size()+1;
		Level copiedLevel = levels.get(levels.size()-1);
		myGame.addLevel(newLevelNumber, new Level(copiedLevel));
		return newLevelNumber; 
	}

	public void deleteObject(int level, String objectType, String name) throws ObjectNotFoundException, DeleteDefaultException {
		Level currentLevel = myGame.levelCheck(level);
		if (objectType.equals("Tower")) {
			currentLevel.removeTower(name);
		}
		if (objectType.equals("Enemy")) {
			currentLevel.removeEnemy(name);
		}
		if (objectType.equals("Wave")) {
			currentLevel.removeWave(name.split(DEFAULT_WAVENAME_SEPARATOR)[1]);
		}
	}

	public void setObjectAttribute(int level, String objectType, String name, String attribute, Object attributeValue) throws ObjectNotFoundException, IllegalArgumentException, IllegalAccessException {
		AttributeFinder attributeFinder = new AttributeFinder();
		if (objectType.equals("Enemy")) {
			Level currentLevel = myGame.levelCheck(level);
			if (currentLevel.containsEnemy(name)) {
				Enemy enemy = currentLevel.getEnemy(name);
				attributeFinder.setFieldValue(attribute, enemy, attributeValue);
			}
		}
		else if (objectType.equals("Tower")) {
			Level currentLevel = myGame.levelCheck(level);
			if (currentLevel.containsTower(name)) {
				Tower tower = currentLevel.getTower(name);
				attributeFinder.setFieldValue(attribute, tower, attributeValue);
			}
		}
		else if (objectType.equals("Settings")) {
			attributeFinder.setFieldValue(attribute, myGame.getSettings(), attributeValue);
		}
	}
	public void updateAllProperties() throws ObjectNotFoundException {
		Level level;
		for (String levelNumber : getLevels()) {
			Integer numLevel = Integer.parseInt(levelNumber);
			level = getLevel(numLevel);
			level.updateAllProperties(); 
		}
	}

}
