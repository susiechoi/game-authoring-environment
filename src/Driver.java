import controller.ChiefController;
import javafx.application.Application;
import javafx.stage.Stage;

/** 
 * Use the driver JavaFX library to start the application.
 *
 * @author Ben Hodgson 3/27/18
 *
 * 
 */
public class Driver extends Application {  

    /**
     * Initialize the program and begin the animation loop 
     * 
     * @param stage: Primary stage to attach all scenes
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
	ChiefController controller = new ChiefController(primaryStage);
//	controller.play();
    }

    /**
     * Start the program
     */
    public static void main (String[] args) {
	launch(args);
    }
}
