/**
 * @author Sarah Bland
 * @author susiechoi
 * 
 * Represents View of authoring environment's MVC. 
 * Allows for screen transitions and the communication of object altering/creation to Controller. 
 */

package authoring.frontend;

import java.awt.Point;
import java.awt.geom.Point2D;
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
import frontend.ErrorReader;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import frontend.View;
import gameplayer.ScreenManager;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import xml.AuthoringModelWriter;

public class AuthoringView extends View {

	public static final String DEFAULT_SCREENFLOW_FILEPATH = "src/frontend/ScreenFlow.properties";
	public static final String DEFAULT_ERROR_FILEPATH_BEGINNING = "languages/";
	public static final String DEFAULT_ERROR_FILEPATH_END = "/Errors.properties";
	public static final String DEFAULT_AUTHORING_CSS = "styling/GameAuthoringStartScreen.css";
	private StageManager myStageManager; 
	private PromptReader myPromptReader;
	private ErrorReader myErrorReader;
	private PropertiesReader myPropertiesReader;
	private AuthoringController myController; 
	private String myCurrentCSS;
	private int myLevel; 
	private HashMap<String, List<Point>> myImageMap;
	private AuthoringModel myModel;


	public AuthoringView(StageManager stageManager, String languageIn, AuthoringController controller) {
		super(stageManager);
		myPromptReader = new PromptReader(languageIn, this);
		myErrorReader = new ErrorReader(languageIn, this);
		myPropertiesReader = new PropertiesReader();
		myStageManager = stageManager; 
		myController = controller; 
		myCurrentCSS = new String(DEFAULT_AUTHORING_CSS);
	}


	public void setModel(AuthoringModel model) {
		myModel = model;
	}

	public void loadInitialScreen() {
		myStageManager.switchScreen((new StartScreen(this)).getScreen());
	}

	@Override
	public void loadErrorScreen(String error) {
		loadErrorScreenToStage(myErrorReader.resourceDisplayText(error));
	}
	public void loadErrorAlert(String error) {
		loadErrorAlertToStage(myErrorReader.resourceDisplayText(error));
	}
	protected void loadScreen(Screen screen) {
		myStageManager.switchScreen(screen.getScreen());
	}
	protected String getCurrentCSS() {
		return myCurrentCSS;
	}
	protected void addWaveEnemy(int level, String pathName, int waveNumber, String enemyKey, int amount) {
		//myController.addWaveEnemy(level, pathName, waveNumber, enemyKey, amount);
	}

