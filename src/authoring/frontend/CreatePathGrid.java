package authoring.frontend;


import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


//add start/end
//separate panel containing blocks
//move drag and drop functionality to separate class (or set up grid in one class, and one for path drag-and-drop)
//delete button to get rid of path blocks (trash, select all and delete)

public class CreatePathGrid {

	public static final int INITIAL_PATH_SIZE = 60;
	private double pathSize;
	private int colIndex;
	private int rowIndex;
	private GridPane grid;
	private PathImageComposite pathComposite;

	protected GridPane makePathGrid() {
		pathComposite = new PathImageComposite();
		grid = new GridPane();
		grid.setMaxSize(1000, 750); //only goes to 750
		setGridConstraints(INITIAL_PATH_SIZE);
		grid.setGridLinesVisible(true);

		grid.setStyle("-fx-background-image: url('file:images/plaingreen.png')"); 
		populateGrid();
		return grid;
	}

	private void populateGrid() {
		for (int x = 0 ; x < grid.getColumnCount(); x++) {
			for (int y = 0 ; y < grid.getRowCount(); y++) {
				Pane cell = new Pane();
//				GridPane.setConstraints(cell, x, y);
				
				final int col = x;
				final int row = y;

				cell.setOnDragOver(new EventHandler <DragEvent>() {
					public void handle(DragEvent event) {
						if (event.getDragboard().hasImage()) {
							event.acceptTransferModes(TransferMode.ANY);
						}
						colIndex = col;
						rowIndex = row;
						event.consume();
					}
				});

				cell.setOnDragDropped(new EventHandler <DragEvent>() {
					public void handle(DragEvent event) {
						event.acceptTransferModes(TransferMode.ANY);
						Dragboard db = event.getDragboard();
						boolean success = false;
						if (db.hasImage()) {
							ImageView path = new ImageView(db.getImage());
							path.fitWidthProperty().bind(cell.widthProperty()); 
							path.fitHeightProperty().bind(cell.heightProperty()); 
							grid.add(path, colIndex, rowIndex);
							pathComposite.addPathImage(path);
							success = true;
						}
						event.setDropCompleted(success);
						event.consume();
					}
				});
				grid.add(cell, x, y);
			}
		}
	}

	public PathImageComposite getPathComposite() {
		return pathComposite;
	}
	
	public double getPathSize() {
		return pathSize;
	}

	public void setGridConstraints(double size) {

		grid.getColumnConstraints().clear();
		grid.getRowConstraints().clear();

		pathSize = size;
		for (int i = 0; i < 1000/pathSize; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPrefWidth(pathSize);
			grid.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < 750/pathSize; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPrefHeight(pathSize);
			grid.getRowConstraints().add(rowConst);         
		}
		populateGrid();
	}
}
