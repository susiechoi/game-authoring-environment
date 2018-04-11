package authoring.frontend;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public abstract class PathScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";

	private StackPane pathRoot;
	private GridPane pathGrid;
	private CreatePathPanel panel;
	private CreatePathToolBar toolBar;
	private CreatePathGrid grid;

	protected PathScreen(AuthoringView view) {	
		super(view);
		grid = new CreatePathGrid(view);
	}
	protected void setPathPanel(CreatePathPanel panelnew, CreatePathToolBar toolbar) {
		panel = panelnew;
		toolBar = toolbar;
		pathRoot.getChildren().clear();
		pathRoot.getChildren().add(panel.getPanel());
		pathRoot.getChildren().add(toolBar.getPanel());
		pathRoot.getChildren().add(pathGrid);

		StackPane.setAlignment(pathGrid, Pos.TOP_LEFT);
		StackPane.setAlignment(panel.getPanel(), Pos.CENTER_RIGHT);
		StackPane.setAlignment(toolBar.getPanel(), Pos.BOTTOM_LEFT);
	}
	protected PathPanel getPathPanel() {
		return panel;
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		setStyleSheet(DEFAULT_OWN_STYLESHEET);
		pathGrid = grid.makePathGrid();
		pathRoot = new StackPane();
		initializeGridSettings(grid);
		setGridUIComponents();
		return pathRoot; 	
	}

	public abstract void initializeGridSettings(CreatePathGrid grid);

	private void setGridUIComponents() {
		Button pathSizePlusButton = toolBar.getPlusButton();
		pathSizePlusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() < 100) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() + 10);
				}
			}
		});

		Button pathSizeMinusButton = toolBar.getMinusButton();
		pathSizeMinusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() > 30) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() - 10);

				}
			}
		});

		ImageView trashImage = panel.makeTrashImage();
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
					//					pathGrid.getChildren().remove(grid.getDraggableImage());
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

		Button backgroundButton = (Button) toolBar.getBackgroundButton();
		backgroundButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("View Pictures");
				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
				File file = fileChooser.showOpenDialog(new Stage());
				grid.setBackgroundImage(file.toURI().toString());
			}
		});
		
		setImageOnButtonPressed(toolBar.getPathImageButton(), panel.getPathImage());
		setImageOnButtonPressed(toolBar.getStartImageButton(), panel.getStartImage());
		setImageOnButtonPressed(toolBar.getEndImageButton(), panel.getEndImage());
	}
	
	private void setImageOnButtonPressed(Button button, DraggableImage image) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("View Pictures");
				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
				File file = fileChooser.showOpenDialog(new Stage());
				image.setNewImage(new Image(file.toURI().toString()));
			}
		});
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
}
