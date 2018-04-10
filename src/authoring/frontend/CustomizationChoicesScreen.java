package authoring.frontend;
import java.util.ArrayList;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CustomizationChoicesScreen extends AuthoringScreen {
    public static final String TEST_PROPERTIES = "images/TestProperties.properties";
    private String myGameName;
    
    protected CustomizationChoicesScreen(AuthoringView view) {
	//TODO: figure out how to not get gamename!!!
	super(view);
//	myGameName = "TEST";
    }

    @Override
    public Parent makeScreenWithoutStyling(){
	VBox vbox = new VBox();
	HBox hbox = new HBox();
//	Text heading = getUIFactory().makeScreenTitleText(myGameName);
//	vbox.getChildren().add(heading);

	Button resourcesButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeResources"));
	resourcesButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+"ResourcesButton");});
	vbox.getChildren().add(resourcesButton);
	
	Button newLevelButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CreateLevelLabel"));
	newLevelButton.setOnAction(e -> {
		getView().addNewLevel();
	    getView().goForwardFrom(this.getClass().getSimpleName()+"EditNewLevel");
	});
	Button demoButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("DemoLabel"));
	Button saveButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("SaveLabel"));
	Button mainButton = setupBackButton();
	String levelPrompt = getErrorCheckedPrompt("EditDropdownLabel");
	vbox.getChildren().add(demoButton);
	vbox.getChildren().add(saveButton);
	hbox.getChildren().add(newLevelButton);
	
	List<String> currentLevels = new ArrayList<String>();
	currentLevels.add(levelPrompt);
	currentLevels.addAll(getView().getLevels()); 
	if (currentLevels.size() > 0) {
		VBox newLevelVBox = new VBox(); 
		Button editButton = getUIFactory().makeTextButton("editbutton", levelPrompt);
		ComboBox<String> levelChooser = getUIFactory().makeTextDropdownSelectAction("", currentLevels, e -> {
		    editButton.setDisable(false);}, e -> {editButton.setDisable(true);}, levelPrompt);
		editButton.setDisable(true);
		editButton.setOnAction(e -> {
		    getView().setLevel(Integer.parseInt(levelChooser.getValue()));
		    getView().goForwardFrom(this.getClass().getSimpleName()+"EditExistingLevel");
		    });
		Button autogenerateButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("AutogenerateLevel"));
		autogenerateButton.setOnAction(e -> {
			getView().autogenerateLevel(); 
		    getView().goForwardFrom(this.getClass().getSimpleName()+"EditExistingLevel");
		});
		newLevelVBox.getChildren().add(levelChooser);
		newLevelVBox.getChildren().add(editButton);
		hbox.getChildren().add(newLevelVBox);
		hbox.getChildren().add(autogenerateButton);
	}
	HBox songSelector = new HBox();
	ComboBox<String> songDropdown = new ComboBox<>();
	try {
	songDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TEST_PROPERTIES));
	}
	catch(MissingPropertiesException e){
	    getView().loadErrorScreen("NoFile");
	}
	ImageView imageDisplay = new ImageView(); 
	try {
	songSelector = getUIFactory().setupImageSelector(getPropertiesReader(), "", TEST_PROPERTIES, 100, getErrorCheckedPrompt("Song"), getErrorCheckedPrompt("NewSong"),
		getErrorCheckedPrompt("NewSongName"), songDropdown, imageDisplay);
	}
	catch(MissingPropertiesException e) {
	    getView().loadErrorScreen("NoFile");
	}
	HBox songPrompted = getUIFactory().addPromptAndSetupHBox("", songSelector, getErrorCheckedPrompt("Song"));
	
	vbox.getChildren().add(hbox);
	vbox.getChildren().add(songPrompted);
	vbox.getChildren().add(mainButton);
	return vbox;

    }

}