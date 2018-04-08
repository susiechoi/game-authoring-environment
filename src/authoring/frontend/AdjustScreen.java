package authoring.frontend;

import java.io.File;

import frontend.Screen;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 * 
 * Abstract class of Screens where values that need to be communicated to AuthoringModel
 * are changed (i.e. Screens that have some kind of "Apply" button)
 * @author susiechoi
 *
 */

abstract class AdjustScreen extends AuthoringScreen {
		
	public static final String DEFAULT_BACK_IMAGE = "images/back.gif"; 
	
	protected AdjustScreen(AuthoringView view) {
		super(view);
	}
	
	/**
	 * @return  HBox with back and apply buttons
	 */
	protected HBox setupBackAndApplyButton() {
		HBox hb = new HBox();
		Button backButton = setupBackButton(this);
		hb.getChildren().add(backButton);
		Button applyButton = setupApplyButton();
		applyButton.setOnMouseClicked((event) -> { 
			// TODO 
		});
		hb.getChildren().add(applyButton);
		return hb; 
	}

	//TODO: set up listener here?
	protected Button setupApplyButton() {
		Button applyButton = getUIFactory().makeTextButton("applyButton", "Apply"); //TODO: set up prompts properties file	
		return applyButton;
	}
	
	protected Button setupBackButton(Screen currentScreen) {
		Image backbuttonImage = new Image((new File(DEFAULT_BACK_IMAGE)).toURI().toString(), 60, 40, true, false); // TODO move to css
		Button backButton = getUIFactory().makeImageButton("backButton",backbuttonImage);
		backButton.setOnMouseClicked((event) -> { 
			getView().goBackFrom(currentScreen);
		}); 
		return backButton; 
	}
	
}
