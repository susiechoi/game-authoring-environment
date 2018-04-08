package authoring.frontend;

import java.util.ArrayList;

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
		startHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("StartScreenHeader", getView().getLanguage()));
		ArrayList<String> dummyGameNames = new ArrayList<>();
		String prompt = new String();
		prompt = getErrorCheckedPrompt("GameEditSelector", getView().getLanguage());
		dummyGameNames.add(prompt);
		dummyGameNames.add("Vanilla");
		dummyGameNames.add("Plants vs. Zombies");
		Button newGameButton = getUIFactory().makeTextButton("editbutton", getErrorCheckedPrompt("NewGameButtonLabel", getView().getLanguage()));
		myEditButton = getUIFactory().makeTextButton("editbutton", getErrorCheckedPrompt("EditButtonLabel", getView().getLanguage()));
		ComboBox<String> gameChooser = getUIFactory().makeTextDropdownSelectAction("", dummyGameNames, e -> {
			myEditButton.setDisable(false);}, e -> {myEditButton.setDisable(true);}, prompt);
		myEditButton.setDisable(true);
		vbox.getChildren().add(startHeading);
		vbox.getChildren().add(gameChooser);
		vbox.getChildren().add(myEditButton);
		vbox.getChildren().add(newGameButton);
		
		return new Scene(vbox, 1500, 900);

	}
}
