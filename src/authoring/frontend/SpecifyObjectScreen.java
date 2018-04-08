/**
 * @author susiechoi
 * Abstract class used to present the designer the option of creating a new object or editing an existing one 
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

	/**
	 * Makes the screen with the option of creating a new object OR editing an existing one 
	 * @return Parent/root to attach to Scene that will be set on the stage
	 */
	public Parent makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
		Text orText = new Text("or"); 

		Button newObjectButton = makeCreateNewObjectButton(myObjectDescription);

		ComboBox<String> objectsDropdown = getUIFactory().makeTextDropdown("objectOptions",myObjectOptions);
		HBox objectsWithPrompt = getUIFactory().addPromptAndSetupHBox("", objectsDropdown, getErrorCheckedPrompt("EditExisting")+myObjectDescription);
		
		Button backButton = setupBackButton();
		Button applyButton = getUIFactory().setupApplyButton();
		HBox backAndApplyButton = setupBackAndApplyButton(backButton, applyButton);

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("Customize"+myObjectDescription)));
		vb.getChildren().add(newObjectButton);
		vb.getChildren().add(orText);
		vb.getChildren().add(objectsWithPrompt);
		vb.getChildren().add(backAndApplyButton);

		return vb;
	}

	/**
	 * For creating a button option to make a new object
	 * @param object - type of object being made
	 * @return Button to add to Parent
	 */
	protected Button makeCreateNewObjectButton(String object) {
		Button newObjectButton = getUIFactory().makeTextButton("newObjectButton", DEFAULT_NEWOBJECT_TEXT+object); 
		newObjectButton.setOnAction((event) -> {
		    getView().goForwardFrom(this.getClass().getSimpleName()+"NewButton");
		});
		return newObjectButton;
	}

	/** 
	 * For setting the type of object that the subclasses control editing of (Tower, Screen, etc) 
	 * Useful when populating buttons and prompts (e.g. Create New Tower) 
	 * @param object type
	 */
	protected void setDescription(String description) {
		myObjectDescription = description;
	}

	/**
	 * For returning the type of object that the subclasses control editing of  
	 * @return object type
	 */
	protected String getDescription() {
		return myObjectDescription; 
	}

}
