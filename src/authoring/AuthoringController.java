/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/9/18
 *
 * Class that handles mediating creation of authoring environment objects (towers, enemies, path). 
 * Represents Controller in MVC of the authoring environment. 
 * 
 */

package authoring;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.frontend.AuthoringView;
import authoring.frontend.exceptions.DeleteDefaultException;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import controller.MVController;
import controller.PlayController;
import engine.level.Level;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import frontend.StageManager;
import javafx.scene.layout.GridPane;
import xml.AuthoringModelReader;
import xml.AuthoringModelWriter;


public class AuthoringController implements MVController{

	public static final String NO_DEFAULT_OBJ_ALERT_KEY = "NoDefaultObject"; 
	public static final String DEFAULT_EDIT_BUTTON_CTRLFLOW = "Edit";
	public static final String DEFAULT_OBJNOTFOUNDEXCEPTION_ALERT = "NoObject";
	public static final String DEFAULT_NODEFAULTOBJECT_ALERT = "NoDeleteDefault"; 
	
    private AuthoringView myView; 
    private Map<String, List<Point>> myImageMap;
    private AuthoringModel myModel; 

    /**
     * Creates a new AuthoringController
     * @param stageManager contains Stage game is being authored on
     * @param languageIn is chosen language for prompts
     */
    public AuthoringController(StageManager stageManager, String languageIn) {
	myView = new AuthoringView(stageManager, languageIn, this);
	try {
	    myModel = new AuthoringModel();
	} catch (MissingPropertiesException e) {
	    myView.loadErrorScreen(NO_DEFAULT_OBJ_ALERT_KEY);
	}
	myView.setModel(myModel);
	myView.loadInitialScreen();
    }

