package authoring.frontend;


import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;


/* delete button to get rid of path blocks (trash, select all and delete)
 * Right click to be able to get specialty paths
 * filechoosers for loading in new images, get background image from elsewhere
 * Back to main button, if apply has not been clicked then prompt (changes will not be saved)
 * 
 * Need a way to get style info for defaults
 * check for completed path before demo (percolation), cannot click apply until this is done (The apply button will 
 * reassign the path in the AuthoringModel.java class to be used in the game)
 * 
 * Auto-generate new levels, reading in for default parameters
 * auto-populate cells by mouse drag (right click?) or copy and paste
 * 
 */

public class CreatePathGrid {

	public static final int INITIAL_PATH_SIZE = 60;
	private double pathSize;
	private int colIndex;
	private int rowIndex;
	private GridPane grid;

	protected GridPane makePathGrid() {
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
							DraggableImage path = new DraggableImage(db.getImage());
							path.setDraggable();
							path.getPathImage().fitWidthProperty().bind(cell.widthProperty()); 
							path.getPathImage().fitHeightProperty().bind(cell.heightProperty()); 
							grid.add(path.getPathImage(), colIndex, rowIndex);
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
