package authoring.frontend;

import frontend.PromptReader;
import frontend.Screen;
import frontend.StageManager;
import javafx.stage.Stage;
import frontend.View;

class AuthoringView extends View {

	private StageManager myStageManager; 
	private PromptReader myReader;
	protected AuthoringView(StageManager stageManager, PromptReader reader) {
		super(stageManager);
	    	myReader = reader;
		myStageManager = stageManager; 
		//myStage.setScene(new SpecifyTowerScreen(this).getScreen());
		//myStage.setScene(new CustomizationChoicesScreen(this, "English", "Test Game").getScreen());
		//myStage.setScene((new CustomizationChoicesScreen(this, "Test Game")).getScreen());
//		myStage.setScene((new SettingsScreen(this).getScreen()));
		myStageManager.switchScreen((new AdjustEnemyScreen(this)).getScreen());
	}
//	
//	protected String getLanguage() {
//		return myLanguage;
//	}
	

//	protected void applyInfo() {
//		
//	}
	
	protected void goForwardFrom(Screen currentScreen) {
		String currentScreenName = currentScreen.getClass().getSimpleName();
		if (currentScreenName.equals("SpecifyEnemyScreen")) {
			myStageManager.switchScreen((new AdjustEnemyScreen(this)).getScreen()); 	// TODO replace with reflection?
		}
		else if (currentScreenName.equals("SpecifyTowerScreen")) {
			myStageManager.switchScren((new AdjustTowerScreen(this)).getScreen());
		}
	}
	
	protected void goBackFrom(Screen currentScreen) {
		String currentScreenName = currentScreen.getClass().getSimpleName();
		if (currentScreenName.equals("AdjustEnemyScreen")) {
			myStageManager.setScene((new SpecifyEnemyScreen(this).getScreen())); 	// TODO replace with reflection?
		}
		else if (currentScreenName.equals("AdjustTowerScreen")) {
			myStage.setScene((new SpecifyTowerScreen(this).getScreen())); 	
		}
	}
	public 

}
