package authoring.frontend;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import authoring.frontend.PartsFactory; 

abstract class SpecifyObjectScreen extends Screen {
	
	public static final String DEFAULT_NEWOBJECT_TEXT = "Create New ";
	public static final String DEFAULT_OWN_STYLESHEET = "styling/SpecifyObjectScreen.css"; 
	protected Scene myScreen;
	
	@Override
	public void makeScreen() {
		ArrayList<String> stylesheets = new ArrayList<String>();
		stylesheets.add(DEFAULT_SHARED_STYLESHEET);
		stylesheets.add(DEFAULT_OWN_STYLESHEET);
		makeScreenWithStyles(stylesheets);
	}
	
	protected abstract void makeScreenWithStyles(List<String> stylesheets);
	
	protected ComboBox<String> makeTextDropdown(List<String> textOptions) {
		return getUIFactory().makeTextDropdown(textOptions, 200, 20); 
	}
	
	protected ComboBox<Image> makeImageDropdown(List<Image> dropdownImages) {
		return getUIFactory().makeImageDropdown(dropdownImages, 200, 20); 
	}
	
	protected Button makeCreateNewObjectButton(String object) {
		return getUIFactory().makeButton(DEFAULT_NEWOBJECT_TEXT+object, 200, 80); 
	}

}
