/**
 * @author susiechoi
 * Abstract class used to present the designer the option of creating a new object or editing an existing one 
 */

package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

abstract class SpecifyObjectScreen extends AdjustScreen {

	public static final String DEFAULT_NEWOBJECT_TEXT = "Create New ";
	public static final String DEFAULT_GO_TEXT = "Go"; 
	public static final String DEFAULT_CONSTANT_FILEPATH = "src/frontend/Constants.properties";

	private String myDefaultName; 
	protected List<String> myObjectOptions; 
	private String myObjectDescription; 
	protected SpecifyObjectScreen(AuthoringView view, String objectDescription) {
		super(view);
		myObjectDescription = objectDescription;
		myObjectOptions = getView().getCurrentObjectOptions(myObjectDescription);
		try {
			myDefaultName = getView().getPropertiesReader().findVal(DEFAULT_CONSTANT_FILEPATH, "DefaultObjectName");
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoConstants");
		}
	}

	/**
	 * Makes the screen with the option of creating a new object OR editing an existing one 
	 * @return Parent/root to attach to Scene that will be set on the stage
	 */
	@Override
	protected Parent populateScreenWithFields() {
		VBox vb = new VBox(); 
		Text orText = new Text("or"); 

		Button newObjectButton = makeCreateNewObjectButton(myObjectDescription);
		String editPrompt = getErrorCheckedPrompt("EditExisting")+myObjectDescription;
		String selectPrompt = getErrorCheckedPrompt("SelectExisting")+myObjectDescription;

		List<String> dropdownOptions = new ArrayList<String>(); 
		dropdownOptions.add(selectPrompt);
		dropdownOptions.addAll(myObjectOptions);
		
		Button applyButton = getUIFactory().setupApplyButton();
		Button backButton = setupBackButton();

		ComboBox<String> objectsDropdown = getUIFactory().makeTextDropdownSelectAction("objectOptions",dropdownOptions, 
				e-> { applyButton.setDisable(false); }, 
				e-> { applyButton.setDisable(true);}, editPrompt); 
		applyButton.setDisable(true);
		applyButton.setOnAction(e -> {
			getView().goForwardFrom(this.getClass().getSimpleName()+"Apply", objectsDropdown.getValue());
		});

		HBox objectsWithPrompt = getUIFactory().addPromptAndSetupHBox("", objectsDropdown, editPrompt);

		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("Customize"+myObjectDescription)));
		vb.getChildren().add(newObjectButton);
		vb.getChildren().add(orText);
		vb.getChildren().add(objectsWithPrompt);
		vb.getChildren().add(backAndApplyButton);
		return vb;
	}
	@Override
	protected void populateFieldsWithData() {
		//null method, since this type of screen only has buttons TODO: make this not an abstract method??
	}


	/**
	 * For creating a button option to make a new object
	 * @param object - type of object being made
	 * @return Button to add to Parent
	 */
	protected Button makeCreateNewObjectButton(String object) {
		Button newObjectButton = getUIFactory().makeTextButton("newObjectButton", DEFAULT_NEWOBJECT_TEXT+object); 
		newObjectButton.setOnAction((event) -> {
			getView().goForwardFrom(this.getClass().getSimpleName()+"NewButton", myDefaultName);
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
