package authoring.frontend;

import javafx.application.Application;
import javafx.stage.Stage;

public class FrontendLauncherForTesting extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		SpecifyEnemyScreen e = new SpecifyEnemyScreen();
		Stage s = new Stage(); 
		s.setScene(e.getScreen());
		s.show();
	}

}
