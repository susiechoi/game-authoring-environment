package authoring.frontend;



import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SettingsScreen extends AdjustScreen {
	public static final String BACKGROUND_IMAGES = "images/TowerImageNames.properties";
	PropertiesReader myPropertiesReader;
	String myLanguage;
	protected SettingsScreen(String language) {
		myPropertiesReader = new PropertiesReader();
		myLanguage = language;
	}
	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox();
		Text settingsHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SettingsHeading", myLanguage));
		HBox gameName = getUIFactory().setupPromptAndTextField("", getErrorCheckedPrompt("GameName", myLanguage));
		HBox backgroundImage = new HBox();
		try {
			backgroundImage = getUIFactory().setupImageSelector(myPropertiesReader, "", BACKGROUND_IMAGES, 50, getErrorCheckedPrompt("ImageName", myLanguage), 
					getErrorCheckedPrompt("ImageName","German"));
		}
		catch(MissingPropertiesException e){
			showDefaultNoFilesError();
		}
		vb.getChildren().add(gameName);
		vb.getChildren().add(backgroundImage);
		return new Scene(vb, 1500, 900);
	}

}