    /**
     * Method through which information about object fields can be requested
     * Invoked when populating authoring frontend screens used to edit existing objects
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws IllegalAccessException 
     * @throws NoSuchFieldException 
     * @throws ObjectNotFoundException 
     */
    public Object getObjectAttribute(int level, String objectType, String name, String attribute) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
	return myModel.getObjectAttribute(level, objectType, name, attribute);
    }

    /**
     * Method through which information can be sent to instantiate or edit the Resources object in Authoring Model;
     */
    public void makeResources(String gameName, double startingHealth, double starting$, String css, String theme) {
	myModel.makeResources(gameName, startingHealth, starting$, css, theme);
    }

    // TODO
    /**
     * Method through which information can be sent to instantiate or edit a Path in Authoring Model
     * @throws ObjectNotFoundException 
     */
    public void makePath(int level, GridPane grid, List<List<Point>> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage, String pathImage, String startImage, String endImage, int pathSize, int width, int height) throws ObjectNotFoundException { 
	myModel.makePath(level, coordinates, imageCoordinates, backgroundImage, pathImage, startImage, endImage, pathSize, width, height); 
	myImageMap = imageCoordinates;
    }

    /**
     * Method that wraps Model method to return a Path object given its level and 
     * int identifier
     * @param name is int identifier of this path
     * @param level is current level
     * @return Path object corresponding to this level/id
     * @throws ObjectNotFoundException
     */
    public Path getPathFromName(int name, int level) throws ObjectNotFoundException {
	return myModel.getPathFromName(name, level);
    }

    /**
     * Method through which information can be retrieved from AuthoringMOdel re: the current objects of a given type are available for editing
     * @throws ObjectNotFoundException 
     */
    public List<String> getCurrentObjectOptions(int level, String objectType) throws ObjectNotFoundException {
	return myModel.getCurrentObjectOptions(level, objectType);
    }

    /**
     * Invokes a Model method that adds a new level to the authored game, 
     * based on the previous level that the user has created
     * (or the default level if the user has not customized any level) 
     * @return integer of new level created
     */
    public int addNewLevel() throws ObjectNotFoundException{
	return myModel.addNewLevel(); 
    }

    /**
     * Invokes a Model method that returns a list of level numbers 
     * as strings currently in the authored game
     * Useful in displaying the levels available for edit by the user
     * @return List<String>: A list of level numbers as strings
     */
    public List<String> getLevels() {
	return myModel.getLevels(); 
    }

    /**
     * Wraps Model method to autogenerate a level
     * @return int level number of new level
     */
    public int autogenerateLevel() {
	return myModel.autogenerateLevel(); 
    }

    /**
     * Edits/adds an enemy composition in a specified wave
     * 
     * @param level: the level the wave pertains to
     * @param path: the path that specifies the wave
     * @param waveNumber: the wave number
     * @param enemyKey: the unique String name that identifies the enemy
     * @param newAmount: the new amount of the specified enemy to put in the wave
     * @throws ObjectNotFoundException: thrown if the level isn't found
     */
    public void addWaveEnemy(int level, Path path, int waveNumber, String enemyKey, int newAmount) throws ObjectNotFoundException {
	Level thisLevel = myModel.getLevel(level);
	Enemy thisEnemy = thisLevel.getEnemy(enemyKey);
	Wave thisWave;
	if(!thisLevel.containsWaveNumber(waveNumber)) {
	    thisWave = new Wave();
	    thisLevel.addWave(thisWave);
	}
	else{
	    thisWave = thisLevel.getWave(waveNumber);

	}
	thisWave.addEnemy(thisEnemy, path, newAmount);
    }

    /**
     * Returns the number of waves in a specified level that belong to a specified
     * path object.
     * 
     * @param level: the current level
     * @param path: the path that the waves belong to
     * @return int: the number of waves that belong to the path in the level
     * @throws ObjectNotFoundException: thrown if the level isn't found
     */
    public int wavesNumber(int level, Path path) throws ObjectNotFoundException {
	Level thisLevel = myModel.getLevel(level);
	List<Wave> levelWaves = thisLevel.getWaves();
	return levelWaves.size();
    }

    /**
     * Gets a Map of the current Enemy names to the number of that enemy for a given wave/path/
     * level combination
     * @param level of wave desired
     * @param path of wave desired
     * @param waveNumber of wave desired
     * @return Map of enemy names to the number of those enemies going down this path in this wave
     * @throws ObjectNotFoundException
     */
    public Map<String, Integer> getEnemyNameToNumberMap(int level, Path path, int waveNumber) throws ObjectNotFoundException {
	Level currentLevel = myModel.getLevel(level);
	//TODO: issue here - if there is no wave yet then need to make it first!
	if(!currentLevel.containsWaveNumber(waveNumber) || currentLevel.getWaves().get(waveNumber).getUnmodifiableEnemies(path)==null) {
	    return new HashMap<String, Integer>();
	}
	Map<Enemy, Integer> enemyMap = currentLevel.getWaves().get(waveNumber).getUnmodifiableEnemies(path);
	Map<String,Integer> enemyNameMap = new HashMap<>();
	for(Enemy enemy : enemyMap.keySet()) {
	    enemyNameMap.put(enemy.getName(), enemyMap.get(enemy));
	}
	return enemyNameMap;   
    }

    /**
     * Returns a List of the enemies contained in the level 
     * 
     * @param level: the current level
     * @return List<String>: a list of the unique String names for each enemy in the
     * level.
     * @throws ObjectNotFoundException: thrown if the level isn't found
     */
    public List<String> levelEnemies(int level) throws ObjectNotFoundException {
	Level thisLevel = myModel.getLevel(level);
	return thisLevel.getAllEnemies();
    }

    /**
     * Sets name of the game per user entry
     * @param gameName  is new game name
     */
    public void setGameName(String gameName) {
	myModel.setGameName(gameName);
    }	

    /**
     * Gets current name of the game
     * @return current name of the game
     */
    public String getGameName() {
	return myModel.getGameName(); 
    }
    /**
     * Sets the current AuthoringModel being used based on an XML file
     * @param gameName is name of game/XML file being loaded in
     * @throws MissingPropertiesException 
     */
    public void setModel(String gameName) throws MissingPropertiesException {
	myView.setGameName(gameName);
	AuthoringModelReader reader = new AuthoringModelReader();
	myModel = new AuthoringModel(reader.createModel(gameName));
	myView.setModel(myModel);
	myView.goForwardFrom(this.getClass().getSimpleName()+DEFAULT_EDIT_BUTTON_CTRLFLOW, getGameName());
    }

    public void setModel(AuthoringModel model) {
	myModel = model;
    }

    /**
     * Returns a map of String image names to a list of Point coordinates where those 
     * images should be found on the path
     * @return image name to coordinate map
     */
    public Map<String, List<Point>> getGrid() {
	return myImageMap;
    }
    public Path getPathWithStartingPoint(int level, Point point) throws ObjectNotFoundException {
	return myModel.getPathWithStartingPoint(level, point);
    }
    /**
     * Method to retrieve the highest wave number found in a level (including all paths)
     * @param level is level desired
     * @return highest wave number found in that level
     * @throws ObjectNotFoundException
     */
    public Integer getHighestWaveNumber(int level) throws ObjectNotFoundException{
	return myModel.getHighestWaveNumber(level);
    }

    public void makeSprite(String objectType, int level, String name) throws NoDuplicateNamesException, MissingPropertiesException, NumberFormatException, FileNotFoundException, ObjectNotFoundException {
	myModel.makeSprite(objectType, level, name);
    }

    public void setObjectAttribute(int level, String objectType, String name, String attribute, Object attributeValue) throws ObjectNotFoundException, IllegalArgumentException, IllegalAccessException {
	myModel.setObjectAttribute(level, objectType, name, attribute, attributeValue);
    }

    public void createProperty(int level, String objectType, String name, String propertyName, List<Double> attributes) throws ObjectNotFoundException, IllegalArgumentException, IllegalAccessException, MissingPropertiesException {
	myModel.createProperty(level, objectType, name, propertyName, attributes);
    }

    public void setWaveTime(int level, int waveNumber, int time) throws ObjectNotFoundException{
	Level currentLevel = myModel.getLevel(level);
	if(!currentLevel.containsWaveNumber(waveNumber)) {
	    currentLevel.addWave(waveNumber);
	}
	Wave desiredWave = currentLevel.getWaves().get(waveNumber);
	desiredWave.setWaveTime(time);
    }

    public void writeToFile() throws ObjectNotFoundException {
	AuthoringModelWriter writer = new AuthoringModelWriter();
	writer.write(myModel.getGame(), myModel.getGameName());
    }
    
    @Override
    public void playControllerDemo(StageManager manager, String language) {
	new PlayController(manager, language,
		myModel).demoPlay(myModel.getGame());
    }
    
    public void deleteObject(int level, String objectType, String objectName) {
	try {
	    myModel.deleteObject(level, objectType, objectName);
	}
	catch(ObjectNotFoundException e) {
	    myView.loadErrorScreen("NoObject");
	}
	catch(DeleteDefaultException e2) {
	    myView.loadErrorAlert("NoDeleteDefault");
	}
    }
}