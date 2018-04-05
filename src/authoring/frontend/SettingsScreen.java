package authoring.frontend;




import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SettingsScreen extends AdjustScreen {
	public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";
	public static final String DEFAULT_OWN_CSS = "styling/AdjustEnemyTower.css";
	
	protected SettingsScreen(AuthoringView view, String language) {
		super(view);
		setStyleSheet(DEFAULT_OWN_CSS);
	}
	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException {
		VBox vb = new VBox();
		Text settingsHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SettingsHeading", getView().getLanguage()));
		TextField gameNameEntry = getUIFactory().makeTextField("");
		HBox promptGameName = getUIFactory().addPromptAndSetupHBox("", gameNameEntry, getErrorCheckedPrompt("GameName", getView().getLanguage()));
		HBox backgroundImageSelector = new HBox();
		List<String> imageDropdownOptions = getPropertiesReader().allKeys(BACKGROUND_IMAGES);
		ComboBox<String> imageDropdown = getUIFactory().makeTextDropdown("", imageDropdownOptions);
		try {
			backgroundImageSelector = getUIFactory().setupImageSelector(getPropertiesReader(),"", BACKGROUND_IMAGES, 50, getErrorCheckedPrompt("LoadImage", getView().getLanguage()), getErrorCheckedPrompt("Background", getView().getLanguage()),
					getErrorCheckedPrompt("NewImage", getView().getLanguage()), imageDropdown);
		}
		catch(MissingPropertiesException e){
			showDefaultNoFilesError();
		}
		HBox backAndApply = setupBackAndApplyButton();
		vb.getChildren().add(settingsHeading);
		vb.getChildren().add(promptGameName);
		vb.getChildren().add(backgroundImageSelector);
		vb.getChildren().add(backAndApply);
		return new Scene(vb, 1500, 900);
	}

}
