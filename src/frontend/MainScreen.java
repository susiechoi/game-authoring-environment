package frontend;

import authoring.AuthoringController;
import controller.PlayController;
import frontend.Screen;
import frontend.UIFactory;
import frontend.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainScreen extends Screen {
    //TODO re-factor style sheets to abstract
    private  final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
    private  final String DEFAULT_ENGINE_STYLESHEET = "styling/EngineFrontEnd.css";
    private final String DEFAULT_LANGUAGE = "English";

    private final UIFactory UIFACTORY;
    private final StageManager STAGE_MANAGER;

    public MainScreen(StageManager stageManager) {
	UIFACTORY = new UIFactory();
	STAGE_MANAGER = stageManager;
	//setStyleSheet(DEFAULT_OWN_CSS);
    }

    @Override
    //TODO all text should be read from language properties files
    public Parent makeScreenWithoutStyling() {
	VBox rootBox = new VBox();
	Label textInstructs = new Label();
	textInstructs.setWrapText(true);
	textInstructs.setText("Instructions");
	textInstructs.setAlignment(Pos.CENTER);
	textInstructs.setMaxWidth(Double.MAX_VALUE);

	Button newAuthorButt = UIFACTORY.makeTextButton(".button", "Author");
	newAuthorButt.setOnAction(click->{
	    new AuthoringController(STAGE_MANAGER,DEFAULT_LANGUAGE);
	});
	newAuthorButt.setOnMouseClicked((argo0) -> new AuthoringController(STAGE_MANAGER, DEFAULT_LANGUAGE));
	Button continueButt = UIFACTORY.makeTextButton(".button", "Play");

	Button newGameButt = UIFACTORY.makeTextButton(".button", "Game");
	newGameButt.setOnAction(click->{
	    new PlayController(STAGE_MANAGER,DEFAULT_LANGUAGE);
	});
	newGameButt.setOnMouseClicked((argo0) -> new PlayController(STAGE_MANAGER, DEFAULT_LANGUAGE));

	HBox leftCenter = new HBox(newGameButt);
	leftCenter.setAlignment(Pos.CENTER);
	leftCenter.setMaxWidth(Double.MAX_VALUE);
	HBox rightCenter = new HBox(continueButt);
	rightCenter.setAlignment(Pos.CENTER);
	rightCenter.setMaxWidth(Double.MAX_VALUE);

	HBox buttonBox = new HBox(leftCenter,rightCenter);
	buttonBox.setAlignment(Pos.CENTER);
	HBox.setHgrow(leftCenter, Priority.ALWAYS);
	HBox.setHgrow(rightCenter, Priority.ALWAYS);

	rootBox.getChildren().addAll(textInstructs, buttonBox);

	rootBox.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
	rootBox.getStylesheets().add(DEFAULT_ENGINE_STYLESHEET);
	return rootBox;
    }

    @Override
    protected View getView() {
	// TODO Auto-generated method stub
	return null;
    }
}
