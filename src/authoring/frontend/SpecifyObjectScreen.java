package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

abstract class SpecifyObjectScreen extends AdjustScreen {
	
	public static final String DEFAULT_NEWOBJECT_TEXT = "Create New ";
	public static final String DEFAULT_GO_TEXT = "Go"; 
	public static final String DEFAULT_OWN_STYLESHEET = "styling/SpecifyObjectScreen.css"; 
	protected List<String> myObjectOptions; 
	protected String myObjectDescription; 
	
	protected SpecifyObjectScreen(List<String> objectOptions) {
		myObjectOptions = objectOptions; 
		myStylesheet = DEFAULT_OWN_STYLESHEET;
	}
	
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
		
		Text orText = new Text("or"); 
		orText.setId("or");
	
		vb.getChildren().add(makeCreateNewObjectButton(myObjectDescription));
		vb.getChildren().add(orText);
		vb.getChildren().add(makeObjectOptionsDropdown());
		vb.getChildren().add(setupBackAndApplyButton());
		
		return new Scene(vb, 1500, 900); // TODO move to properties file
	}
	
	protected Button makeCreateNewObjectButton(String object) {
		Button newObjectButton = myUIFactory.makeTextButton("newObjectButton", DEFAULT_NEWOBJECT_TEXT+object); 
		return newObjectButton;
	}
		
	protected ComboBox<String> makeObjectOptionsDropdown() {
		ArrayList<String> dropdownOptions = new ArrayList<String>(); 
		dropdownOptions.add("Edit Existing "+myObjectDescription);
		dropdownOptions.addAll(myObjectOptions);
		ComboBox<String> dropdown = myUIFactory.makeTextDropdown("objectOptions", dropdownOptions); 
		return dropdown; 
	}

}
