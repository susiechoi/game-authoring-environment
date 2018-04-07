/**
 * @author susiechoi
 */

package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

abstract class SpecifyObjectScreen extends AdjustScreen {

	public static final String DEFAULT_NEWOBJECT_TEXT = "Create New ";
	public static final String DEFAULT_GO_TEXT = "Go"; 
	protected List<String> myObjectOptions; 
	private String myObjectDescription; 

	protected SpecifyObjectScreen(AuthoringView view) {
		super(view);
		myObjectOptions = new ArrayList<String>(); // TODO read in objects
		myObjectOptions.add("Dummy Object 1");
		myObjectOptions.add("Dummy Object 2");
		myObjectOptions.add("Dummy Object 3");
	}

	public Parent makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
		Text orText = new Text("or"); 

		Button newObjectButton = makeCreateNewObjectButton(myObjectDescription);

		ComboBox<String> objectsDropdown = getUIFactory().makeTextDropdown("objectOptions",myObjectOptions);
		HBox objectsWithPrompt = getUIFactory().addPromptAndSetupHBox("", objectsDropdown, getErrorCheckedPrompt("EditExisting")+myObjectDescription);
		HBox backAndApplyButton = setupBackAndApplyButton();

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("Customize"+myObjectDescription)));
		vb.getChildren().add(newObjectButton);
		vb.getChildren().add(orText);
		vb.getChildren().add(objectsWithPrompt);
		vb.getChildren().add(backAndApplyButton);

		return vb; // TODO move to properties file
	}

	protected Button makeCreateNewObjectButton(String object) {
		Button newObjectButton = getUIFactory().makeTextButton("newObjectButton", DEFAULT_NEWOBJECT_TEXT+object); 
		newObjectButton.setOnAction((event) -> {
		    getView().goForwardFrom(this.getClass().getSimpleName()+"NewButton");
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
