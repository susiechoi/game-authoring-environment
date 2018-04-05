package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class CustomizeLevelScreen extends Screen {

	protected CustomizeLevelScreen(AuthoringView view) {
		super(view);
	}

	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException {	
		VBox vb = new VBox(); 
		
		Button towersButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("Towers", "English"));
		vb.getChildren().add(e)

		return new Scene(vb, 1500, 900);
	}

}
