package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdjustResourcesScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/SpecifyObjectScreen.css"; 

	protected AdjustResourcesScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET);
	}

	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException {
		VBox vb = new VBox(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText("Specify Starting Resources"));

		HBox startingHealthSlider = getUIFactory().setupPromptAndSlider("startingHealth", "Starting Health: ", 100);
		HBox startingCurrencySlider = getUIFactory().setupPromptAndSlider("startingCurrency", "Starting $: ", 999);

		vb.getChildren().add(startingHealthSlider);
		vb.getChildren().add(startingCurrencySlider);
		vb.getChildren().add(setupBackAndApplyButton());
		
		return new Scene(vb, 1500, 900); // TODO move to properties file
	}

}
