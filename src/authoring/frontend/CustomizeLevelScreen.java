package authoring.frontend;

import javafx.scene.control.Button; 
import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class CustomizeLevelScreen extends Screen {
	
	public static final String DEFAULT_STYLESHEET = "styling/GameAuthoringStartScreen.css";

	protected CustomizeLevelScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_STYLESHEET);
	}

	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException {	
		VBox vb = new VBox(); 
		
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeLevel", "English")));
		
		Button towersButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeTower", "English"));
		vb.getChildren().add(towersButton);
		
		Button pathButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizePath", "English"));
		vb.getChildren().add(pathButton);
		
		Button enemiesButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeEnemy", "English"));
		vb.getChildren().add(enemiesButton);
		
		Button resourcesButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeResources", "English"));
		vb.getChildren().add(resourcesButton);
		
		Button settingsButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeSettings", "English"));
		vb.getChildren().add(settingsButton);
		
		Button autogenerateButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("AutogenerateLevel", "English"));
		vb.getChildren().add(autogenerateButton);

		vb.getChildren().add(getUIFactory().setupBackButton());
		
		return new Scene(vb, 1500, 900);
	}

}
