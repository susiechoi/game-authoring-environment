/**
 * @author susiechoi
 * Creates screen in which user can customize the starting resources of the player
 */

package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdjustResourcesScreen extends AdjustScreen {

	protected AdjustResourcesScreen(AuthoringView view) {
		super(view);
	}

	/**
	 * Creates features (specifically, sliders) that users can manipulate to change starting reosurces of player
	 */
	@Override
	public Parent makeScreenWithoutStyling(){
		VBox vb = new VBox(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SpecifyStartingResources")));

		Slider startingHealthSlider = getUIFactory().setupSlider("startingHealth", 100);
		HBox startingHealth = getUIFactory().setupSliderWithValue("startingHealth", startingHealthSlider, getErrorCheckedPrompt("StartingHealth"));
		Slider startingCurrencySlider = getUIFactory().setupSlider("startingCurrency", 999);
		HBox startingCurrency = getUIFactory().setupSliderWithValue("startingCurrency", startingCurrencySlider, getErrorCheckedPrompt("StartingCurrency"));

		Button backButton = setupBackButton();
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			getView().makeResources(startingHealthSlider.getValue(), startingCurrencySlider.getValue());
		});
		HBox backAndApplyButton = setupBackAndApplyButton(backButton, applyButton);
		
		vb.getChildren().add(startingHealth);
		vb.getChildren().add(startingCurrency);
		vb.getChildren().add(backAndApplyButton);
		
		return vb;
	}

}
