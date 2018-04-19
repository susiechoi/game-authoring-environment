
package gameplayer.panel;

import java.util.Map;

import frontend.PropertiesReader;
import frontend.UIFactory;
import authoring.frontend.exceptions.MissingPropertiesException;
import gameplayer.screen.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Tooltip;
import frontend.PromptReader;


public class ControlsPanel extends Panel{

	//TODO read this from settings or properties file, even better would be autoscaling to fit space
	private final int DEFAULT_CONTROL_BUTTON_SIZE = 35;

	private final PromptReader PROMPTS;
	private final GameScreen GAME_SCREEN;
	private final UIFactory UIFACTORY;
	private final String CONTROL_BUTTON_FILEPATH = "images/ControlPanelImages/ControlButtonNames.properties";
	private final PropertiesReader PROP_READ;

	public ControlsPanel(GameScreen gameScreen, PromptReader promptReader) {
		GAME_SCREEN = gameScreen;
		UIFACTORY = new UIFactory();
		PROP_READ = new PropertiesReader();
		PROMPTS = promptReader;
	}


	@Override
	public void makePanel() {
		HBox topControls = new HBox();
		topControls.setAlignment(Pos.CENTER);
		HBox botControls = new HBox();
		botControls.setAlignment(Pos.CENTER);

		makeControlButtons(topControls, botControls);
		VBox panelRoot = new VBox(topControls, botControls);
		PANEL = panelRoot;
	}


	private void makeControlButtons(HBox topControls, HBox botControls) {
		try {
			Map<String,Image> controlsMap = PROP_READ.keyToImageMap(CONTROL_BUTTON_FILEPATH, DEFAULT_CONTROL_BUTTON_SIZE, DEFAULT_CONTROL_BUTTON_SIZE);
			int controlsSplit = controlsMap.keySet().size()/2;
			int count = 0;
			for(String control : controlsMap.keySet()) {
				Button controlButton = UIFACTORY.makeImageButton("controlButton", controlsMap.get(control));
				controlButton.setOnMouseClicked((arg0) -> GAME_SCREEN.controlTriggered(control));
				controlButton.setTooltip(new Tooltip(PROMPTS.resourceDisplayText(control+"Tooltip")));
				if(count <controlsSplit)
					topControls.getChildren().add(controlButton);
				else
					botControls.getChildren().add(controlButton);
				count++;
			}
		} catch (MissingPropertiesException e) {
			//something went wrong and we don't have the control images
			//TODO something reasonable here
			//probably have default images that aren't the ones specified by authoring
		}
	}
}
