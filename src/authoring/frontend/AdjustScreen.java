package authoring.frontend;

import javafx.scene.control.Button;

/**
 * Abstract class of Screens where values that need to be communicated to AuthoringModel
 * are changed (i.e. Screens that have some kind of "Apply" button).
 * @author Sarahbland
 *
 */
public abstract class AdjustScreen extends Screen {
	//TODO: set up listener here?
	public Button setupApplyButton(double length, double height) {
		return getUIFactory().makeButton("Apply", length, height); //TODO: set up prompts properties file	
	}

}
