package authoring.frontend;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import authoring.AuthoringController;
import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import frontend.View;
import gameplayer.ScreenManager;
import javafx.scene.Scene;

public class AuthoringView extends View {
	
	public static final String DEFAULT_SCREENFLOW_FILEPATH = "src/frontend/ScreenFlow.properties";
	public static final String DEFAULT_AUTHORING_CSS = "styling/GameAuthoringStartScreen";
	
	private StageManager myStageManager; 
	private PromptReader myPromptReader;
	private PropertiesReader myPropertiesReader;
	private AuthoringController myController; 
	private String myCurrentCSS;

	public AuthoringView(StageManager stageManager, String languageIn, AuthoringController controller) {
		super(stageManager);
		myPromptReader = new PromptReader(languageIn, this);
		myPropertiesReader = new PropertiesReader();
		myStageManager = stageManager; 
		myController = controller; 
		myStageManager.switchScreen((new CustomizationChoicesScreen(this)).getScreen());
		myCurrentCSS = DEFAULT_AUTHORING_CSS;
	}
	protected void loadScreen(Screen screen) {
	    myStageManager.switchScreen(screen.getScreen());
	}
	protected void loadScene(Scene scene) { //TODO: refactor so no duplication?
	    myStageManager.switchScene(scene);
	}
	protected String getCurrentCSS() {
	    return myCurrentCSS;
	}

	protected void goBackFrom(String id) {
	    goForwardFrom(id+"Back");
	}
	protected void goForwardFrom(String id) {
		try {
		    	System.out.println("id getting sent: " + id);
			String nextScreenClass = myPropertiesReader.findVal(DEFAULT_SCREENFLOW_FILEPATH, id);
			Class<?> clazz = Class.forName(nextScreenClass);
			Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
			if(constructor.getParameterTypes()[0].equals(AuthoringView.class)) {
				AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this);
				myStageManager.switchScreen(nextScreen.getScreen());
			}
			else if(constructor.getParameterTypes()[0].equals(ScreenManager.class)) {
				Screen nextScreen = (Screen) constructor.newInstance(new ScreenManager(myStageManager, myPromptReader));
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

	protected String getErrorCheckedPrompt(String prompt) {
		return myPromptReader.resourceDisplayText(prompt);
	}

	public void getObjectAttribute(String objectType, String objectName, String attribute) {
		myController.getObjectAttribute(objectType, objectName, attribute);
	}

	public void makeTower(boolean newObject, String name, String image, int health, int healthUpgradeCost, int healthUpgradeValue,
			String projectileImage, String ability, int projectileDamage, int projectileValue, int projectileUpgradeCost, int projectileUpgradeValue,
			int launcherValue, int launcherUpgradeCost, int launcherUpgradeValue, int launcherSpeed, int launcherRange) {
		myController.makeTower(newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, ability, projectileDamage, projectileValue, projectileUpgradeCost, projectileUpgradeValue, 
				launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
	}

	public void makeEnemy(boolean newObject, String name, String image, int speed, int healthImpact, int moneyImpact, int killReward, int killUpgradeCost, int killUpgradeValue) {
		myController.makeEnemy(newObject, name, image, speed, healthImpact, moneyImpact, killReward, killUpgradeCost, killUpgradeValue);
	}

	//TODO 
	public void addNewPath() {
		myController.addNewPath();
	}
	
	protected Scene getScene() {
	    return myStageManager.getScene();
	}



}
