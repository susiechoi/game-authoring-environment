package frontend;

import javafx.scene.layout.VBox;
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
    public void loadErrorScreen(String errorMessage) {
	VBox vb = new VBox();
	Text errorScreenMessage = new Text(errorMessage);
	vb.getChildren().add(errorScreenMessage);
	myManager.switchScreen(vb);
    }
    
}
