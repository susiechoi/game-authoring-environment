/**
 * @author susiechoi
 */

package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CustomizeLevelScreen extends AdjustScreen {
	
	public static final String DEFAULT_STYLESHEET = "styling/GameAuthoringStartScreen.css";

	protected CustomizeLevelScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_STYLESHEET);
	}

	@Override
	public Parent makeScreenWithoutStyling() {	
		VBox vb = new VBox(); 
		
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeLevel")));
		
		Button towersButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeTower"));
		vb.getChildren().add(towersButton);
		
		Button pathButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizePath"));
		vb.getChildren().add(pathButton);
		
		Button enemiesButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeEnemy"));
		vb.getChildren().add(enemiesButton);
		
		Button resourcesButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeResources"));
		vb.getChildren().add(resourcesButton);
		
		Button settingsButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeSettings"));
		vb.getChildren().add(settingsButton);
		
		Button autogenerateButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("AutogenerateLevel"));
		vb.getChildren().add(autogenerateButton);

		vb.getChildren().add(getUIFactory().setupBackButton());
		
		return vb;
	}

}
