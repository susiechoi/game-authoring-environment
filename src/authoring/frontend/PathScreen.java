package authoring.frontend;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public abstract class PathScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";

	private StackPane pathRoot;
	private GridPane pathGrid;
	private PathPanel panel;
	private CreatePathGrid grid;

	protected PathScreen(AuthoringView view) {	
		super(view);
		grid = new CreatePathGrid(view);
	}
	protected void setPathPanel(PathPanel panelnew) {
		panel = panelnew;
		pathRoot.getChildren().clear();
		pathRoot.getChildren().add(panel.getPanel());
		pathRoot.getChildren().add(pathGrid);
		StackPane.setAlignment(pathGrid, Pos.CENTER_LEFT);
		StackPane.setAlignment(panel.getPanel(), Pos.CENTER_RIGHT);
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
		Button pathSizePlusButton = panel.getPlusButton();
		pathSizePlusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() < 100) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() + 10);
				}
			}
		});

		Button pathSizeMinusButton = panel.getMinusButton();
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


		//		Button backgroundButton = (Button) panel.getBackgroundButton();
		//		backgroundButton.setOnAction(new EventHandler<ActionEvent>() {
		//			@Override
		//			public void handle(ActionEvent e) {
		//				FileChooser fileChooser = new FileChooser();
		//				fileChooser.setTitle("View Pictures");
		//				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
		//				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
		//				File file = fileChooser.showOpenDialog(new Stage());
		//				grid.setBackgroundmage(file);
		//			}
		//		});
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
