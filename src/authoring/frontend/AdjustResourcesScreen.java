/**
 * @author susiechoi
 * @author sarahbland
 * Creates screen in which user can customize the starting resources of the player
 */

package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdjustResourcesScreen extends AdjustNewOrExistingScreen {
    	private TextField myGameNameEntry;
	//private ComboBox<String> myCSSFilenameChooser; TODO: implement!
	private Slider myStartingHealthSlider;
	private Slider myStartingCurrencySlider;
	
    	protected AdjustResourcesScreen(AuthoringView view) {
		super(view);
	}

	/**
	 * Creates features (specifically, sliders) that users can manipulate to change starting reosurces of player
	 */
	@Override
	public Parent populateScreenWithFields(){
		Text settingsHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SettingsHeading"));
		myGameNameEntry = getUIFactory().makeTextField("");
		HBox promptGameName = getUIFactory().addPromptAndSetupHBox("", myGameNameEntry, getErrorCheckedPrompt("GameName"));
		VBox vb = new VBox(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SpecifyStartingResources")));

		myStartingHealthSlider = getUIFactory().setupSlider("startingHealth", 100);
		HBox startingHealth = getUIFactory().setupSliderWithValue("startingHealth", myStartingHealthSlider, getErrorCheckedPrompt("StartingHealth"));
		myStartingCurrencySlider = getUIFactory().setupSlider("startingCurrency", 999);
		HBox startingCurrency = getUIFactory().setupSliderWithValue("startingCurrency", myStartingCurrencySlider, getErrorCheckedPrompt("StartingCurrency"));

		Button backButton = setupBackButton();
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
		    	setSaved();
			getView().makeResources(myGameNameEntry.getText(), myStartingHealthSlider.getValue(), myStartingCurrencySlider.getValue());//TODO fix
			getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
		});
		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
		
		vb.getChildren().add(settingsHeading);
		vb.getChildren().add(promptGameName);
		vb.getChildren().add(startingHealth);
		vb.getChildren().add(startingCurrency);
		vb.getChildren().add(backAndApplyButton);
		
		return vb;
	}

	@Override
	protected void populateFieldsWithData() {
		myGameNameEntry.setText(getView().getObjectAttribute("Settings", "", "myGameName").toString());
		getUIFactory().setSliderToValue(myStartingHealthSlider, getView().getObjectAttribute("Settings", "", "myStartingHealth").toString());
		getUIFactory().setSliderToValue(myStartingCurrencySlider, getView().getObjectAttribute("Settings", "", "myStartingMoney").toString());	    
	}
}
