package authoring.frontend;

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
//		AdjustEnemyScreen a = new AdjustTowerScreen(); 

		AuthoringView view = new AuthoringView(stage, "English");
		CreatePathScreen a = new CreatePathScreen(view);

		Stage s = new Stage(); 
		s.setScene(a.getScreen());
		s.show();
//		stage.show();

	}

}
