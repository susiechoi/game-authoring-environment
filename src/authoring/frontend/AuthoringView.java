package authoring.frontend;

import authoring.AuthoringController;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import frontend.View;
import javafx.scene.image.ImageView;

public class AuthoringView extends View {

	private StageManager myStageManager; 
	private PromptReader myPromptReader;
	private PropertiesReader myPropertiesReader;
	private AuthoringController myController; 

	public AuthoringView(StageManager stageManager, String languageIn, AuthoringController controller) {
		super(stageManager);
		myPromptReader = new PromptReader(languageIn, this);
		myPropertiesReader = new PropertiesReader();
		myStageManager = stageManager; 
		myController = controller; 
		myStageManager.switchScreen((new AdjustNewEnemyScreen(this)).getScreen());
	}

	protected String getErrorCheckedPrompt(String prompt) {
		return myPromptReader.resourceDisplayText(prompt);
	}

	public void getObjectAttribute(String objectType, String objectName, String attribute) {
		myController.getObjectAttribute(objectType, objectName, attribute);
	}
	
	public void makeTower(boolean newObject, String name, ImageView image, int health, int healthUpgradeCost, int healthUpgradeValue,
			ImageView projectileImage, String ability, int projectileDamage, int projectileValue, int projectileUpgradeCost, int projectileUpgradeValue,
			int launcherValue, int launcherUpgradeCost, int launcherUpgradeValue, int launcherSpeed, int launcherRange) {
		myController.makeTower(newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, ability, projectileDamage, projectileValue, projectileUpgradeCost, projectileUpgradeValue, 
				launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
	}
	
	public void makeEnemy(boolean newObject, String name, ImageView image, int speed, int healthImpact, int moneyImpact, int killReward, int killUpgradeCost, int killUpgradeValue) {
		myController.makeEnemy(newObject, name, image, speed, healthImpact, moneyImpact, killReward, killUpgradeCost, killUpgradeValue);
	}
	
	//TODO 
	public void addNewPath() {
		myController.addNewPath();
	}
	
	//	protected String getLanguage() {
	//		return myLanguage;
	//	}

	//	protected void goForwardFrom(Screen currentScreen) {
	//		String currentScreenName = currentScreen.getClass().getSimpleName();
	//		if (currentScreenName.equals("SpecifyEnemyScreen")) {
	//			myStageManager.switchScreen((new AdjustEnemyScreen(this)).getScreen()); 	// TODO replace with reflection?
	//		}
	//		else if (currentScreenName.equals("SpecifyTowerScreen")) {
	//			myStageManager.switchScreen((new AdjustTowerScreen(this)).getScreen());
	//		}
	//	}
	//
	//	protected void goBackFrom(Screen currentScreen) {
	//		String currentScreenName = currentScreen.getClass().getSimpleName();
	//		if (currentScreenName.equals("AdjustEnemyScreen")) {
	//			myStageManager.switchScreen((new SpecifyEnemyScreen(this).getScreen())); 	// TODO replace with reflection?
	//		}
	//		else if (currentScreenName.equals("AdjustTowerScreen")) {
	//			myStageManager.switchScreen((new SpecifyTowerScreen(this).getScreen())); 	
	//		}
	//	}

}
