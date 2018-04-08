/**
 * @author susiechoi
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

abstract class AdjustNewOrExistingScreen extends AdjustScreen {

	public static final String DEFAULT_CONSTANTS = "frontend/Constants.properties";
	
	protected int myMaxHealthImpact;
	protected int myMaxSpeed;
	protected int myMaxRange;
	protected int myMaxPrice; 
	protected int myMaxUpgradeIncrement; 
	
	private boolean myIsNewObject; 	
	
	protected AdjustNewOrExistingScreen(AuthoringView view) {
		super(view);
		setConstants();
	}

	private void setConstants() {
		try {
			myMaxHealthImpact = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxHealthImpact"));
			myMaxSpeed = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxSpeed"));
			myMaxRange = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxRange"));
			myMaxPrice = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxPrice"));
			myMaxUpgradeIncrement = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxUpgradeIncrement"));
		} catch (NumberFormatException e) {
			getView().loadErrorScreen("BadConstants");
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoConstants");
		}
		
	}
	
	@Override
	public Parent makeScreenWithoutStyling() {
		Parent constructedScreen = populateScreenWithFields();
		populateFieldsWithData(); 
		return constructedScreen;
	}
	
	protected void setIsNewObject(boolean isNewObject) {
		myIsNewObject = isNewObject; 
	}
	
	protected abstract Parent populateScreenWithFields();
	protected abstract void populateFieldsWithData(); 

	protected boolean getIsNewObject() {
		return myIsNewObject; 
	}
	
	protected void setComboBoxToValue(ComboBox<String> combobox, String selectionValue) {
		int dropdownIdx = combobox.getItems().indexOf(selectionValue); 
		combobox.getSelectionModel().select(dropdownIdx);
	}
	
	protected void setSliderToValue(Slider slider, String valueAsString) {
		Double value = Double.parseDouble(valueAsString);
		slider.setValue(value);
	}
	
}
