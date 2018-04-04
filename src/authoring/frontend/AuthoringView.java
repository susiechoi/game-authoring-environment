package authoring.frontend;

import javafx.stage.Stage;

class AuthoringView {

	private Stage myStage; 
	private String myLanguage;
	
	protected AuthoringView(Stage stage, String language) {
		myLanguage = language;
		myStage = stage; 
		myStage.setScene(new AdjustResourcesScreen(this).getScreen());
//		myStage.setScene(new CustomizationChoicesScreen(this, "English", "Test Game").getScreen());
	}
	
	protected String getLanguage() {
		return myLanguage;
	}
	

//	protected void applyInfo() {
//		
//	}
	
	protected void goForwardFrom(Screen currentScreen) {
		String currentScreenName = currentScreen.getClass().getSimpleName();
		if (currentScreenName.equals("SpecifyEnemyScreen")) {
			myStage.setScene(new AdjustEnemyScreen(this).getScreen()); 	// TODO replace with reflection?
		}
		else if (currentScreenName.equals("SpecifyTowerScreen")) {
			myStage.setScene(new AdjustTowerScreen(this).getScreen());
		}
	}
	
	protected void goBackFrom(Screen currentScreen) {
		String currentScreenName = currentScreen.getClass().getSimpleName();
		if (currentScreenName.equals("AdjustEnemyScreen")) {
			myStage.setScene(new SpecifyEnemyScreen(this).getScreen()); 	// TODO replace with reflection?
		}
		else if (currentScreenName.equals("AdjustTowerScreen")) {
			myStage.setScene(new SpecifyTowerScreen(this).getScreen()); 	
		}
	}
	
}
