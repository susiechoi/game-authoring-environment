package authoring.frontend;

import authoring.AuthoringController;
import frontend.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class FrontendLauncherForTesting extends Application {

	public static void main(String[] args) {
	    	System.out.println("launching");
		launch(args);
	}

	@Override
	public void start(Stage stage) {
	    	System.out.println("launching");
		AuthoringController controlla = new AuthoringController(new StageManager(stage), "English");
		stage.show();
	}

}
