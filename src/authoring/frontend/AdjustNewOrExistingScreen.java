/**
 * @author susiechoi
 */

package authoring.frontend;

import javafx.scene.Parent;

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
	
}
