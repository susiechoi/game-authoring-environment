package authoring.frontend;
import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CustomizationChoicesScreen extends AuthoringScreen {
    public static final String TEST_PROPERTIES = "images/TestProperties.properties";
    public static final String DEFAULT_OWN_CSS = "styling/GameAuthoringStartScreen.css";
    private String myGameName;

    protected CustomizationChoicesScreen(AuthoringView view) {
	//TODO: figure out how to not get gamename!!!
	super(view);
	//setStyle(DEFAULT_OWN_CSS);
	myGameName = "TEST";
    }

    @Override
    public Parent makeScreenWithoutStyling(){
	VBox vbox = new VBox();
	HBox hbox = new HBox();
	Text heading = getUIFactory().makeScreenTitleText(myGameName);
	Button settingsButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("SettingsButtonLabel"));
	Button newLevelButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CreateLevelLabel"));
	Button demoButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("DemoLabel"));
	Button saveButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("SaveLabel"));
	Button mainButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("MainLabel"));
	String levelPrompt = getErrorCheckedPrompt("EditDropdownLabel");
	ArrayList<String> dummyLevels = new ArrayList<>();
	VBox newLevelVBox = new VBox();
	dummyLevels.add(levelPrompt);
	dummyLevels.add("1");
	dummyLevels.add("2");
	Button editButton = getUIFactory().makeTextButton("editbutton", levelPrompt);
	ComboBox<String> levelChooser = getUIFactory().makeTextDropdownSelectAction("", dummyLevels, e -> {
	    editButton.setDisable(false);}, e -> {editButton.setDisable(true);}, levelPrompt);
	editButton.setDisable(true);
	HBox songSelector = new HBox();
	ComboBox<String> songDropdown = new ComboBox<>();
	try {
	songDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TEST_PROPERTIES));
	}
	catch(MissingPropertiesException e){
	    getView().loadErrorScreen("NoFile");
	}
	try {
	songSelector = getUIFactory().setupImageSelector(getPropertiesReader(), "", TEST_PROPERTIES, 100, getErrorCheckedPrompt("Song"), getErrorCheckedPrompt("NewSong"),
		getErrorCheckedPrompt("NewSongName"), songDropdown);
	}
	catch(MissingPropertiesException e) {
	    getView().loadErrorScreen("NoFile");
	}
	HBox songPrompted = getUIFactory().addPromptAndSetupHBox("", songSelector, getErrorCheckedPrompt("Song"));
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
	vbox.getChildren().add(songPrompted);
	return vbox;

    }

}