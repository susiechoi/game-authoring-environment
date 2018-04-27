/**
 /**
 * @author Sarah Bland
 * @author susiechoi
 * 
 * Represents View of authoring environment's MVC. 
 * Allows for screen transitions and the communication of object altering/creation to Controller. 
 */

package authoring.frontend;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.AuthoringController;
import authoring.AuthoringModel;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import engine.path.Path;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import frontend.View;
import gameplayer.ScreenManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class AuthoringView extends View {

	public static final String DEFAULT_SCREENFLOW_FILEPATH = "src/frontend/ScreenFlow.properties";
	public static final String DEFAULT_ERROR_FILEPATH_BEGINNING = "languages/";
	public static final String DEFAULT_ERROR_FILEPATH_END = "/Errors.properties";
	public static final String DEFAULT_AUTHORING_CSS = "styling/GameAuthoringStartScreen.css";
	public static final String DEFAULT_LANGUAGE = "English";
	
	private StageManager myStageManager; 
	private PropertiesReader myPropertiesReader;
	private AuthoringController myController; 
	private String myCurrentCSS;
	private int myLevel; 
	private GridPane myGrid = new GridPane();
	private AuthoringModel myModel;
	private BooleanProperty myCSSChanged;
	private String myTheme; 

	public AuthoringView(StageManager stageManager, String languageIn, AuthoringController controller) {
		super(stageManager, languageIn);

		myPropertiesReader = new PropertiesReader();
		myStageManager = stageManager; 
		myController = controller; 
		myCurrentCSS = DEFAULT_AUTHORING_CSS;
		myCSSChanged = new SimpleBooleanProperty(false);
		
	}


	/**
	 * Sets the AuthoringModel used to retrieve backend information.
	 * @param model is new Model used
	 */
	public void setModel(AuthoringModel model) {
		myModel = model;
	}
	
	/**
	 * Returns the AuthoringModel object the user uses to author a game. 
	 * Should never return null because the model and view are both created
	 * in the AuthoringController class and the view's method setModel is called.
	 * 
	 * @return AuthoringModel: the model authored by the user
	 */
	public AuthoringModel getModel() {
	    return myModel;
	}

	/**
	 * Loads the first authoring screen shown to user (currently StartScreen) from which ScreenFlow
	 *  can direct further screens.
	 */
	public void loadInitialScreen() {
		myStageManager.switchScreen((new StartScreen(this)).getScreen());
	}

	protected void loadScreen(Screen screen) {
		myStageManager.switchScreen(screen.getScreen());
	}
	/**
	 * @return current CSS filepath
	 */
	protected String getCurrentCSS() {
		return myCurrentCSS;
	}
	
	protected void setCurrentCSS(String css) {
		myCurrentCSS = css; 
		myCSSChanged.set(!myCSSChanged.get());
	}
	protected void setWaveTime(int waveNumber, int time) {
	    try {
	    	myController.setWaveTime(getLevel(), waveNumber, time);
	    }
	    catch(ObjectNotFoundException e) {
		loadErrorScreen("NoObject");
	    }
	}
	protected void addWaveEnemy(int level, String pathName, int waveNumber, String enemyKey, int amount) {
		try {
		    myController.addWaveEnemy(level, pathName, waveNumber, enemyKey, amount);
		}
		catch(ObjectNotFoundException e) {
		    e.printStackTrace();
		    loadErrorScreen("NoObject");
		}
	}

	protected void goBackFrom(String id) {
		goForwardFrom(id+"Back");
	}
	

	protected void goForwardFrom(String id) {
		goForwardFrom(id, "");
	}
	
	public void goForwardFrom(String id, List<String> name) {
	    try {
		String nextScreenClass = myPropertiesReader.findVal(DEFAULT_SCREENFLOW_FILEPATH, id);
		Class<?> clazz = Class.forName(nextScreenClass);
		Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
		if(constructor.getParameterTypes().length == 2) {
			if(constructor.getParameterTypes()[1].equals(AuthoringModel.class)) {
				AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this, myModel);
				myStageManager.switchScreen(nextScreen.getScreen());
			}
			else if(constructor.getParameterTypes()[1].equals(ArrayList.class)) {
				AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this, name);
				myStageManager.switchScreen(nextScreen.getScreen());
			}
			else if(constructor.getParameterTypes()[1].equals(String.class)) {
			    	AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this, name.get(0));
				myStageManager.switchScreen(nextScreen.getScreen());
			}
			else if(constructor.getParameterTypes()[0].equals(StageManager.class)){
				Screen nextScreen = (Screen) constructor.newInstance(myStageManager, this);
				myStageManager.switchScreen(nextScreen.getScreen());
			}
		}
		else if(constructor.getParameterTypes()[0].equals(AuthoringView.class)) {
			AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this);
			myStageManager.switchScreen(nextScreen.getScreen());
		}
		else if(constructor.getParameterTypes()[0].equals(ScreenManager.class)) {
			Screen nextScreen = (Screen) constructor.newInstance(new ScreenManager(myStageManager, DEFAULT_LANGUAGE));
			myStageManager.switchScreen(nextScreen.getScreen());
		} 
		else {
			throw new MissingPropertiesException("");
		}

	}
	catch(MissingPropertiesException | ClassNotFoundException | InvocationTargetException
			| IllegalAccessException | InstantiationException e) {
		e.printStackTrace();
		loadErrorScreen("NoScreenFlow");
	}
	}
	
	public void goForwardFrom(String id, String name) {
	    	ArrayList<String> parameterList= new ArrayList<>();
	    	parameterList.add(name);
		goForwardFrom(id,  parameterList);
	}

	public void makePath(GridPane grid, List<Point> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage, int pathSize) throws ObjectNotFoundException {
		myGrid = grid;
		myController.makePath(myLevel, grid, coordinates, imageCoordinates, backgroundImage, pathSize);
	}

	/**
	 * Method through which information can be sent to instantiate or edit the Resources object in Authoring Model;
	 */

	public void makeResources(String gameName, double startingHealth, double starting$, String css) {
		myController.makeResources(gameName, startingHealth, starting$, css, getTheme());
	}

	/**
	 * Method through which information can be retrieved from AuthoringMOdel re: the current objects of a given type are available for editing
	 */
	public List<String> getCurrentObjectOptions(String objectType) {
		List<String> availableObjectOptions = new ArrayList<>(); 
		try {
			availableObjectOptions = myController.getCurrentObjectOptions(myLevel, objectType);
		} catch (ObjectNotFoundException e) {
			loadErrorScreen("NoObject");
		}
		return availableObjectOptions; 
	}

	/**
	 * Method through which information about object fields can be requested
	 * Invoked when populating authoring frontend screens used to edit existing objects
	 */
	public Object getObjectAttribute(String objectType, String objectName, String attribute) {
		Object returnedObjectAttribute = ""; 
		try {
			returnedObjectAttribute = myController.getObjectAttribute(myLevel, objectType, objectName, attribute);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | ObjectNotFoundException e) {
			loadErrorScreen("NoObject");
		} 
		return returnedObjectAttribute; 
	}

	/**
	 * Enumerates the current level that the user is editing 
	 * Useful when creating new objects so that Model may organize objects by the level in which they become accessible to the user 
	 * @param level - the level that the user has selected to edit
	 */
	protected void setLevel(int level) {
		myLevel = level; 
	}
	
	/**
	 * Returns the StageManager object used by the game to switch the Screens
	 * displayed on the Stage.
	 * 
	 * @return StageManager: the StageManager object in the game
	 */
	public StageManager getStageManager() {
	    return myStageManager;
	}

	protected void addNewLevel() {
		int newLevel = myController.addNewLevel(); 
		setLevel(newLevel);
	}

	protected List<String> getLevels() {
		return myController.getLevels(); 
	}

	protected void autogenerateLevel() {
		int newLevel = myController.autogenerateLevel(); 
		setLevel(newLevel); 
	}

	protected int getLevel() {
		return myLevel; 
	}

	public void setGameName(String gameName) {
		myController.setGameName(gameName);
	}
	
	protected Map<String, Integer> getEnemyNameToNumberMap(int level, int pathName, int waveNumber) { 
		try {
			Path path = myController.getPathFromName(pathName, level);
			return myController.getEnemyNameToNumberMap(level, path, waveNumber);
		}
		catch(ObjectNotFoundException e) {
		    	e.printStackTrace();
			loadErrorAlert("NoObject");
		}
		return new HashMap<>();

	}
	
	protected Integer getHighestWaveNumber(int level) {
	    try {
	    return myController.getHighestWaveNumber(level);
	    }
	    catch(ObjectNotFoundException e) {
		e.printStackTrace();
		loadErrorScreen("NoObject");
	    }
	    return 1;
	}

	protected void writeToFile() {
		try {
		    myController.writeToFile();
		} catch (ObjectNotFoundException e) {
		    loadErrorScreen("NoObject");
		} 
	}

	protected void readFromFile(String name) throws MissingPropertiesException {
	    myController.setModel(name);
	}
	

	protected BooleanProperty cssChangedProperty() {
		return myCSSChanged; 
	}

	protected String getGameName() {
		return myModel.getGameName();
	}

	protected void deleteObject(String objectType, String objectName) {
		try {
			myModel.deleteObject(myLevel, objectType, objectName);
		} catch (ObjectNotFoundException e) {
			loadErrorAlert("NoObject");
		}
	}


	public void makeTower(String name) throws NumberFormatException, FileNotFoundException, ObjectNotFoundException {
		try {
			myController.makeTower(myLevel, name);
		} catch (MissingPropertiesException e) {
			loadErrorAlert("NoImageFile");
		} catch (NoDuplicateNamesException e) {
			loadErrorAlert("NoDuplicateNames");
		} 
	}
	
	public void makeEnemy(String name) throws NumberFormatException, FileNotFoundException, ObjectNotFoundException {
		try {
			myController.makeEnemy(myLevel, name);
		} catch (MissingPropertiesException e) {
			loadErrorAlert("NoImageFile");
		} catch (NoDuplicateNamesException e) {
			loadErrorAlert("NoDuplicateNames");
		} 
	}
	
	public void setObjectAttribute(String objectType, String name, String attribute, Object attributeValue) {
		try {
			myController.setObjectAttribute(myLevel, objectType, name, attribute, attributeValue);
		} catch (IllegalArgumentException | IllegalAccessException | ObjectNotFoundException e) {
			loadErrorScreen("NoObject");
		}
	}
	
	public void setTheme(String selectedTheme) {
		myTheme = selectedTheme; 
		setObjectAttribute("Settings", "", "myGameTheme", myTheme);
	}
	
	public String getTheme() {
		if (myTheme == null) {
			try {
				myTheme = (String) myController.getObjectAttribute(1, "Settings", "", "myGameTheme");
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | ObjectNotFoundException e) {
				loadErrorAlert("NoFile");
			}
		}
		System.out.println(myTheme);
		return myTheme; 
	}

}