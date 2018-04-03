package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

abstract class SpecifyObjectScreen extends AdjustScreen {

	public static final String DEFAULT_NEWOBJECT_TEXT = "Create New ";
	public static final String DEFAULT_GO_TEXT = "Go"; 
	public static final String DEFAULT_OWN_STYLESHEET = "styling/SpecifyObjectScreen.css"; 
	protected List<String> myObjectOptions; 
	private String myObjectDescription; 

	protected SpecifyObjectScreen(AuthoringView view) {
		super(view);
		myObjectOptions = new ArrayList<String>(); // TODO read in objects
		setStyleSheet(DEFAULT_OWN_STYLESHEET);
	}

	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 

		Text orText = new Text("or"); 
		orText.setId("or");

		Button newObjectButton = makeCreateNewObjectButton(myObjectDescription);
		
		HBox objectsDropdown = getUIFactory().setupPromptAndDropdown("objectOptions", "Edit Existing "+myObjectDescription, myObjectOptions); 
		HBox backAndApplyButton = setupBackAndApplyButton();
		
		vb.getChildren().add(getUIFactory().makeScreenTitleText(myObjectDescription));
		vb.getChildren().add(newObjectButton);
		vb.getChildren().add(orText);
		vb.getChildren().add(objectsDropdown);
		vb.getChildren().add(backAndApplyButton);

		return new Scene(vb, 1500, 900); // TODO move to properties file
	}

	protected Button makeCreateNewObjectButton(String object) {
		Button newObjectButton = getUIFactory().makeTextButton("newObjectButton", DEFAULT_NEWOBJECT_TEXT+object); 
		newObjectButton.setOnAction((event) -> {
			getView().goForwardFrom(this);
		});
		return newObjectButton;
	}
	
	protected void setDescription(String description) {
		myObjectDescription = description;
	}
	
	protected String getDescription() {
		return myObjectDescription; 
	}

}
