/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/8/18
 * @author Katie Van Dyk 4/24/18
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

import authoring.factory.AttributeFactory;
import authoring.factory.PropertyFactory;
import authoring.factory.SpriteFactory;
import authoring.frontend.exceptions.DeleteDefaultException;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import engine.builders.PathBuilder;
import engine.Settings;
import engine.builders.SettingsBuilder;
import engine.level.Level;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import engine.sprites.towers.Tower;
import frontend.PropertiesReader;

public class AuthoringModel {

    private final GenericModel myGeneric = new GenericModel();
    private final String mySettingsFile = "default_objects/Settings.properties";
    private final AuthoredGame myGame;
    private String DEFAULT_PROMPTS;
    private String DEFAULT_CONSTANT_FILEPATH;
    private PropertiesReader myPropertiesReader;
    private String myDefaultName; 
    private Tower myDefaultTower;
    private Enemy myDefaultEnemy;
    private Path myDefaultPath;
    private SpriteFactory spriteFactory;
    protected Map<String, List<Point>> myImageMap = new HashMap<String, List<Point>>();
    protected String myBackgroundImage = new String();
    protected List<Point> myPathCoordinates = new ArrayList<Point>();
    private PropertyFactory propertyFactory = new PropertyFactory();
    private AttributeFactory attributeFactory = new AttributeFactory();

    public AuthoringModel() throws MissingPropertiesException {
	this(new AuthoredGame());
	populateInstanceVariables();
	setupDefaultSettings(); 
	setupDefaultLevel();
    }

    public AuthoringModel(AuthoredGame game) throws MissingPropertiesException {
	myGame = game;
	spriteFactory = new SpriteFactory(myGeneric);
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
	listToReturn.remove(myDefaultName);
	return listToReturn; 
    }

    public List<Object> getObjectProperty(int level, String objectType, String name, String attribute){
	return propertyFactory.retrieveProperty(name, attribute);
    }

    public void setObjectProperty(int level, String objectType, String objectName, String propertyName, List<Object> attributes) throws ObjectNotFoundException{
	Level currentLevel = myGame.levelCheck(level);
	propertyFactory.setProperty(currentLevel, objectType, objectName, propertyName, attributes);
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
    
    public void makeSprite(String objectType, int level, String name) throws ObjectNotFoundException, NumberFormatException, FileNotFoundException, NoDuplicateNamesException, MissingPropertiesException {
	Level currentLevel = myGame.levelCheck(level);
	spriteFactory.makeSprite(objectType, currentLevel, name);
    }

    public void deleteObject(int level, String objectType, String name) throws ObjectNotFoundException, DeleteDefaultException {
	Level currentLevel = myGame.levelCheck(level);
	spriteFactory.deleteSprite(objectType, currentLevel, name);
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
    public Object getObjectAttribute(int level, String objectType, String name, String attribute) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
	return attributeFactory.getObjectAttribute(level, objectType, name, attribute, myGame);
    }
    
    public void setObjectAttribute(int level, String objectType, String name, String attribute, Object attributeValue) throws ObjectNotFoundException, IllegalArgumentException, IllegalAccessException {
	attributeFactory.setObjectAttribute(level, objectType, name, attribute, attributeValue, myGame);
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
