package authoring.frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Class to create the righthand panel for the path authoring screen.
 * @author Erik Riis
 *
 */
public class CreatePathPanel extends PathPanel {

    public static final int PANEL_PATH_SIZE = 90;
    public static final int PANEL_WIDTH = 330;
    public static final int PANEL_HEIGHT = 900;
    public static final String DEFAULT_PATH_IMAGE = "file:images/cobblestone.png";
    public static final String DEFAULT_START_IMAGE = "file:images/brick.png";
    public static final String DEFAULT_END_IMAGE = "file:images/darkstone.png";
    private VBox pathPanel;
    private DraggableImage pathImage;
    private DraggableImage startImage;
    private DraggableImage endImage;
    private Button applyButton;
    private Button backButton;
    private ImageView trashImage;
    private boolean isTransparent;

    public CreatePathPanel(AuthoringView view) {
	super(view);
	makePanel();
    }

    @Override
    protected Button getApplyButton() {
	return applyButton;
    }

    @Override
    protected void makePanel() {

	pathPanel = new VBox();
	pathPanel.setMaxSize(PANEL_WIDTH, PANEL_HEIGHT);
	pathPanel.getStylesheets();

	Label panelTitle = new Label("Drag and Drop Paths");
	Label startLabel = new Label("Start:");
	Label pathLabel = new Label("Path:");
	Label endLabel = new Label("End:");

	HBox pathHBox = new HBox();

	pathImage = new DraggableImage(new Image(DEFAULT_PATH_IMAGE));
	pathImage.setCopyDraggable();
	pathImage.getPathImage().setId("path");

	startImage = new DraggableImage(new Image(DEFAULT_START_IMAGE));
	startImage.setCopyDraggable();
	startImage.getPathImage().setId("start");

	endImage = new DraggableImage(new Image(DEFAULT_END_IMAGE));
	endImage.setCopyDraggable();
	endImage.getPathImage().setId("end");

	applyButton = getUIFactory().makeTextButton("", "Apply");
	backButton = setupBackButton();

	trashImage = new ImageView(new Image("file:images/trash.png", 120, 120, true, false));

	ToggleGroup radioGroup = new ToggleGroup();
	ToggleButton transparentToggle = new ToggleButton("Make Path Transparent");
	transparentToggle.setToggleGroup(radioGroup);
	radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
	    public void changed(ObservableValue<? extends Toggle> ov,
		    Toggle toggle, Toggle new_toggle) {
		if (new_toggle == null) {
		    isTransparent = false;
		} else {
		    isTransparent = true;
		}
	    }
	});

	pathPanel.getChildren().addAll(panelTitle, startLabel, startImage.getPathImage(), pathLabel, pathImage.getPathImage(), endLabel, endImage.getPathImage(), trashImage, transparentToggle, applyButton, backButton);
    }

    protected ImageView makeTrashImage() {
	trashImage.setOnDragDropped(new EventHandler <DragEvent>() {
	    @Override
	    public void handle(DragEvent event) {
		event.acceptTransferModes(TransferMode.ANY);
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasImage()) {
		    success = true;
		}
		event.setDropCompleted(success);
		event.consume();
	    }
	});
	return trashImage;
    }

    protected boolean getTransparent() {
	return isTransparent;
    }

    @Override
    protected Node getPanel() {
	return pathPanel;
    }


    protected DraggableImage getPanelPathImage() {
	return pathImage;
    }

    protected DraggableImage getPanelStartImage() {
	return startImage;
    }

    protected DraggableImage getPanelEndImage() {
	return endImage;
    }

    /** 
     * TODO: this method is null - should be integrated
     *
     * @see frontend.Screen#makeScreenWithoutStyling()
     */
    @Override

    public Parent makeScreenWithoutStyling() {
	//TODO Auto-generated method stub
	return null;
    }

    @Override
    protected void setApplyButtonAction(EventHandler<ActionEvent> e) {
	applyButton.setOnAction(
		event -> {
		    setSaved();
		    e.handle(event);
		});
    }
}

