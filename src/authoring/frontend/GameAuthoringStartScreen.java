package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class to create the original screen users see when entering the Game Authoring environment. 
 * Dependent on the AuthoringView to create it correctly and on the PartsFactory to make UI
 * elements.
 * @author Sarahbland
 *
 *
 */
public class GameAuthoringStartScreen extends Screen {
	public static final String DEFAULT_OWN_CSS = "styling/GameAuthoringStartScreen.css";
	private PropertiesReader myPropertiesReader;
	protected GameAuthoringStartScreen() {
		myPropertiesReader = new PropertiesReader();
		setStyleSheet(DEFAULT_OWN_CSS);
	}
	@Override
	protected Scene makeScreenWithoutStyling() {
		Text startHeading = new Text();
		VBox vbox = new VBox();
		try { //TODO: fix languages/error catching
			startHeading = getUIFactory().makeScreenTitleText(myPropertiesReader.findVal("prompts/EnglishPrompts.properties", "StartScreenHeader"));
		}
		catch(MissingPropertiesException e) {
			System.out.println("not finding file");
			e.printStackTrace(); //TODO: temporary until errors fixed
		}
		vbox.getChildren().add(startHeading);
		return new Scene(vbox, 1500, 900);

	}
}