	protected void goBackFrom(String id) {
		goForwardFrom(id+"Back");
	}
	protected void goForwardFrom(String id) {
		goForwardFrom(id, "");
	}
	protected void goForwardFrom(String id, String name) {
		try {
			String nextScreenClass = myPropertiesReader.findVal(DEFAULT_SCREENFLOW_FILEPATH, id);
			Class<?> clazz = Class.forName(nextScreenClass);
			System.out.println("next class: " + nextScreenClass);
			Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
			if(constructor.getParameterTypes().length == 2) {
				System.out.println("our name "+name);
				if(constructor.getParameterTypes()[1].equals(AuthoringModel.class)) {
					AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this, myModel);
					myStageManager.switchScreen(nextScreen.getScreen());
				}
				else {
					AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this, name);
					myStageManager.switchScreen(nextScreen.getScreen());
				}
			}
			else if(constructor.getParameterTypes()[0].equals(AuthoringView.class)) {
				AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this);
				myStageManager.switchScreen(nextScreen.getScreen());
			}
			else if(constructor.getParameterTypes()[0].equals(ScreenManager.class)) {
				Screen nextScreen = (Screen) constructor.newInstance(new ScreenManager(myStageManager, "English"));
				myStageManager.switchScreen(nextScreen.getScreen());
			} //TODO: handle case where switching to gameplay
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


	/**
	 * Method through which information can be sent to instantiate or edit a tower object in Authoring Model;
	 * @throws NoDuplicateNamesException 
	 */
	public void makeTower(boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
			String projectileImage, double projectileDamage, double projectileUpgradeCost, double projectileUpgradeValue, double projectileSize, double projectileSpeed,
			double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange,
			double towerValue, double towerUpgradeCost, double towerUpgradeValue) throws NoDuplicateNamesException {
		try {
			myController.makeTower(myLevel, newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
					projectileImage, projectileDamage, projectileUpgradeCost, projectileUpgradeValue, projectileSize, projectileSpeed,
					launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange,
					towerValue, towerUpgradeCost, towerUpgradeValue);
		} catch (MissingPropertiesException e) {
			loadErrorScreen("NoImageFile");
		} catch (ObjectNotFoundException e) {
			loadErrorScreen("NoObject");
		}
	}

	/**
	 * Method through which information can be sent to instantiate or edit an enemy object in Authoring Model;
	 * @throws NoDuplicateNamesException 
	 */

	public void makeEnemy(boolean newObject, String name, String image, double speed, double initialHealth, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) throws NoDuplicateNamesException {

		try {
			myController.makeEnemy(myLevel, newObject, name, image, speed, initialHealth, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
		} catch (MissingPropertiesException e) {
			loadErrorScreen("NoImageFile");
		} 
		catch (ObjectNotFoundException e) {
			loadErrorScreen("NoObject");
		}
	}

	public void makePath(GridPane grid, List<Point> coordinates, HashMap<String, List<Point>> imageCoordinates, String backgroundImage) {
		myController.makePath(myLevel, grid, coordinates, imageCoordinates, backgroundImage);
		myImageMap = imageCoordinates;
	}


	/**
	 * Method through which information can be sent to instantiate or edit the Resources object in Authoring Model;
	 */

	public void makeResources(String gameName, double startingHealth, double starting$) {
		myController.makeResources(gameName, startingHealth, starting$);
	}

	/**
	 * Method through which information can be retrieved from AuthoringMOdel re: the current objects of a given type are available for editing
	 */
	public List<String> getCurrentObjectOptions(String objectType) {
		List<String> availableObjectOptions = new ArrayList<String>(); 
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
	public String getObjectAttribute(String objectType, String objectName, String attribute) {
		String returnedObjectAttribute = ""; 
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

	protected Scene getScene() {
		return myStageManager.getScene();
	}

	public String getErrorCheckedPrompt(String prompt) {
		return myPromptReader.resourceDisplayText(prompt);
	}

	public void addNewLevel() {
		int newLevel = myController.addNewLevel(); 
		setLevel(newLevel);
	}

	public List<String> getLevels() {
		return myController.getLevels(); 
	}

	public void autogenerateLevel() {
		int newLevel = myController.autogenerateLevel(); 
		setLevel(newLevel); 
	}

	public int getLevel() {
		return myLevel; 
	}

	protected PropertiesReader getPropertiesReader() {
		return myPropertiesReader; 
	}

	public void setGameName(String gameName) {
		myController.setGameName(gameName);
	}
	public Map<String, Integer> getEnemyNameToNumberMap(int level, int pathName, int waveNumber) { 
		try {
			Path path = myController.getPathFromName(pathName, level);
			return myController.getEnemyNameToNumberMap(level, path, waveNumber);
		}
		catch(ObjectNotFoundException e) {
			loadErrorAlert("NoObject");
		}
		return new HashMap<String, Integer>();

	}
	public void writeToFile() {
		AuthoringModelWriter writer = new AuthoringModelWriter();
		System.out.println("SAVING" + myModel.getGameName());
		writer.write(myModel, myModel.getGameName());
	}

	public void readFromFile(String name) {
	    myController.setModel(name);
	}
	

	public HashMap<String, List<Point>> getImageCoordinates() {
		return myImageMap;
	}

}