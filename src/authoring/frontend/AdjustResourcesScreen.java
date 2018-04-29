/**
 * @author susiechoi
 * @author sarahbland
 * Creates screen in which user can customize the starting resources of the player
 */

package authoring.frontend;

import com.sun.javafx.tools.packager.Log;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdjustResourcesScreen extends AdjustNewOrExistingScreen {
	
	public static final String DEFAULT_GAME_NAME_KEY = "myGameName"; 
	public static final String DEFAULT_HEALTH_KEY = "myStartingHealth";
	public static final String DEFAULT_MONEY_KEY = "myStartingMoney";
	public static final String DEFAULT_CSS_STYLES = "src/styling/CurrentCSS.properties";
	public static final String OBJECT_TYPE = "Settings";
	
    	private TextField myGameNameEntry;
	private Slider myStartingHealthSlider;
	private Slider myStartingCurrencySlider;
//	private ComboBox<String> myCSSFilenameChooser;
	
    	protected AdjustResourcesScreen(AuthoringView view) {
		super(view);
	}

	/**
	 * Creates features (specifically, sliders) that users can manipulate to change starting reosurces of player
	 */
	@Override
	public Parent populateScreenWithFields(){
		VBox vb = new VBox(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SpecifyStartingResources")));

		Text settingsHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SettingsHeading"));
		myGameNameEntry = getUIFactory().makeTextField();
		vb.getChildren().add(settingsHeading);
		int maxStartingHealth = 0;
		int maxStartingCurrency = 0;
		try {
		    maxStartingHealth = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS_FILEPATH, "StartingHealth"));
		    maxStartingCurrency = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS_FILEPATH, "StartingMoney"));
		}
		catch(MissingPropertiesException e) {
		    Log.debug(e);
		    getView().loadErrorScreen("NoConstants");
		}
		HBox promptGameName = getUIFactory().addPromptAndSetupHBox(myGameNameEntry, getErrorCheckedPrompt("GameName"));
		vb.getChildren().add(promptGameName);	
		myStartingHealthSlider = getUIFactory().setupSlider(maxStartingHealth);
		myStartingHealthSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, DEFAULT_HEALTH_KEY, newValue);
		});
		HBox startingHealth = getUIFactory().setupSliderWithValue(myStartingHealthSlider, getErrorCheckedPrompt("StartingHealth"));
		vb.getChildren().add(startingHealth);
		myStartingCurrencySlider = getUIFactory().setupSlider(maxStartingCurrency);

		myStartingCurrencySlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, DEFAULT_MONEY_KEY, newValue);
		});
		HBox startingCurrency = getUIFactory().setupSliderWithValue(myStartingCurrencySlider, getErrorCheckedPrompt("StartingCurrency"));
		vb.getChildren().add(startingCurrency);

//		List<String> cssOptions = new ArrayList<>(); 
//		try {
//			cssOptions = getPropertiesReader().allKeys(DEFAULT_CSS_STYLES);
//		} catch (MissingPropertiesException e1) {
//		    	Log.debug(e1);
//			getView().loadErrorAlert("NoFile");
//		}
//		myCSSFilenameChooser = getUIFactory().makeTextDropdown(cssOptions);
//		vb.getChildren().add(getUIFactory().addPromptAndSetupHBox(myCSSFilenameChooser, getErrorCheckedPrompt("CSS")));
//		myCSSFilenameChooser.addEventHandler(ActionEvent.ACTION, e -> {
//			getView().setObjectAttribute(OBJECT_TYPE, "myCSSTheme", myCSSFilenameChooser.getSelectionModel().getSelectedItem()); 
//		});
		
		Button backButton = setupBackButton();
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
		    	setSaved();
		    	getView().setGameName(myGameNameEntry.getText());
		    	getView().setObjectAttribute(OBJECT_TYPE, DEFAULT_GAME_NAME_KEY, myGameNameEntry.getText());
		    	getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
		});
		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
		
		vb.getChildren().add(backAndApplyButton);
		
		return vb;
	}

	@Override
	protected void populateFieldsWithData() {
		myGameNameEntry.setText(getView().getObjectAttribute(OBJECT_TYPE, DEFAULT_GAME_NAME_KEY).toString());
		getUIFactory().setSliderToValue(myStartingHealthSlider, getView().getObjectAttribute(OBJECT_TYPE, DEFAULT_HEALTH_KEY).toString());
		getUIFactory().setSliderToValue(myStartingCurrencySlider, getView().getObjectAttribute(OBJECT_TYPE, DEFAULT_MONEY_KEY).toString());	
	}
}
