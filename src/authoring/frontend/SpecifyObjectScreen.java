package authoring.frontend;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;

abstract class SpecifyObjectScreen extends Screen {
	
	public static final String DEFAULT_NEWOBJECT_TEXT = "Create New ";
	public static final String DEFAULT_GO_TEXT = "Go"; 
	public static final String DEFAULT_OWN_STYLESHEET = "styling/SpecifyObjectScreen.css"; 
	
	@Override
	public Scene makeScreen() {
		myScreen = makeScreenWithoutStyling();
		applyDefaultStyling(); 
		applyStyle(DEFAULT_OWN_STYLESHEET);
		return myScreen; 
	}
	
	public abstract Scene makeScreenWithoutStyling();
	
	protected ComboBox<String> makeTextDropdown(List<String> textOptions) {
		return getUIFactory().makeTextDropdown(textOptions, 250, 50); 
	}
	
	protected ComboBox<Image> makeImageDropdown(List<Image> dropdownImages) {
		return getUIFactory().makeImageDropdown(dropdownImages, 200, 20); 
	}
	
	protected Button makeCreateNewObjectButton(String object) {
		return getUIFactory().makeButton(DEFAULT_NEWOBJECT_TEXT+object, 300, 80); 
	}
	
	protected Button makeGoButton() {
		Button goButton = getUIFactory().makeButton(DEFAULT_GO_TEXT, 100, 50);
		goButton.setId("goButton");
		return goButton; 
	}

}
