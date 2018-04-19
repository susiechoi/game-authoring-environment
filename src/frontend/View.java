package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Abstract class for View classes in both frontends (authoring and gameplay). Used for common
 * functionality of loading error screens.
 * @author Sarahbland
 *
 */
public abstract class View {
    StageManager myManager;
    public View(StageManager manager) {
	myManager = manager;
    }
    
    /**
     * Loadst the stage with a given ErrorMessage
     * @param errorMessage is message to be displayed
     */
    public void loadErrorScreenToStage(String errorMessage) {
	VBox vb = new VBox();
	Text errorScreenMessage = new Text(errorMessage);
	vb.setAlignment(Pos.CENTER);
	errorScreenMessage.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
	vb.getChildren().add(errorScreenMessage);
	myManager.switchScreen(vb);
    }
    
    /**
     * Loads an error alert (does not stop program) to notify user of a problem
     * @param errorMessage is message to be displayed
     */
    public void loadErrorAlertToStage(String errorMessage) {
		    Alert alert = new Alert(AlertType.ERROR);
		    alert.setContentText(errorMessage);
		    alert.showAndWait();
    }
   
    
    /**
     * Loads error Message and takes action specific to the View when an error occurs
     * @param errorMessage is message needed to be displayed
     */
    public abstract void loadErrorScreen(String errorMessage);
    
}
