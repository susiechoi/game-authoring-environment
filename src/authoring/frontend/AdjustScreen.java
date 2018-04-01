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
	
	/**
	 * @return  HBox with back and apply buttons
	 */
	protected HBox setupBackAndApplyButton() {
		HBox hb = new HBox();
		Button backButton = setupBackButton();
		hb.getChildren().add(backButton);
		Button applyButton = setupApplyButton();
		hb.getChildren().add(applyButton);
		return hb; 
	}
	
	protected Button setupBackButton() {
		Image backbuttonImage = new Image(DEFAULT_BACK_URL, 60, 40, true, false); // TODO move to css
		Button backButton = myUIFactory.makeImageButton("backButton",backbuttonImage);
		return backButton; 
	}

	//TODO: set up listener here?
	protected Button setupApplyButton() {
		Button applyButton = myUIFactory.makeTextButton("applyButton", "Apply"); //TODO: set up prompts properties file	
		return applyButton;
	}

}
