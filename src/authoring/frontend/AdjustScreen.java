package authoring.frontend;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

/**
 * Abstract class of Screens where values that need to be communicated to AuthoringModel
 * are changed (i.e. Screens that have some kind of "Apply" button).
 * @author Sarahbland
 * @author susiechoi
 *
 */

public abstract class AdjustScreen extends Screen {

	protected Button setupBackButton() {
		Image backbuttonImage = new Image("http://bsa824.org/Troop824/Go-Back-arrow.gif", 50, 30, true, false);
		Button backButton = myUIFactory.makeImageButton(backbuttonImage);
		return backButton; 
	}

	//TODO: set up listener here?
	public Button setupApplyButton() {
		return myUIFactory.makeTextButton("Apply", 75, 40); //TODO: set up prompts properties file	
	}

}
