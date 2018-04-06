package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdjustResourcesScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/SpecifyObjectScreen.css"; 

	protected AdjustResourcesScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET);
	}

	@Override
	public Scene makeScreenWithoutStyling() throws MissingPropertiesException {
		VBox vb = new VBox(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText("Specify Starting Resources"));

		Slider startingHealthSlider = getUIFactory().setupSlider("startingHealth", 100);
		HBox startingHealth = getUIFactory().addPromptAndSetupHBox("startingHealth", startingHealthSlider, getErrorCheckedPrompt("StartingHealth", getView().getLanguage()));
		Slider startingCurrencySlider = getUIFactory().setupSlider("startingCurrency", 999);
		HBox startingCurrency = getUIFactory().addPromptAndSetupHBox("startingCurrency", startingCurrencySlider, getErrorCheckedPrompt("StartingCurrency", getView().getLanguage()));

		vb.getChildren().add(startingHealth);
		vb.getChildren().add(startingCurrency);
		vb.getChildren().add(setupBackAndApplyButton());
		
		return new Scene(vb, 1500, 900); // TODO move to properties file
	}

}
