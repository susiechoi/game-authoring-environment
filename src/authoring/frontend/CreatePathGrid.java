package authoring.frontend;


import com.sun.tools.javac.util.List;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;


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
	private GridPane checkGrid;
	private Label startLabel;
	private Label endLabel;
	private Label pathLabel;
	private Label dummy;


	protected GridPane makePathGrid() {
		grid = new GridPane();

		checkGrid = new GridPane();
		checkGrid.setMaxSize(1000, 750);
		setGridConstraints(checkGrid, INITIAL_PATH_SIZE);

		grid.setMaxSize(1000, 750); //only goes to 750
		setGridConstraints(grid, INITIAL_PATH_SIZE);
		//		grid.setGridLinesVisible(true);

		model = new SelectionModel();
		new ShiftSelection(grid, model);

		grid.setStyle("-fx-background-image: url('file:images/plaingreen.png')"); 
		populateGrid();

		return grid;
	}

	private void populateGrid() {
		for (int x = 0 ; x < grid.getColumnCount(); x++) {
			for (int y = 0 ; y < grid.getRowCount(); y++) {
				StackPane cell = new StackPane();

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
							//							cell.getChildren().add(path.getPathImage());
							grid.add(path.getPathImage(), colIndex, rowIndex);

							if (imageCompare(path.getPathImage().getImage(), startImage.getImage()) == true) {
								System.out.println("here");
								startLabel = new Label("start");
								checkGrid.add(startLabel, colIndex, rowIndex);
								//								startCols.add(colIndex);
								//								startRows.add(rowIndex);
							} else if (imageCompare(path.getPathImage().getImage(), endImage.getImage()) == true) {
								endLabel = new Label("end");
								checkGrid.add(endLabel, colIndex, rowIndex);
							} else if (imageCompare(path.getPathImage().getImage(), pathImage.getImage()) == true) {
								pathLabel = new Label("path");
								checkGrid.add(pathLabel, colIndex, rowIndex);
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

	public void setGridConstraints(GridPane grid, double size) {

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
	
//		boolean done = false;
		
		System.out.println("row: " +row);
		System.out.println("col: " +col);
		System.out.println();

		if (getNode(checkGrid, col, row) == endLabel) {
			System.out.println("TRUE");
			return true;
		}
		if (col < 0 || col >= grid.getColumnCount() || row < 0 || row >= grid.getRowCount()) {
			return false;
		}
		if ((getNode(checkGrid, col, row) == null) || (getNode(checkGrid, col, row) == dummy)) {
			return false;
		}
		
		dummy = new Label("dummy");
		removeNode(checkGrid, col, row);
		checkGrid.add(dummy, col, row);
		
		if ((checkPathConnected(row, col + 1)) == true) {
	    		System.out.println("HERE");
	        return true;
	    }
		if ((checkPathConnected(row, col - 1)) == true) {
	        return true;
	    }
	    if ((checkPathConnected(row + 1 , col)) == true) {
	        return true;
	    }
	    if ((checkPathConnected(row - 1, col)) == true) {
	        return true;
	    }
	    
	    removeNode(checkGrid, col, row);
		checkGrid.add(pathLabel, col, row);
	      
	    return false;

//		if (!done && row < checkGrid.getRowCount() && getNode(checkGrid, col, row + 1) instanceof Label) {
//			done = checkPathConnected(row + 1, col);
//		} else if (!done && col < checkGrid.getColumnCount() && getNode(checkGrid, col + 1, row) instanceof Label) {
//			done = checkPathConnected(row, col + 1);
//		} else if (!done && row > 0 && getNode(checkGrid, col, row - 1) instanceof Label) {
//			done = checkPathConnected(row - 1, col);
//		} else if (!done && col > 0 && getNode(checkGrid, col - 1, row) instanceof Label) {
//			done = checkPathConnected(row, col - 1);
//		}

	}

	public Node getNode(GridPane gridPane, int col, int row) {
		Node result = null;
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
				result = node;
				break;
			}
		}
		return result;
	}
	

	public void removeNode(GridPane grid, int row, int col) {
		for(Node node : grid.getChildren()) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
				grid.getChildren().remove(node);
				break;
			}
		} 
	}


	public GridPane getGrid() {
		return grid;
	}

	public GridPane getCheckGrid() {
		return checkGrid;
	}

	private boolean imageCompare(Image image1, Image image2) {
		for (int i = 0; i < image1.getWidth(); i++){
			for (int j = 0; j < image1.getHeight(); j++){
				if (image1.getPixelReader().getArgb(i, j) != image2.getPixelReader().getArgb(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean getCoords(int row, int col) {
		//if there is a turn then return that coordinate
		//traverse, if block does not have neighbor in same direction of traversal then return that coordinate


		boolean done = false;

		if (col < 0 || col >= grid.getColumnCount() || row < 0 || row >= grid.getRowCount()) {
			done = false;
		}

		if (getNode(checkGrid, col, row) == endLabel) {
			done = true;
		}

		if (!done && row < checkGrid.getRowCount() && getNode(checkGrid, col, row + 1) instanceof Label) {
			done = checkPathConnected(row + 1, col);
		} else if (!done && col < checkGrid.getColumnCount() && getNode(checkGrid, col + 1, row) instanceof Label) {
			done = checkPathConnected(row, col + 1);
		} else if (!done && row > 0 && getNode(checkGrid, col, row - 1) instanceof Label) {
			done = checkPathConnected(row - 1, col);
		} else if (!done && col > 0 && getNode(checkGrid, col - 1, row) instanceof Label) {
			done = checkPathConnected(row, col - 1);
		}

		System.out.println(done);
		return done;
	}

}
