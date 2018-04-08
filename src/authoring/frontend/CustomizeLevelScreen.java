/**
 * @author susiechoi
 */

package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CustomizeLevelScreen extends AdjustScreen {
	
	protected CustomizeLevelScreen(AuthoringView view) {
		super(view);
	}

	@Override
	public Parent makeScreenWithoutStyling() {	
		VBox vb = new VBox(); 
		
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeLevel")+" "+getView().getLevel()));
		
		Button towersButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeTower"));
		towersButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+ "CustomizeTower");});
		vb.getChildren().add(towersButton);
		
		Button pathButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizePath"));
		pathButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+ "CustomizePath");});
		vb.getChildren().add(pathButton);
		
		Button enemiesButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeEnemy"));
		enemiesButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+ "CustomizeEnemy");});
		vb.getChildren().add(enemiesButton);
		
		Button settingsButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeSettings"));
		settingsButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+ "CustomizeSettings");});
		vb.getChildren().add(settingsButton);
		
		Button autogenerateButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("AutogenerateLevel"));
		vb.getChildren().add(autogenerateButton);

		Button backButton = setupBackButton();
		vb.getChildren().add(backButton);
		
		return vb;
	}

}
