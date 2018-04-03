package authoring.frontend;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

public class FrontendLauncherForTesting extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
//		ArrayList<String> enemyOptions = new ArrayList<String>();
//		enemyOptions.add("Tower 1");
//		enemyOptions.add("Tower 2");
//		SpecifyEnemyScreen a = new SpecifyEnemyScreen(enemyOptions);
//		AdjustTowerScreen a = new AdjustTowerScreen(); 
		//GameAuthoringStartScreen a = new GameAuthoringStartScreen("English");
		//SettingsScreen a = new SettingsScreen("English");
		//AdjustTowerScreen a = new AdjustTowerScreen(); 
		  CustomizationChoicesScreen a = new CustomizationChoicesScreen("English", "Test Game");
//		AdjustEnemyScreen a = new AdjustEnemyScreen(); 
		Stage s = new Stage(); 
		s.setScene(a.getScreen());
		s.show();
	}

}
