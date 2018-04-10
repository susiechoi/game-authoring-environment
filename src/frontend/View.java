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
    
    public void loadErrorScreenToStage(String errorMessage) {
	VBox vb = new VBox();
	Text errorScreenMessage = new Text(errorMessage);
	vb.setAlignment(Pos.CENTER);
	errorScreenMessage.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
	vb.getChildren().add(errorScreenMessage);
	myManager.switchScreen(vb);
    }
    
    public void loadErrorAlertToStage(String errorMessage) {
		    Alert alert = new Alert(AlertType.ERROR);
		    alert.setContentText(errorMessage);
		    alert.showAndWait();
    }
    
    public void loadErrorAlertToStage(String errorMessage, Screen returnToScreen) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setContentText(errorMessage);
	    myManager.switchScreen(returnToScreen.getScreen());
	    alert.showAndWait();
}

    public abstract void loadErrorScreen(String errorMessage);
    
}
