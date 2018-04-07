package authoring.frontend;

import authoring.AuthoringController;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import frontend.View;

public class AuthoringView extends View {

	private StageManager myStageManager; 
	private PromptReader myPromptReader;
	private PropertiesReader myPropertiesReader;
	private AuthoringController myController; 
	
	public AuthoringView(StageManager stageManager, String languageIn) {
		super(stageManager);
		myPromptReader = new PromptReader(languageIn, this);
		myPropertiesReader = new PropertiesReader();
		myStageManager = stageManager; 
		myController = null; // TODO what to do with this constructor? 
		myStageManager.switchScreen((new AdjustNewLauncherProjectileScreen(this)).getScreen());
	}
	
	protected AuthoringView(StageManager stageManager, String languageIn, AuthoringController controller) {
		super(stageManager);
		myPromptReader = new PromptReader(languageIn, this);
		myPropertiesReader = new PropertiesReader();
		myStageManager = stageManager; 
		myController = controller; 
		myStageManager.switchScreen((new CustomizeLevelScreen(this)).getScreen());
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
	
	protected String getErrorCheckedPrompt(String prompt) {
		return myPromptReader.resourceDisplayText(prompt);
	}

}
