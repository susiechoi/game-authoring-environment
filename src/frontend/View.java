package frontend;

import javafx.geometry.Pos;
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
    public abstract void loadErrorScreen(String errorMessage);
    
}
