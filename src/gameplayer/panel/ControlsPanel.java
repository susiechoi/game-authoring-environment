
package gameplayer.panel;

import java.util.Map;

import com.sun.javafx.tools.packager.Log;

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

/**
 * @Author Alexi Kontos & Andrew Arnold
 */



public class ControlsPanel extends Panel{

	private final PromptReader PROMPTS;
	private final GameScreen GAME_SCREEN;
	private Map<String,String> GAMEPLAYER_PROPERTIES;
	private final UIFactory UIFACTORY;
	private final PropertiesReader PROP_READ;

	public ControlsPanel(GameScreen gameScreen, PromptReader promptReader) {
		GAME_SCREEN = gameScreen;
		GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();
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

		String CONTROL_BUTTON_FILEPATH = GAMEPLAYER_PROPERTIES.get("ControlButtonFilepath");
		Integer DEFAULT_CONTROL_BUTTON_SIZE = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("ControlButtonSize"));
		try {

			Map<String,Image> controlsMap = PROP_READ.keyToImageMap(CONTROL_BUTTON_FILEPATH, DEFAULT_CONTROL_BUTTON_SIZE, DEFAULT_CONTROL_BUTTON_SIZE);
			int controlsSplit = controlsMap.keySet().size()/2;
			int count = 0;
			for(Map.Entry<String,Image> control: controlsMap.entrySet()) {
				Button controlButton = UIFACTORY.makeImageButton(GAMEPLAYER_PROPERTIES.get("ControlButtonID"), controlsMap.get(control.getKey()));
				controlButton.setOnMouseClicked(arg0  -> {
					try {
						GAME_SCREEN.controlTriggered(control.getKey());
					} catch (MissingPropertiesException e) {
						// TODO Auto-generated catch block
					    	Log.debug(e);
						e.printStackTrace();
					}
				});
				controlButton.setTooltip(new Tooltip(PROMPTS.resourceDisplayText(control.getKey()+GAMEPLAYER_PROPERTIES.get("Tooltip"))));
				if(count <controlsSplit) {
					topControls.getChildren().add(controlButton);
				}
				else {
					botControls.getChildren().add(controlButton);
				}
				count++;
			}
		} catch (MissingPropertiesException e) {
		    Log.debug(e);
			//something went wrong and we don't have the control images
			//TODO something reasonable here
			//probably have default images that aren't the ones specified by authoring
		}
	}
}
