package authoring.frontend;

import java.util.ArrayList;

import authoring.AuthoringController;
import authoring.AuthoringView;
import javafx.application.Application;
import javafx.stage.Stage;

public class FrontendLauncherForTesting extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		AuthoringView view = new AuthoringView(stage);
		stage.show();
	}

}
