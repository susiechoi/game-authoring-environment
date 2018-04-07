/**
 * @author susiechoi
 */

package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

abstract class AdjustNewOrExistingScreen extends AdjustScreen {

	private boolean myIsNewObject; 
	
	protected AdjustNewOrExistingScreen(AuthoringView view) {
		super(view);
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
