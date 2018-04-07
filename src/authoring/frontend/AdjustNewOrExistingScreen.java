package authoring.frontend;

import javafx.scene.Parent;

abstract class AdjustNewOrExistingScreen extends AdjustScreen {

	protected AdjustNewOrExistingScreen(AuthoringView view) {
		super(view);
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		Parent constructedScreen = populateScreenWithFields();
		populateFieldsWithData(); 
		return constructedScreen;
	}
	
	protected abstract Parent populateScreenWithFields();
	protected abstract void populateFieldsWithData(); 

}
