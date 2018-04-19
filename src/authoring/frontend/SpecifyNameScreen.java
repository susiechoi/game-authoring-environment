package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

abstract class SpecifyNameScreen extends AdjustScreen {
	
	public static final String DEFAULT_CONSTANTS = "src/frontend/Constants.properties";

	private String myDefaultObjectName; 
	private String myObjectDescription; 
	private TextField myNameField; 

	protected SpecifyNameScreen(AuthoringView view, String objectDescription) {
		super(view);
		myObjectDescription = objectDescription; 
		setConstants(); 
	}
	
	private void setConstants() {
		try {
			myDefaultObjectName = getPropertiesReader().findVal(DEFAULT_CONSTANTS, "DefaultObjectName");
		} catch (NumberFormatException e) {
			getView().loadErrorScreen("BadConstants");
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

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SpecifyObjectName")+myObjectDescription));
		TextField nameField = getUIFactory().makeTextField("");
		vb.getChildren().add(nameField);
		myNameField = nameField; 

		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			if (validNameField(myNameField)) {
				if (this.getClass().getSimpleName().equals("SpecifyTowerNameScreen")) {
				getView().makeTower(myNameField.getText());
				}
				if (this.getClass().getSimpleName().equals("SpecifyEnemyNameScreen")) {
					getView().makeEnemy(myNameField.getText());
				}
				getView().goForwardFrom(this.getClass().getSimpleName()+"Apply",myNameField.getText());
			}
		});
		vb.getChildren().add(applyButton);

		return vb;
	}

	@Override
	protected void populateFieldsWithData() {
		//null method, since this type of screen only has buttons 
	}
	
	protected boolean validNameField(TextField nameField) {
		boolean valid = true; 
		if (nameField.getText().length() == 0) {
			getView().loadErrorAlert("PopulateName");
			valid = false; 
		}
		else if (nameField.getText().equals(myDefaultObjectName)) {
			getView().loadErrorAlert("NoDefaultName");
			valid = false; 
		}
		return valid; 
	}

}
