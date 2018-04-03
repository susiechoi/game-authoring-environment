package authoring.frontend;
import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CustomizationChoicesScreen extends Screen {
	public static final String DEFAULT_OWN_CSS = "styling/GameAuthoringStartScreen.css";
	public static final String TEST_PROPERTIES = "images/TestProperties.properties";
	private String myLanguage;
	private String myGameName;
	
	protected CustomizationChoicesScreen(AuthoringView view, String language, String gameName) {
		super(view);
		setStyleSheet(DEFAULT_OWN_CSS);
		myLanguage = language;
		myGameName = gameName;
	}
	
	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException {
		VBox vbox = new VBox();
		HBox hbox = new HBox();
		Text heading = getUIFactory().makeScreenTitleText(myGameName);
		Button settingsButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("SettingsButtonLabel", myLanguage));
		Button newLevelButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CreateLevelLabel", myLanguage));
		Button demoButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("DemoLabel", myLanguage));
		Button saveButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("SaveLabel", myLanguage));
		Button mainButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("MainLabel", myLanguage));
		String levelPrompt = getErrorCheckedPrompt("EditDropdownLabel", myLanguage);
		ArrayList<String> dummyLevels = new ArrayList<>();
		VBox newLevelVBox = new VBox();
		dummyLevels.add(levelPrompt);
		dummyLevels.add("1");
		dummyLevels.add("2");
		Button editButton = getUIFactory().makeTextButton("editbutton", levelPrompt);
		ComboBox<String> levelChooser = getUIFactory().makeTextDropdownButtonEnable("", dummyLevels, e -> {
			editButton.setDisable(false);}, e -> {editButton.setDisable(true);}, levelPrompt);
		editButton.setDisable(true);
		VBox testSelector = new VBox();
		testSelector = getUIFactory().setupSelector(getPropertiesReader(), "", TEST_PROPERTIES);
		vbox.getChildren().add(heading);
		vbox.getChildren().add(settingsButton);
		vbox.getChildren().add(demoButton);
		vbox.getChildren().add(saveButton);
		hbox.getChildren().add(newLevelButton);
		newLevelVBox.getChildren().add(levelChooser);
		newLevelVBox.getChildren().add(editButton);
		hbox.getChildren().add(newLevelVBox);
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(mainButton);
		vbox.getChildren().add(testSelector);
		return new Scene(vbox, 1500, 900);
		
	}

}