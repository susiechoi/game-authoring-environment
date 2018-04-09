package authoring.frontend;


import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
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
    private ArrayList<Integer> startRows;
    private ArrayList<Integer> startCols;
    private GridPane checkGrid;
    private Label startLabel;
    private Label endLabel;
    private Label pathLabel;
    private ArrayList<Point2D> pathCoords = new ArrayList<Point2D>();
    private ArrayList<DraggableImage> draggableImagesOnScreen = new ArrayList<>();


    protected GridPane makePathGrid() {
	grid = new GridPane();
	startCols = new ArrayList<Integer>();
	startRows = new ArrayList<Integer>();

	checkGrid = new GridPane();
	checkGrid.setMaxSize(1000, 750);
	setGridConstraints(checkGrid, INITIAL_PATH_SIZE);

	grid.setMaxSize(1000, 750); 
	setGridConstraints(grid, INITIAL_PATH_SIZE);

	model = new SelectionModel();
	new ShiftSelection(grid, model);

	grid.setStyle("-fx-background-image: url('file:images/plaingreen.png')"); 
	populateGrid();

	return grid;
    }
    public void setUpForWaves(EventHandler<MouseEvent> action) {
	makeUnDraggable();
	for(DraggableImage image : draggableImagesOnScreen) {
	    image.getPathImage().setOnMouseClicked(e -> action.handle(e));
	}
    }
    private void makeUnDraggable() {
	for(DraggableImage image : draggableImagesOnScreen) {
	    image.disableDraggable();
	}
    }

    //REFACTOR
    private void populateGrid() {

	for (int x = 0 ; x < grid.impl_getColumnCount(); x++) {
	    for (int y = 0 ; y < grid.impl_getRowCount(); y++) {
		StackPane cell = new StackPane();

		final int col = x;
		final int row = y;

		//This can be separate class (for drag over objects)
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
			    draggableImagesOnScreen.add(path);
			    //path.disableDraggable(); //ADDED THIS GET RID OF IT ONLY FOR TESTING
			    if (imageCompare(path.getPathImage().getImage(), startImage.getImage()) == true) {
				startLabel = new Label("start");
				checkGrid.add(startLabel, colIndex, rowIndex);
				startCols.add(colIndex);
				startRows.add(rowIndex);
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

	if (col < 0 || col >= grid.impl_getColumnCount() || row < 0 || row >= grid.impl_getRowCount())
	    return false;
	if ((getNode(checkGrid, col, row) == null))
	    return false;
	if (getNode(checkGrid, col, row) == endLabel) 
	    return true;

	removeNode(checkGrid, row, col);

	if ((checkPathConnected(row, col + 1)) == true) {
	    addCoordinates(row, col+1);
	    return true;
	}
	if ((checkPathConnected(row + 1 , col)) == true) {
	    addCoordinates(row + 1, col);
	    return true;
	}
	if ((checkPathConnected(row, col - 1)) == true) {
	    addCoordinates(row, col - 1);
	    return true;
	}
	if ((checkPathConnected(row - 1, col)) == true) {
	    addCoordinates(row - 1, col);
	    return true;
	}

	System.out.println(pathCoords);
	return false;
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


    public HashMap<Integer, ArrayList<Integer>> getStartingPosition() {
	HashMap<Integer, ArrayList<Integer>> coordMap = new HashMap<Integer, ArrayList<Integer>>();
	ArrayList<Integer> coords = new ArrayList<Integer>();
	for (int i=0; i<startCols.size(); i++) {
	    coords.clear();
	    coords.add(startRows.get(i));
	    coords.add(startCols.get(i));
	    coordMap.put(i, coords);
	}
	return coordMap;
    }

    public void addCoordinates(int row, int col) {
	double x = getNode(grid, col, row).getBoundsInParent().getMinX();
	double y = getNode(grid, col, row).getBoundsInParent().getMinY();
	Point2D point = new Point2D(x, y);
	pathCoords.add(point);
	System.out.println(pathCoords);
    }
}

