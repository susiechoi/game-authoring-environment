package authoring.frontend;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import authoring.frontend.exceptions.ObjectNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreatePathScreen extends PathScreen {

	private CreatePathPanel myPathPanel;
	private CreatePathToolBar myPathToolBar;
	private String myBackgroundImage = "Images/generalbackground.jpg";
	private CreatePathGrid myGrid;
	private List<List<Point>> myCoords = new ArrayList<List<Point>>();
	private boolean gridCheck = false;
	private CreatePathScreen me;

	public CreatePathScreen(AuthoringView view) {
		super(view);
		myPathPanel = new CreatePathPanel(view);
		myPathToolBar = new CreatePathToolBar(view);
		me = this;
	}

	private void setGridApplied(CreatePathGrid grid) {
		myGrid = grid;
		myPathPanel.setApplyButtonAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			    	setSaved();
				List<Point> startCoords = grid.getStartingPosition();
				if (startCoords.size() == 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Path Cutomization Error");
					alert.setContentText("Your path has no starting blocks");
					alert.showAndWait();
				}
				for (Point point: startCoords) {
					gridCheck = false;
					grid.getAbsoluteCoordinates().clear();
					if (grid.checkPathConnected(grid.getCheckGrid(), (int) point.getY(), (int) point.getX()) == true) {
						gridCheck = true;
						List<Point> coords = new ArrayList<Point>(grid.getAbsoluteCoordinates());
						myCoords.add(coords);
					}
				}
				//TODO: PATHS ARE NOT GETTING CORRECTLY PASSED FOR EDITTING!!!
				if (gridCheck == true) {
					try {
						getView().makePath(grid.getGrid(), myCoords, grid.getGridImageCoordinates(grid.getCheckGrid()), myBackgroundImage, 
								myPathPanel.getPanelPathImage().getPathImage().getImage().getUrl(), myPathPanel.getPanelStartImage().getPathImage().getImage().getUrl(), 
								myPathPanel.getPanelEndImage().getPathImage().getImage().getUrl(), grid.getPathSize(), grid.getColumnCount(), grid.getRowCount());
						getView().getObjectAttribute("Path", "", "myPathMap");
						getView().getObjectAttribute("Path", "", "myBackgroundImage");
						getView().getObjectAttribute("Path", "", "myPathSize");
						getView().getObjectAttribute("Path", "", "myPathImage");
						getView().getObjectAttribute("Path", "", "myStartImage");
						getView().getObjectAttribute("Path", "", "myEndImage");
						getView().goForwardFrom(me.getClass().getSimpleName()+"Apply");
					} catch (ObjectNotFoundException e1) {
						// TODO Auto-generated catch block
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
		gridIn.setUpForPathCreation();
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

		Button backgroundButton = myPathToolBar.getBackgroundButton();
		backgroundButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("View Pictures");
				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
				File file = fileChooser.showOpenDialog(new Stage());
				myBackgroundImage = file.toURI().toString();
				getGrid().setBackgroundImage(myBackgroundImage);
			}
		});
		setImageOnButtonPressed(myPathToolBar.getPathImageButton(), myPathPanel.getPanelPathImage());
		setImageOnButtonPressed(myPathToolBar.getStartImageButton(), myPathPanel.getPanelStartImage());
		setImageOnButtonPressed(myPathToolBar.getEndImageButton(), myPathPanel.getPanelEndImage());
	}

	private void setImageOnButtonPressed(Button button, DraggableImage image) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				if (button.equals(myPathToolBar.getPathImageButton())) {
					myGrid.setPathImage(myPathPanel.getPanelPathImage().getPathImage());
					myPathPanel.getPanelPathImage().setPath();
				} else if (button.equals(myPathToolBar.getStartImageButton())) {
					myGrid.setStartImage(myPathPanel.getPanelStartImage().getPathImage());
					myPathPanel.getPanelPathImage().setStart();
				} else if (button.equals(myPathToolBar.getEndImageButton())) {
					myGrid.setEndImage(myPathPanel.getPanelEndImage().getPathImage());
					myPathPanel.getPanelPathImage().setEnd();
				}
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("View Pictures");
				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
				File file = fileChooser.showOpenDialog(new Stage());
				image.setNewImage(new Image(file.toURI().toString()));
//				changeGridImages(file.toURI().toString());
			}
		});
	}
	
	//TODO: CHANGE ALL IMAGES IN GRID ON IMAGE CHANGE!!
//	private void changeGridImages(String imageFilePath) {
//		for (int i = 0; i < myGrid.getColumnCount(); i++) {
//			for (int j = 0; j < myGrid.getRowCount(); j++) {
//				
//				if (myGrid.getNode(myGrid.getCheckGrid(), i, j) != null) {
//					DraggableImage image = (DraggableImage) myGrid.getNode(myGrid.getCheckGrid(), i, j);
//					myGrid.removeNode(myGrid.getGrid(), i, j);
//					Label checkLabel = (Label) myGrid.getNode(myGrid.getCheckGrid(), i, j);
//					if (imageFilePath == myPathPanel.getPanelStartImage().getPathImage().getImage().getUrl().toString()) {
//						System.out.println("HERE");
//						image.setNewImage(new Image(imageFilePath));
//						DraggableImage path = new DraggableImage(new Image(imageFilePath));
//						path.setDraggable(myGrid.getCheckGrid(), j, i);
//						myGrid.getGrid().add(path.getPathImage(), i, j);
//					}
//				}
//			}
//		}
//	}
}
