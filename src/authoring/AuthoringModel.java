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
	
    private final GenericModel myGeneric = new GenericModel();
    private final String mySettingsFile = "default_objects/Settings.properties";
    private final AuthoredGame myGame;
    private String DEFAULT_PROMPTS;
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
	setupDefaultSettings(); 
	setupDefaultLevel();
    }
    
    public AuthoringModel(AuthoredGame game) throws MissingPropertiesException {
	myGame = game;
    }

    private void populateInstanceVariables() throws MissingPropertiesException {
	myPropertiesReader = new PropertiesReader();
	DEFAULT_PROMPTS = myPropertiesReader.findVal(mySettingsFile, "PromptsFile");
	DEFAULT_CONSTANT_FILEPATH = myPropertiesReader.findVal(mySettingsFile, "ConstantFiles");
	DEFAULT_PROMPTS = myPropertiesReader.findVal(mySettingsFile, "PromptsFile");
	DEFAULT_CONSTANT_FILEPATH = myPropertiesReader.findVal(mySettingsFile, "ConstantFiles");
	myDefaultName = myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "DefaultObjectName");
	try {
	    myDefaultTower = myGeneric.generateGenericTower();
	    myDefaultEnemy = myGeneric.generateGenericEnemy();
	    myDefaultPath = myGeneric.generateGenericPath();
	} catch (NumberFormatException | FileNotFoundException e) {
	    throw new MissingPropertiesException(myDefaultName);
	}
    }

    private void setupDefaultSettings() throws MissingPropertiesException {
	String defaultGameName = myPropertiesReader.findVal(DEFAULT_PROMPTS, "NewGame");
	int startingHealth = Integer.parseInt(myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "StartingHealth"));
	int startingMoney = Integer.parseInt(myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "StartingMoney"));
	String startingCSS = myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "StartingCSS");
	String startingTheme = myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "StartingTheme");
	Settings newSettings = new SettingsBuilder().construct(defaultGameName, 
		startingHealth, startingMoney, startingCSS, startingTheme);
	myGame.setSettings(newSettings);
    } 

    private void setupDefaultLevel() {
	Level firstLevel = new Level(1);
	firstLevel.addTower(myDefaultName, new Tower(myDefaultTower));
	Enemy testEnemy = new Enemy(myDefaultEnemy);
	firstLevel.addEnemy(myDefaultName, testEnemy);
	firstLevel.addPath(myDefaultPath);
	myGame.addLevel(1, firstLevel);
    }

    /**
     * Class to make a wave to be used in a specified level
     * @throws ObjectNotFoundException 
     */
    public void makeWave(int level, Wave wave) throws ObjectNotFoundException {
	Level currentLevel = myGame.levelCheck(level);
	currentLevel.addWave(wave);
    }

    /**
     * Method through which information can be sent to instantiate or edit a path object
     * Wraps constructor in case of new object creation
     * @throws ObjectNotFoundException 
     */

    public void makePath(int level, List<Point> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage, int pathSize) throws ObjectNotFoundException {
	myImageMap = imageCoordinates;
	myBackgroundImage = backgroundImage;
	myPathCoordinates = coordinates;
	Level currentLevel = myGame.levelCheck(level);
	Path newPath = new PathBuilder().construct(coordinates, imageCoordinates, backgroundImage, pathSize);
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
	System.out.println(level+" "+name);
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
		System.out.println("attribute val: " + attributeValue);
		//System.out.println("GETTING ENEMY INFO AFTER SAVE?");
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
	    //			if (currentLevel.containsTower(name)) {
	    Path path = currentLevel.getPath();
	    attributeValue = attributeFinder.retrieveFieldValue(attribute, path);
	    System.out.println("PATH INFO: " +attributeValue);
	    //			}
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
	System.out.println("IS MAP NULL: " +myImageMap);
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
	//		int newLevelNumber = myLevels.size()+1; 
	//		myLevels.put(newLevelNumber, new Level(newLevelNumber));
	return newLevelNumber; 
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
	List<Level> levels = myGame.unmodifiableLevels();
	int newLevelNumber = levels.size()+1;
	Level copiedLevel = levels.get(levels.size()-1);
	myGame.addLevel(newLevelNumber, new Level(copiedLevel));
	return newLevelNumber; 
    }

    public void deleteObject(int level, String objectType, String name) throws ObjectNotFoundException {
	Level currentLevel = myGame.levelCheck(level);
	if (objectType.equals("Tower")) {
	    currentLevel.removeTower(name);
	}
	if (objectType.equals("Enemy")) {
	    currentLevel.removeEnemy(name);
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
