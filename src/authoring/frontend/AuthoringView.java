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
    public static final String DEFAULT_AUTHORING_CSS = "styling/GameAuthoringStartScreen.css";
    private StageManager myStageManager; 
    private PromptReader myPromptReader;
    private PropertiesReader myPropertiesReader;
    private AuthoringController myController; 
    private String myCurrentCSS;
    private int myLevel; 

    public AuthoringView(StageManager stageManager, String languageIn, AuthoringController controller) {
	super(stageManager);
	myPromptReader = new PromptReader(languageIn, this);
	myPropertiesReader = new PropertiesReader();
	myStageManager = stageManager; 
	myController = controller; 
	myCurrentCSS = new String(DEFAULT_AUTHORING_CSS);
	myStageManager.switchScreen((new StartScreen(this)).getScreen());
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

    public void makeTower(int level, boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
	    String projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue,
	    double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
	myController.makeTower(level, newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
		projectileImage, projectileDamage, projectileValue, projectileUpgradeCost, projectileUpgradeValue, 
		launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
    }

    public void makeEnemy(int level, boolean newObject, String name, String image, double speed, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) {
	myController.makeEnemy(level, newObject, name, image, speed, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
    }

    //TODO 
    public void makePath(int level) {
	myController.makePath(level);
    }

    public String getObjectAttribute(String objectType, String objectName, String attribute) {
	return myController.getObjectAttribute(objectType, objectName, attribute);
    }

    protected Scene getScene() {
	return myStageManager.getScene();
    }

    protected void setLevel(int level) {
	myLevel = level; 
    }

    protected int getLevel() {
	return myLevel; 
    }


}
