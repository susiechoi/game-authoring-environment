/**
 * @author susiechoi
 */

package authoring.frontend;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * 
 * Abstract class of Screens where values that need to be communicated to AuthoringModel
 * are changed (i.e. Screens that have some kind of "Apply" button)
 * @author susiechoi
 *
 */

abstract class AdjustScreen extends AuthoringScreen {
		
	public static final String DEFAULT_OWN_STYLESHEET = "styling/GameAuthoringStartScreen.css";
	public static final String DEFAULT_BACK_IMAGE = "images/back.gif"; 
	
	protected AdjustScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET);
	}
	
	protected HBox setupBackAndApplyButton(Button back, Button apply) {
		HBox backAndApplyButton = new HBox(); 
		backAndApplyButton.getChildren().add(back);
		backAndApplyButton.getChildren().add(apply);
		return backAndApplyButton; 
	}
	
}
