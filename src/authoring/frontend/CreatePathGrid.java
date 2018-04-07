package authoring.frontend;


import com.sun.tools.javac.util.List;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private SelectionModel model;
	private ImageView startImage = new ImageView(new Image("file:images/start.png"));
	private ImageView endImage = new ImageView(new Image("file:images/end.png"));
	private ImageView pathImage = new ImageView(new Image("file:images/cobblestone.png"));
	private List<Integer> startRows;
	private List<Integer> startCols;
	

	protected GridPane makePathGrid() {
		grid = new GridPane();
		grid.setMaxSize(1000, 750); //only goes to 750
		setGridConstraints(INITIAL_PATH_SIZE);
		grid.setGridLinesVisible(true);
		
		model = new SelectionModel();
		new ShiftSelection(grid, model);
		
		grid.setStyle("-fx-background-image: url('file:images/plaingreen.png')"); 
		populateGrid();
		
		System.out.println(grid.getChildren());
		
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
							path.setDraggable(model);
							path.getPathImage().fitWidthProperty().bind(cell.widthProperty()); 
							path.getPathImage().fitHeightProperty().bind(cell.heightProperty()); 
							grid.add(path.getPathImage(), colIndex, rowIndex);
							if (path.getPathImage().equals(startImage)) {
								startCols.add(colIndex);
								startRows.add(rowIndex);
							}
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
	
	public boolean checkPathConnected(int row, int col) {
		
		boolean done = false;
		
		if (col < 0 || col >= grid.getColumnCount() || row < 0 || row >= grid.getRowCount()) {
			done = false;
		}
		if (getNode(grid, col, row).equals(endImage)) {
			done = true;
		}
		
		if (getNode(grid, row - 1, col).equals(pathImage))
			checkPathConnected(row - 1, col);
		if (getNode(grid, row, col - 1).equals(pathImage))
			checkPathConnected(row, col - 1);
		if (getNode(grid, row, col + 1).equals(pathImage))
			checkPathConnected(row, col + 1);
		if (getNode(grid, row + 1, col).equals(pathImage))
			checkPathConnected(row + 1, col);
		
		System.out.println(done);
		return done;
	}
	
	private Node getNode(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	    		System.out.println(node);
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            return node;
	        }
	    }
	    return null;
	}
	
	public GridPane getGrid() {
	    return grid;
	}
}
