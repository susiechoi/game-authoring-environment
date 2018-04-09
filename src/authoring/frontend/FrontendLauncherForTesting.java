package authoring.frontend;

import authoring.AuthoringController;
import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.PropertiesReader;
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

		PropertiesReader pr = new PropertiesReader();
		try {
			System.out.println(pr.findVal("images/EnemyImageNames.properties", "Zombie"));
			System.out.println(pr.findVal("default_objects/GenericTower.properties", "projectileSize"));
		} catch (MissingPropertiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    	System.out.println("launching");
		AuthoringController controlla = new AuthoringController(new StageManager(stage), "English");
		stage.show();

	}

}
