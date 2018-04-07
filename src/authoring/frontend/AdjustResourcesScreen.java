/**
 * @author susiechoi
 */

package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdjustResourcesScreen extends AdjustScreen {

	protected AdjustResourcesScreen(AuthoringView view) {
		super(view);
	}

	@Override
	public Parent makeScreenWithoutStyling(){
		VBox vb = new VBox(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText("Specify Starting Resources"));

		Slider startingHealthSlider = getUIFactory().setupSlider("startingHealth", 100);
		HBox startingHealth = getUIFactory().addPromptAndSetupHBox("startingHealth", startingHealthSlider, getErrorCheckedPrompt("StartingHealth"));
		Slider startingCurrencySlider = getUIFactory().setupSlider("startingCurrency", 999);
		HBox startingCurrency = getUIFactory().addPromptAndSetupHBox("startingCurrency", startingCurrencySlider, getErrorCheckedPrompt("StartingCurrency"));

		vb.getChildren().add(startingHealth);
		vb.getChildren().add(startingCurrency);
		vb.getChildren().add(setupBackAndApplyButton());
		
		return vb;
	}

}
