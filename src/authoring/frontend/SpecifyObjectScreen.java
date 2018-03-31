package authoring.frontend;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import authoring.frontend.PartsFactory; 

abstract class SpecifyObjectScreen implements Screen {
	
	public static final String DEFAULT_NEWOBJECT_TEXT = "Create New ";
	protected Parent myScreen;
	
	@Override
	public abstract void makeScreen();
	
	@Override
	public Parent getScreen() {
		if (myScreen == null) {
			makeScreen(); 
		}
		return myScreen; 
	}
	
	protected ComboBox<String> makeTextDropdown(List<String> textOptions, double length, double height) {
		return PartsFactory.makeTextDropdown(textOptions, length, height); 
	}
	
	protected ComboBox<Image> makeImageDropdown(List<Image> dropdownImages, double length, double height) {
		return PartsFactory.makeImageDropdown(dropdownImages, length, height); 
	}
	
	protected Button makeCreateNewObjectButton(String object) {
		return PartsFactory.makeButton(DEFAULT_NEWOBJECT_TEXT+object, 200, 20); 
	}


}
