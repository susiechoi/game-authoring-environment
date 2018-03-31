package authoring.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrontendLauncherForTesting extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		AdjustEnemyScreen e = new AdjustEnemyScreen();
		Scene sc = new Scene(e.getScreen(), 400, 400);
		Stage s = new Stage(); 
		s.setScene(sc);
		s.show();
	}

}
