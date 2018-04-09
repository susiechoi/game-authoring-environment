package authoring.frontend;

import java.util.List;
import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Sarahbland
 *
 */
public class SettingsScreen extends AdjustScreen {
    public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";


    private ComboBox<String> myImageDropdown;
    private TextField myGameNameEntry;
    
    protected SettingsScreen(AuthoringView view) {
	
	super(view);
    }
    
    @Override
    public Parent populateScreenWithFields(){
	VBox vb = new VBox();
	Text settingsHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SettingsHeading"));
	myGameNameEntry = getUIFactory().makeTextField("");
	HBox promptGameName = getUIFactory().addPromptAndSetupHBox("", myGameNameEntry, getErrorCheckedPrompt("GameName"));
	HBox backgroundImageSelector = new HBox();
	ImageView imageDisplay = new ImageView(); 
	try {
	List<String> imageDropdownOptions = getPropertiesReader().allKeys(BACKGROUND_IMAGES);
	ComboBox<String> imageDropdown = getUIFactory().makeTextDropdown("", imageDropdownOptions);
	backgroundImageSelector = getUIFactory().setupImageSelector(getPropertiesReader(),"", BACKGROUND_IMAGES, 100, getErrorCheckedPrompt("Background"), getErrorCheckedPrompt("LoadImage"),
		getErrorCheckedPrompt("NewImageName"), imageDropdown, imageDisplay);
	}
	catch(MissingPropertiesException e) {
	    getView().loadErrorScreen("NoImageFile");
	}
	HBox backgroundImagePrompted = getUIFactory().addPromptAndSetupHBox("", backgroundImageSelector, getErrorCheckedPrompt("Background"));
	
	Button backButton = setupBackButton();
	Button applyButton = getUIFactory().setupApplyButton();
	HBox backAndApplyButton = setupBackAndApplyButton(backButton, applyButton);
	
	vb.getChildren().add(settingsHeading);
	vb.getChildren().add(promptGameName);
	vb.getChildren().add(backgroundImagePrompted);
	vb.getChildren().add(backAndApplyButton);
	return vb;
    }

    @Override
    protected void populateFieldsWithData() {
	getUIFactory().setComboBoxToValue(myImageDropdown, getView().getObjectAttribute("Settings", "", "BackgroundImage"));
	myGameNameEntry.setText(getView().getObjectAttribute("Settings", "", "GameName"));
    }

}
