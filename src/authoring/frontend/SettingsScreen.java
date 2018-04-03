package authoring.frontend;



import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SettingsScreen extends AdjustScreen {
	public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";
	public static final String DEFAULT_OWN_CSS = "styling/AdjustEnemyTower.css";
	private String myLanguage;
	
	protected SettingsScreen(AuthoringView view, String language) {
		super(view);
		myLanguage = language;
		setStyleSheet(DEFAULT_OWN_CSS);
	}
	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox();
		Text settingsHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SettingsHeading", myLanguage));
		HBox gameName = getUIFactory().setupPromptAndTextField("", getErrorCheckedPrompt("GameName", myLanguage));
		HBox backgroundImage = new HBox();
		try {
			backgroundImage = getUIFactory().setupImageSelector(getPropertiesReader(),"", BACKGROUND_IMAGES, 50, getErrorCheckedPrompt("LoadImage", myLanguage), getErrorCheckedPrompt("Background", myLanguage));
		}
		catch(MissingPropertiesException e){
			showDefaultNoFilesError();
		}
		HBox backAndApply = setupBackAndApplyButton();
		vb.getChildren().add(settingsHeading);
		vb.getChildren().add(gameName);
		vb.getChildren().add(backgroundImage);
		vb.getChildren().add(backAndApply);
		return new Scene(vb, 1500, 900);
	}

}
