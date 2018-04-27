package authoring.frontend;
import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringModel;
import authoring.frontend.exceptions.MissingPropertiesException;
import controller.PlayController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class to create Screen where users choose which elements they would like to customize (a level,
 * settings, etc.) or save/demo a game. Dependent on the View to house/relay information from the Model
 * and on View screenflow methods to send the user to the correct next screen.
 * @author Sarahbland
 * @author susiechoi
 */

public class CustomizationChoicesScreen extends AuthoringScreen {
	public static final String TEST_PROPERTIES = "images/TestProperties.properties";


	protected CustomizationChoicesScreen(AuthoringView view, AuthoringModel model) {
	    	super(view);
	}

	
	/**
	 * Creates UI elements (buttons, selectors) necessary for User to choose next customization
	 * or demo/save.
	 * @see frontend.Screen#makeScreenWithoutStyling()
	 */
	@Override
	
	public Parent makeScreenWithoutStyling(){
		VBox vbox = new VBox();
		HBox hbox = new HBox();
		HBox newLevelHBox = new HBox();

		Text heading = getUIFactory().makeScreenTitleText(getView().getGameName());
		vbox.getChildren().add(heading);

		Button resourcesButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeResources"));
		resourcesButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+"ResourcesButton");});
		vbox.getChildren().add(resourcesButton);

		Button newLevelButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CreateLevelLabel"));
		newLevelButton.setOnAction(e -> {
			getView().addNewLevel();
			getView().goForwardFrom(this.getClass().getSimpleName()+"EditNewLevel");
		});
		AuthoringModel currentModel = getView().getModel();
		Button demoButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("DemoLabel"));
		demoButton.setOnAction(e -> {
		    	getView().writeToFile();
		    	new PlayController(getView().getStageManager(), getView().getLanguage(),
				currentModel).demoPlay(currentModel.getGame()); //TODO: there has to be a way to do this with listeners - can't be good to give a Screen the Model
		});
		Button saveButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("SaveLabel"));
		saveButton.setDisable(false);
		saveButton.setOnAction(e -> {
		    	setSaved();
		    	saveButton.setDisable(true);
			getView().writeToFile();
		});
		Button mainButton = setupBackButton();
		String levelPrompt = getErrorCheckedPrompt("EditDropdownLabel");

		List<String> currentLevels = new ArrayList<String>();
		currentLevels.add(levelPrompt);
		currentLevels.addAll(getView().getLevels()); 
		if (currentLevels.size() > 0) {
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
			hbox.getChildren().add(levelChooser);
			hbox.getChildren().add(editButton);
			newLevelHBox.getChildren().add(newLevelButton);
			newLevelHBox.getChildren().add(autogenerateButton);
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


		Button visualizations = getUIFactory().makeTextButton("", getErrorCheckedPrompt("Graphs"));
		visualizations.setOnAction(click-> {
			getView().goForwardFrom(this.getClass().getSimpleName()+"Graphs");
		});
		
		vbox.getChildren().addAll(newLevelHBox, hbox, demoButton, saveButton, visualizations, mainButton);
		//vbox.getChildren().add(songPrompted); TODO: change to mp3 selector and readd
		return vbox;

	}

}