/**
 * @author susiechoi
 * Creates screen in which user can customize what aspect of the level to customize (tower, path, etc)
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
	protected Parent populateScreenWithFields() {
		VBox vb = new VBox(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeLevel")+" "+getView().getLevel()));

		Button towersButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeTower"));
		towersButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+ "CustomizeTower");});
		vb.getChildren().add(towersButton);

		Button enemiesButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeEnemy"));
		enemiesButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+ "CustomizeEnemy");});
		vb.getChildren().add(enemiesButton);
		
		Button pathButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizePath"));
		pathButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+ "CustomizePath");});
		vb.getChildren().add(pathButton);

		Button waveButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeWave"));
		waveButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+ "CustomizeWave");});
		vb.getChildren().add(waveButton);
		Button backButton = setupBackButton();
		vb.getChildren().add(backButton);

		return vb;
	}

	@Override
	protected void populateFieldsWithData() {
		// DO NOTHING 
	}

}
