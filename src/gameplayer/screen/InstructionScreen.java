
package gameplayer.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import frontend.PromptReader;
import frontend.Screen;
import frontend.UIFactory;
import frontend.View;
import gameplayer.ScreenManager;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InstructionScreen extends Screen {
	public static final String DEFAULT_SHARED_STYLESHEET = "styling/GameAuthoringStartScreen.css";
	private final ScreenManager SCREEN_MANAGER;
	private final PromptReader PROMPTS;
	private final UIFactory UIFACTORY;
	private ComboBox<String> allGames;
	private Button continueButt;
	private Button backButton;

	public InstructionScreen(ScreenManager screenManager, PromptReader promptReader) {
		SCREEN_MANAGER = screenManager;
		UIFACTORY = new UIFactory();
		PROMPTS = promptReader;
		setStyleSheet(DEFAULT_SHARED_STYLESHEET);
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		VBox rootBox = new VBox();
		Text title = getUIFactory().makeScreenTitleText("Select a New Game to Play");
		// TODO: Make a load game button
		//	Button newGameButt = UIFACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("NewGameButton"));
		//	newGameButt.setOnMouseClicked((arg0) -> SCREEN_MANAGER.loadGameScreenNew());

		allGames = UIFACTORY.makeTextDropdown("", gameOptions());
		allGames.setOnAction(click ->{ 
			continueButt.setDisable(false);
		});

		continueButt = UIFACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("ContinueButton"));
		continueButt.setDisable(true);
		continueButt.setOnMouseClicked((arg0) -> SCREEN_MANAGER.loadGameScreenNew(allGames.getValue()));
		//	continueButt.setOnMouseClicked((arg0) -> SCREEN_MANAGER.loadGameScreenContinuation());
		Button backButton = UIFACTORY.setupBackButton(e->{
			SCREEN_MANAGER.toMain();
		}, PROMPTS.resourceDisplayText("Cancel")); 

		VBox center = new VBox(title, allGames, continueButt);
		center.setAlignment(Pos.CENTER);
		center.setMaxWidth(Double.MAX_VALUE);
		VBox.setVgrow(center, Priority.ALWAYS);

		rootBox.getChildren().addAll(center);
		return rootBox;
	}

	/**
	 * Returns all models in savedModels directory for display on the instruction screen
	 * 
	 * @return List<String>: containing all game options
	 */
	private List<String> gameOptions(){
		File[] files = new File("SavedModels/").listFiles();
		List<String> ret = new ArrayList<String>();
		for(File file : files){
			ret.add(file.getName().substring(0, file.getName().indexOf(".")));
		}
		return ret;
	}


	@Override
	protected View getView() {
		// TODO Auto-generated method stub
		return null;
	}
}

