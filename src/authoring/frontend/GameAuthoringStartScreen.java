package authoring.frontend;

import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class to create the original screen users see when entering the Game Authoring environment. 
 * Dependent on the AuthoringView to create it correctly and on the PartsFactory to make UI
 * element
 * @author Sarahbland
 *
 *
 */
public class GameAuthoringStartScreen extends Screen {
	public static final String DEFAULT_OWN_CSS = "styling/GameAuthoringStartScreen.css";
	private Button myEditButton;

	protected GameAuthoringStartScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_CSS);
	}
	@Override
	protected Scene makeScreenWithoutStyling() {
		Text startHeading = new Text();
		VBox vbox = new VBox();
		try { //TODO: fix languages/error catching
			startHeading = getUIFactory().makeScreenTitleText(getPropertiesReader().findVal("prompts/EnglishPrompts.properties", "StartScreenHeader"));
		}
		catch(MissingPropertiesException e) {
			System.out.println("not finding file");
			e.printStackTrace(); //TODO: temporary until errors fixed
		}
		ArrayList<String> dummyGameNames = new ArrayList<>();
		String prompt = new String();
		try { //TODO: fix languages/error catching
			prompt = getPropertiesReader().findVal("prompts/EnglishPrompts.properties", "GameEditSelector");
		}
		catch(MissingPropertiesException e) {
			System.out.println("not finding file");
			e.printStackTrace(); //TODO: temporary until errors fixed
		}
		dummyGameNames.add(prompt);
		dummyGameNames.add("Vanilla");
		dummyGameNames.add("Plants vs. Zombies");
		Button newGameButton = new Button();
		try {
			newGameButton = getUIFactory().makeTextButton("editbutton", getPropertiesReader().findVal("prompts/EnglishPrompts.properties", "NewGameButtonLabel"));
		}
		catch(MissingPropertiesException e) {
			System.out.println("not finding file");
			e.printStackTrace(); //TODO: temporary until errors fixed
		}
		try {
			myEditButton = getUIFactory().makeTextButton("editbutton", getPropertiesReader().findVal("prompts/EnglishPrompts.properties", "EditButtonLabel"));
		}
		catch(MissingPropertiesException e) {
			System.out.println("not finding file");
			e.printStackTrace(); //TODO: temporary until errors fixed
		}
		ComboBox<String> gameChooser = getUIFactory().makeTextDropdownButtonEnable("", dummyGameNames, e -> {
			myEditButton.setDisable(false);}, e -> {myEditButton.setDisable(true);}, prompt);
		myEditButton.setDisable(true);
		vbox.getChildren().add(startHeading);
		vbox.getChildren().add(gameChooser);
		vbox.getChildren().add(myEditButton);
		vbox.getChildren().add(newGameButton);
		
		return new Scene(vbox, 1500, 900);

	}
}
