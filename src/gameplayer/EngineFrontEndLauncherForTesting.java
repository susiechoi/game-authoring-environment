package gameplayer;

import javafx.application.Application;
import javafx.stage.Stage;

public class EngineFrontEndLauncherForTesting extends Application {

    /**
     * Initialize the program and begin the animation loop 
     * 
     * @param stage: Primary stage to attach all scenes
     */
    @Override
    public void start(Stage primaryStage){
	ScreenManager manager = new ScreenManager(primaryStage);
	manager.loadInstructionScreen();		
    }

    /**
     * Start the program
     */
    public static void main (String[] args) {
	launch(args);
    }
}
