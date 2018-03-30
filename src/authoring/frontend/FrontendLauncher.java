// ONLY FOR TESTING PURPOSES!!!

package authoring.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrontendLauncher extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		EnemyScreen e = new EnemyScreen();
		Scene sc = new Scene(e.getScreen(), 400, 400);
		Stage s = new Stage(); 
		s.setScene(sc);
		s.show();
	}

}
