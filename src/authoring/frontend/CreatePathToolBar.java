package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;


public class CreatePathToolBar extends PathToolBar {

   // public static final int PANEL_PATH_SIZE = 90;
    public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";



    private Button startImageChooser;
    private Button endImageChooser;
    private Button pathImageChooser;
    private Button backgroundButton;
    private List<Button> myImageButtons;

    public CreatePathToolBar(AuthoringView view) {
	super(view);
    }


    public void makePanel() {
	myImageButtons = new ArrayList<>();
	backgroundButton = getUIFactory().makeTextButton("", "Choose Background Image");

	pathImageChooser = getUIFactory().makeTextButton("", "Choose Path Image");
	myImageButtons.add(pathImageChooser);

	startImageChooser = getUIFactory().makeTextButton("", "Choose Start Image");
	myImageButtons.add(startImageChooser);

	endImageChooser = getUIFactory().makeTextButton("", "Choose End Image");
	myImageButtons.add(endImageChooser);

	//HBox pathSizeButtons = makeSizingButtons();

	getToolBar().getChildren().addAll(backgroundButton, startImageChooser, pathImageChooser, endImageChooser);
    }



//    protected HBox makeSizingButtons() {
//	HBox hb = new HBox();
//	Image plusImg = new Image(DEFAULT_PLUS_IMAGE, 60, 40, true, false);
//	myPlusButton = getUIFactory().makeImageButton("", plusImg);
//	myPlusButton.getStyleClass().add("button-pathsize");
//	mySizingButtons.add(myPlusButton);
//
//	Image minusImg = new Image(DEFAULT_MINUS_IMAGE, 60, 40, true, false);
//	myMinusButton = getUIFactory().makeImageButton("", minusImg);
//	myMinusButton.getStyleClass().add("button-pathsize");
//	mySizingButtons.add(myMinusButton);
//	hb.getChildren().addAll(mySizingButtons);
//	return hb;
//    }

    public Button getPathImageButton() {
	return pathImageChooser;
    }

    public Button getStartImageButton() {
	return startImageChooser;
    }

    public Button getEndImageButton() {
	return endImageChooser;
    }

    public Button getBackgroundButton() {
	return backgroundButton;
    }

//    public HBox getSizeButtons() {
//	return pathSizeButtons;
//    }

    @Override
    public Parent makeScreenWithoutStyling() {
	//TODO Auto-generated method stub
	return null;
    }

//    @Override
//    protected void setApplyButtonAction(EventHandler<ActionEvent> e) {
//	// TODO Auto-generated method stub
//	applyButton.setOnAction(event -> e.handle(event));
//    }

}

