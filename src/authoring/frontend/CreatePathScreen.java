package authoring.frontend;

import java.awt.Point;
import java.io.File;
import java.util.List;
import authoring.frontend.exceptions.ObjectNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
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
				List<Point> startCoords = grid.getStartingPosition();
				if (startCoords.size() == 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Path Cutomization Error");
					alert.setContentText("Your path has no starting blocks");
					alert.showAndWait();
				}
				for (Point point: startCoords) {
					if (grid.checkPathConnected(grid.getCheckGrid(), (int) point.getY(), (int) point.getX())) {
						System.out.println("TRUE");
						try { //do this outside of for loop...have a boolean check?
						
							getView().makePath(grid.getGrid(), grid.getAbsoluteCoordinates(), grid.getGridImageCoordinates(), myBackgroundImage, grid.getPathSize(), grid.getColumnCount(), grid.getRowCount());
							getView().getObjectAttribute("Path", "", "myPathMap");
							getView().getObjectAttribute("Path", "", "myBackgroundImage");
							getView().getObjectAttribute("Path", "", "myPathSize");
							
							getView().goForwardFrom(me.getClass().getSimpleName()+"Apply");
					
						} catch (ObjectNotFoundException e1) {
							// TODO Auto-generated catch block
						}
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Path Cutomization Error");
						alert.setContentText("Your path is incomplete - Please make sure that any start and end positions are connected");
						alert.showAndWait();
					}
				}
			}
		});
		System.out.println("HERE: " +this.getClass().getSimpleName());
	}

	@Override
	protected Parent populateScreenWithFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void populateFieldsWithData() {
		// TODO Auto-generated method stub
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
			public void handle(DragEvent event) {
				if (event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.ANY);
				}
			}
		});

		trashImage.setOnDragDropped(new EventHandler <DragEvent>() {
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

		Button backgroundButton = (Button) myPathToolBar.getBackgroundButton();
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
			}
		});
	}
}
