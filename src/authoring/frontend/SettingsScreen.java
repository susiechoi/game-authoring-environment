package authoring.frontend;

import java.util.List;
import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Sarahbland
 *
 */
public class SettingsScreen extends AdjustScreen {
    public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";
    public static final String DEFAULT_OWN_CSS = "styling/GameAuthoringStartScreen.css";

    protected SettingsScreen(AuthoringView view) {
	super(view);
	setStyleSheet(DEFAULT_OWN_CSS);
    }
    @Override
    public Parent makeScreenWithoutStyling(){
	VBox vb = new VBox();
	Text settingsHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SettingsHeading"));
	TextField gameNameEntry = getUIFactory().makeTextField("");
	HBox promptGameName = getUIFactory().addPromptAndSetupHBox("", gameNameEntry, getErrorCheckedPrompt("GameName"));
	HBox backgroundImageSelector = new HBox();
	try {
	List<String> imageDropdownOptions = getPropertiesReader().allKeys(BACKGROUND_IMAGES);
	ComboBox<String> imageDropdown = getUIFactory().makeTextDropdown("", imageDropdownOptions);
	backgroundImageSelector = getUIFactory().setupImageSelector(getPropertiesReader(),"", BACKGROUND_IMAGES, 100, getErrorCheckedPrompt("Background"), getErrorCheckedPrompt("LoadImage"),
		getErrorCheckedPrompt("NewImageName"), imageDropdown);
	}
	catch(MissingPropertiesException e) {
	    getView().loadErrorScreen("NoImageFile");
	}
	HBox backgroundImagePrompted = getUIFactory().addPromptAndSetupHBox("", backgroundImageSelector, getErrorCheckedPrompt("Background"));
	HBox backAndApply = setupBackAndApplyButton();
	vb.getChildren().add(settingsHeading);
	vb.getChildren().add(promptGameName);
	vb.getChildren().add(backgroundImagePrompted);
	vb.getChildren().add(backAndApply);
	return vb;
    }

}
