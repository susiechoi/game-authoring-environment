package authoring.frontend;

import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import javafx.stage.Stage;
import frontend.View;

class AuthoringView extends View {

	private StageManager myStageManager; 
	private PromptReader myPromptReader;
	private PropertiesReader myPropertiesReader;
	protected AuthoringView(StageManager stageManager, String languageIn) {
		super(stageManager);
		myPromptReader = new PromptReader(languageIn, this);
		myPropertiesReader = new PropertiesReader();
		myStageManager = stageManager; 
		//myStage.setScene(new SpecifyTowerScreen(this).getScreen());
		//myStage.setScene(new CustomizationChoicesScreen(this, "English", "Test Game").getScreen());
		//myStage.setScene((new CustomizationChoicesScreen(this, "Test Game")).getScreen());
		//		myStage.setScene((new SettingsScreen(this).getScreen()));
		myStageManager.switchScreen((new CustomizeLevelScreen(this)).getScreen());
	}
	
	//	
	//	protected String getLanguage() {
	//		return myLanguage;
	//	}

	protected void goForwardFrom(Screen currentScreen) {
		String currentScreenName = currentScreen.getClass().getSimpleName();
		if (currentScreenName.equals("SpecifyEnemyScreen")) {
			myStageManager.switchScreen((new AdjustEnemyScreen(this)).getScreen()); 	// TODO replace with reflection?
		}
		else if (currentScreenName.equals("SpecifyTowerScreen")) {
			myStageManager.switchScreen((new AdjustTowerScreen(this)).getScreen());
		}
	}

	protected void goBackFrom(Screen currentScreen) {
		String currentScreenName = currentScreen.getClass().getSimpleName();
		if (currentScreenName.equals("AdjustEnemyScreen")) {
			myStageManager.switchScreen((new SpecifyEnemyScreen(this).getScreen())); 	// TODO replace with reflection?
		}
		else if (currentScreenName.equals("AdjustTowerScreen")) {
			myStageManager.switchScreen((new SpecifyTowerScreen(this).getScreen())); 	
		}
	}
	
	protected String getErrorCheckedPrompt(String prompt) {
		return myPromptReader.resourceDisplayText(prompt);
	}

}
