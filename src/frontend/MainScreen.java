package frontend;

import authoring.AuthoringController;
import authoring.AuthoringModel;
import authoring.frontend.exceptions.MissingPropertiesException;
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
import javafx.scene.text.Text;

public class MainScreen extends Screen {
    //TODO re-factor style sheets to abstract
    public static final String DEFAULT_CSS = "styling/GameAuthoringStartScreen.css";
   // private  final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
//    private  final String DEFAULT_ENGINE_STYLESHEET = "styling/EngineFrontEnd.css";
    private final String DEFAULT_LANGUAGE = "English";

    private final UIFactory UIFACTORY;
    private final StageManager STAGE_MANAGER;

    public MainScreen(StageManager stageManager) {
	UIFACTORY = new UIFactory();
	STAGE_MANAGER = stageManager;
	setStyleSheet(DEFAULT_CSS);
    }

    @Override
    //TODO all text should be read from language properties files
    public Parent makeScreenWithoutStyling() {
	VBox rootBox = new VBox();
	Text title = getUIFactory().makeScreenTitleText("Welcome");
	Button newAuthorButt = getUIFactory().makeTextButton("editbutton", "Author A Game");
	newAuthorButt.setOnAction(click->{
	    new AuthoringController(STAGE_MANAGER,DEFAULT_LANGUAGE);
	});
	newAuthorButt.setOnMouseClicked((argo0) -> new AuthoringController(STAGE_MANAGER, DEFAULT_LANGUAGE));

	Button newGameButt = getUIFactory().makeTextButton("editbutton", "Load/Play A Game");
	newGameButt.setOnAction(click->{
	    try {
		new PlayController(STAGE_MANAGER, DEFAULT_LANGUAGE, new AuthoringModel());
	    } catch (MissingPropertiesException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	});

//	HBox leftCenter = new HBox(newAuthorButt);
//	leftCenter.setAlignment(Pos.CENTER);
//	leftCenter.setMaxWidth(Double.MAX_VALUE);
//	HBox rightCenter = new HBox(newGameButt);
//	rightCenter.setAlignment(Pos.CENTER);
//	rightCenter.setMaxWidth(Double.MAX_VALUE);
//
//	HBox buttonBox = new HBox(leftCenter,rightCenter);
//	buttonBox.setAlignment(Pos.CENTER);
//	HBox.setHgrow(leftCenter, Priority.ALWAYS);
//	HBox.setHgrow(rightCenter, Priority.ALWAYS);

	rootBox.getChildren().addAll(title, newAuthorButt, newGameButt);

	rootBox.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
//	rootBox.getStylesheets().add(DEFAULT_ENGINE_STYLESHEET);
	return rootBox;
    }

    @Override
    protected View getView() {
	// TODO Auto-generated method stub
	return null;
    }
}
