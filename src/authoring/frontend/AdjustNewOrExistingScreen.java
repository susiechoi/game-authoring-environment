/**
 * @author susiechoi
 * Abstract class of screens that have both "new" and "existing" object edit options 
 * (e.g. AdjustTowerScreen extends AdjustNewOrExistingScreen because a designer can edit 
 * a new or existing Tower) 
 * 
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

abstract class AdjustNewOrExistingScreen extends AdjustScreen {

	public static final String DEFAULT_CONSTANTS = "src/frontend/Constants.properties";
	
	private String mySelectedObjectName; 
	
	private String myDefaultObjectName; 
	private int myMaxHealthImpact;
	private int myMaxSpeed;
	private int myMaxRange;
	private int myMaxPrice; 
	private int myMaxUpgradeIncrement; 

	private boolean myIsNewObject; 	
	
	protected AdjustNewOrExistingScreen(AuthoringView view, String selectedObjectName) {
		super(view);
		setConstants(); 
		mySelectedObjectName = selectedObjectName; 
		myIsNewObject = selectedObjectName.equals(myDefaultObjectName);
//		System.out.println(mySelectedObjectName+" should be nothing ");
	}

	private void setConstants() {
		try {
			myDefaultObjectName = getPropertiesReader().findVal(DEFAULT_CONSTANTS, "DefaultObjectName");
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
	
	/**
	 * For all screens in which users can edit either new or existing objects, the makeScreenWithoutStyling method should 
	 * ensure that the screen is populated with fields and that, if deemed necessary by the subclass, 
	 * the fields are populated with data (in the case that an existing object is being edited) 
	 */
	@Override
	public Parent makeScreenWithoutStyling() {
		Parent constructedScreen = populateScreenWithFields();
		populateFieldsWithData(); 
		return constructedScreen;
	}
	
	protected abstract Parent populateScreenWithFields();
	
	protected abstract void populateFieldsWithData();
	
	/**
	 * Used when the changes on the Screen are applied and the Screen must convey whether the object that has been created is new or existing 
	 * @return
	 */
	protected boolean getIsNewObject() {
		return myIsNewObject; 
	}
	
	protected void setEditableOrNot(TextField name, boolean isNewObject) {
		if (!isNewObject) name.setEditable(false);
	}
	
	protected String getMyDefaultName() {
		return myDefaultObjectName; 
	}
	
	/** 
	 * The following methods are getters for range-specifying constants so that subclasses may know what range to depict on their sliders
	 * @return int max of the sliders 
	 */
	protected int getMyMaxHealthImpact() {
		return myMaxHealthImpact;
	}
	
	protected int getMyMaxSpeed() {
		return myMaxSpeed;
	}
	
	protected int getMyMaxRange() {
		return myMaxRange; 
	}
	
	protected int getMyMaxPrice() {
		return myMaxPrice;
	}
	
	protected int getMyMaxUpgradeIncrement() {
		return myMaxUpgradeIncrement; 
	}
	
	protected String getMySelectedObjectName() {
		return mySelectedObjectName; 
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