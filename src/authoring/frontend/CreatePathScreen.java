package authoring.frontend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import authoring.frontend.exceptions.ObjectNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import jdk.internal.jline.internal.Log;

public class CreatePathScreen extends PathScreen {

    private CreatePathPanel myPathPanel;
    private CreatePathToolBar myPathToolBar;
    private String myBackgroundImage = "file:Images/generalbackground.jpg";
    private CreatePathGrid myGrid;
    private List<List<Point>> myCoords = new ArrayList<List<Point>>();
    private boolean gridCheck = false;
    private CreatePathScreen me;
    private HBox backgroundHBox;
    private HBox pathHBox;
    private HBox startHBox;
    private HBox endHBox;

    public CreatePathScreen(AuthoringView view) {
	super(view);

    }
    @Override
    public void makePanels() {
	myPathPanel = new CreatePathPanel(getView());
	myPathToolBar = new CreatePathToolBar(getView());
	me = this;
    }

    private void setGridApplied(CreatePathGrid grid) {
	myGrid = grid;
	myPathPanel.setApplyButtonAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent e) {
		setSaved();
		List<Point> startCoords = grid.getStartingPosition(grid.getCheckGrid());
		if (startCoords.size() == 0) {
		    Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Path Cutomization Error");
		    alert.setContentText("Your path has no starting blocks");
		    alert.showAndWait();
		}
		for (Point point: startCoords) {
		    gridCheck = false;
		    grid.getAbsoluteCoordinates().clear();
		    if (grid.checkPathConnected(grid.copyGrid(grid.getCheckGrid()), (int) point.getY(), (int) point.getX()) == true) {
			gridCheck = true;
			List<Point> coords = new ArrayList<Point>(grid.getAbsoluteCoordinates());
			myCoords.add(coords);

		    } else {
			gridCheck = false;
		    }
		}

		if (gridCheck == true) {
		    try {
			String startImage = myPathPanel.getPanelStartImage().getPathImage().getImage().getUrl();
			String pathImage = myPathPanel.getPanelPathImage().getPathImage().getImage().getUrl();
			String endImage = myPathPanel.getPanelEndImage().getPathImage().getImage().getUrl();
			grid.getStartingPosition(grid.getCheckGrid());

			getView().makePath(grid.getGrid(), myCoords, grid.getGridImageCoordinates(grid.getCheckGrid(), startImage, pathImage, endImage), myBackgroundImage, 
				pathImage, startImage, endImage, grid.getPathSize(), grid.getGridWidth(), grid.getGridHeight());
			getView().getObjectAttribute("Path", "", "myPathMap");
			getView().getObjectAttribute("Path", "", "myBackgroundImage");
			getView().getObjectAttribute("Path", "", "myPathSize");
			getView().getObjectAttribute("Path", "", "myPathImage");
			getView().getObjectAttribute("Path", "", "myStartImage");
			getView().getObjectAttribute("Path", "", "myEndImage");
			getView().goForwardFrom(me.getClass().getSimpleName()+"Apply");
		    } catch (ObjectNotFoundException e1) {
//			Log.debug(e1);
			getView().loadErrorScreen("NoObject");
		    }
		} else {
		    Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Path Customization Error");
		    alert.setContentText("Your path is incomplete - Please make sure that any start and end positions are connected");
		    alert.showAndWait();
		}
	    }
	});
    }


    @Override
    public void initializeGridSettings(CreatePathGrid gridIn) {
	setPathPanel(myPathPanel, myPathToolBar);
	setGridApplied(gridIn);
    }

    @Override
    public void setSpecificUIComponents() {
	setGridUIComponents(myPathPanel, myPathToolBar);
	ImageView trashImage = myPathPanel.makeTrashImage();
	trashImage.setOnDragOver(new EventHandler <DragEvent>() {
	    @Override
	    public void handle(DragEvent event) {
		if (event.getDragboard().hasImage()) {
		    event.acceptTransferModes(TransferMode.ANY);
		}
	    }
	});

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

	backgroundHBox = myPathToolBar.getBackgroundHBox();
	ComboBox<String> backgroundComboBox =  (ComboBox<String>) ((VBox) backgroundHBox.getChildren().get(0)).getChildren().get(0);
	backgroundComboBox.addEventHandler(ActionEvent.ACTION, e -> {
	    myBackgroundImage = "file:images/"+backgroundComboBox.getValue()+".png";
	    getGrid().setBackgroundImage(myBackgroundImage);
	});

	pathHBox = myPathToolBar.getPathHBox();
	ComboBox<String> pathComboBox = (ComboBox<String>) ((VBox) pathHBox.getChildren().get(0)).getChildren().get(0);
	pathComboBox.addEventHandler(ActionEvent.ACTION, e -> {
	    String pathImageFilePath = "file:images/"+pathComboBox.getValue()+".png";
	    myGrid.setPathImage(myPathPanel.getPanelPathImage().getPathImage());
	    //	    myPathPanel.getPanelPathImage().setPath();
	    myPathPanel.getPanelPathImage().setNewImage(new Image(pathImageFilePath));
//	    changeGridImages(pathImageFilePath);
	    //	    for (int i = 0; i < getGrid().getGrid().getChildren().size(); i++) {
	    //		if (getGrid().getGrid().getChildren().get(i) instanceof ImageView) {
	    //		    getGrid().getGrid().getChildren().remove(i);
	    //		}
	    //	    }
	    //	   getGrid().getCheckGrid().getChildren().clear();
	});


	startHBox = myPathToolBar.getStartHBox();
	ComboBox<String> startComboBox = (ComboBox<String>) ((VBox) startHBox.getChildren().get(0)).getChildren().get(0);
	startComboBox.addEventHandler(ActionEvent.ACTION, e -> {
	    String startImageFilePath = "file:images/"+startComboBox.getValue()+".png";
	    myGrid.setStartImage(myPathPanel.getPanelPathImage().getPathImage());
	    //	    myPathPanel.getPanelPathImage().setStart();
	    myPathPanel.getPanelStartImage().setNewImage(new Image(startImageFilePath));
	});

	endHBox = myPathToolBar.getEndHBox();
	ComboBox<String> endComboBox = (ComboBox<String>) ((VBox) endHBox.getChildren().get(0)).getChildren().get(0);
	endComboBox.addEventHandler(ActionEvent.ACTION, e -> {
	    String endImageFilePath = "file:images/"+endComboBox.getValue()+".png";
	    myGrid.setEndImage(myPathPanel.getPanelPathImage().getPathImage());
	    //	    myPathPanel.getPanelPathImage().setEnd();
	    myPathPanel.getPanelEndImage().setNewImage(new Image(endImageFilePath));
	});
    }
    
    //Might need to just remove path blocks when changing images
//    private void changeGridImages(String imageFilePath) {
//	for (int i = 0; i < myGrid.getColumnCount(); i++) {
//		if (getGrid().getNode(getGrid().getCheckGrid(), i, j) != null && ((Label) getGrid().getNode(getGrid().getCheckGrid(), i, j)).getText() == "path") {
		    //iterate through children of grid, change those ImageViews?
//		}
//	    }	
//	}
}
