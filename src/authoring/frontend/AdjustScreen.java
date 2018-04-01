package authoring.frontend;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 * 
 * Abstract class of Screens where values that need to be communicated to AuthoringModel
 * are changed (i.e. Screens that have some kind of "Apply" button)
 * @author Sarahbland
 * @author susiechoi
 *
 */

public abstract class AdjustScreen extends Screen {

	public static final String DEFAULT_BACK_URL = "http://bsa824.org/Troop824/Go-Back-arrow.gif"; 
	
	protected HBox setupBackAndApply() {
		HBox hb = new HBox();
		Button backButton = setupBackButton();
		backButton.setId("backButton");
		hb.getChildren().add(backButton);
		Button applyButton = setupApplyButton();
		applyButton.setId("applyButton");
		hb.getChildren().add(applyButton);
		return hb; 
	}
	
	protected Button setupBackButton() {
		Image backbuttonImage = new Image(DEFAULT_BACK_URL, 50, 30, true, false);
		Button backButton = myUIFactory.makeImageButton(backbuttonImage);
		return backButton; 
	}

	//TODO: set up listener here?
	protected Button setupApplyButton() {
		return myUIFactory.makeTextButton("Apply", 75, 40); //TODO: set up prompts properties file	
	}

}
