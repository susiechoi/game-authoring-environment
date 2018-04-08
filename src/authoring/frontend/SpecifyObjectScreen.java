package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	myObjectOptions.add("Dummy Object 1");
	myObjectOptions.add("Dummy Object 2");
	myObjectOptions.add("Dummy Object 3");
	setStyleSheet(DEFAULT_OWN_STYLESHEET);
    }

	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
	Text orText = new Text("or"); 
	orText.setId("or");

	Button newObjectButton = makeCreateNewObjectButton(myObjectDescription);

	ComboBox<String> objectsDropdown = getUIFactory().makeTextDropdown("objectOptions",myObjectOptions);
	HBox objectsWithPrompt = getUIFactory().addPromptAndSetupHBox("", objectsDropdown, getErrorCheckedPrompt("EditExisting", getView().getLanguage())+myObjectDescription);
	HBox backAndApplyButton = setupBackAndApplyButton();

	vb.getChildren().add(getUIFactory().makeScreenTitleText(myObjectDescription));
	vb.getChildren().add(newObjectButton);
	vb.getChildren().add(orText);
	vb.getChildren().add(objectsWithPrompt);
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
