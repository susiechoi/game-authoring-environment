package authoring.frontend;


import java.awt.Point;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
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

/* 
 * Right click to be able to get specialty paths
 * Back to main button, if apply has not been clicked then prompt (changes will not be saved)
 * 
 * Need a way to get style info for defaults
 * check for completed path before demo (percolation), cannot click apply until this is done (The apply button will 
 * reassign the path in the AuthoringModel.java class to be used in the game)
 * 
 * Auto-generate new levels, reading in for default parameters
 * auto-populate cells by mouse drag (right click?) or copy and paste
 */

public class CreatePathGrid extends AdjustScreen {

	public static final int INITIAL_PATH_SIZE = 60;
	private double pathSize;
	private int colIndex;
	private int rowIndex;
	private GridPane grid = new GridPane();;
	private SelectionModel model;

	//TODO: have these update with change images, and get images from default file
	private ImageView startImage = new ImageView(new Image("file:images/start.png"));
	private ImageView endImage = new ImageView(new Image("file:images/end.png"));
	private ImageView pathImage = new ImageView(new Image("file:images/cobblestone.png"));
	private GridPane checkGrid;
	private Label startLabel;
	private Label endLabel;
	private Label pathLabel;
	private ArrayList<Point> pathCoords = new ArrayList<Point>();
	private ArrayList<DraggableImage> draggableImagesOnScreen = new ArrayList<>();
	private HashMap<String, List<Point>> gridImageCoordinates = new HashMap<String, List<Point>>(); //map (imagefileName, (row,col))
	private ArrayList<Point> startPoints = new ArrayList<Point>();
	private ArrayList<Point> endPoints = new ArrayList<Point>();
	private ArrayList<Point> pathPoints = new ArrayList<Point>();
	private DraggableImage myCurrentClicked;
	private int startCount = 0;

	public CreatePathGrid(AuthoringView view) {
		super(view);
	}

	protected GridPane makePathGrid() {

		grid = new GridPane();

		checkGrid = new GridPane();
		checkGrid.setMaxSize(1000, 750);
		setGridConstraints(checkGrid, INITIAL_PATH_SIZE);

		grid.setMaxSize(1000, 750); 
		setGridConstraints(grid, INITIAL_PATH_SIZE);

//		System.out.println(getView().getImageCoordinates());
//		gridImageCoordinates = getView().getImageCoordinates();


		model = new SelectionModel();
		new ShiftSelection(grid, model);

		grid.setStyle("-fx-background-image: url('file:images/white.png')"); 
		populateGrid();

		return grid;
	}

	//TODO: REFACTOR

	//Given: path images and locations as defaults, change to populate with initial params, 
	private void populateGrid() {

		for (int x = 0 ; x < grid.getColumnCount(); x++) {
			for (int y = 0 ; y < grid.getRowCount(); y++) {
				StackPane cell = new StackPane();

				final int col = x;
				final int row = y;

				checkGrid.add(cell, x, y);

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
							draggableImagesOnScreen.add(path);
							grid.add(path.getPathImage(), colIndex, rowIndex);
							if (imageCompare(path.getPathImage().getImage(), startImage.getImage()) == true) {
								startCount++;
								path.setPathName(startCount);
								startPoints.add(new Point(colIndex, rowIndex));
								startLabel = new Label("start");
								checkGrid.add(startLabel, colIndex, rowIndex);
							} else if (imageCompare(path.getPathImage().getImage(), endImage.getImage()) == true) {
								endPoints.add(new Point(colIndex, rowIndex));
								endLabel = new Label("end");
								checkGrid.add(endLabel, colIndex, rowIndex);
							} else if (imageCompare(path.getPathImage().getImage(), pathImage.getImage()) == true) {
								pathPoints.add(new Point(colIndex, rowIndex));
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

	public void addImagesToGrid(Map<String, List<Point>> imageCoordinates) {
		if (imageCoordinates.size() != 0) {
			for (String key: imageCoordinates.keySet()) { //goes through images
				for (int i = 0; i < imageCoordinates.keySet().size(); i++) {
					Point point = imageCoordinates.get(key).get(i);
					grid.add(new DraggableImage(new Image(key)), (int) point.getX(), (int) point.getY());
				}
			}
		}
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



	public boolean checkPathConnected(GridPane grid, int row, int col) {

		if (getNode(grid, col, row) != null) {
			Label checkLabel = (Label) getNode(grid, col, row);
			if (checkLabel.getText() == "end") {
				return true;
			}
		} else {

			return false;
		}

		removeNode(grid, row, col);

		if ((checkPathConnected(grid, row, col + 1)) == true) {
			System.out.println("right: " +col);
			grid.add(new Label("path"), col, row);
			System.out.println("HERE: " +grid.getChildren());
			addCoordinates(row, col+1);
			return true;
		}
		if ((checkPathConnected(grid, row + 1 , col)) == true) {
			System.out.println("down");
			grid.add(new Label("path"), col, row);
			addCoordinates(row + 1, col);
			return true;
		}
		if ((checkPathConnected(grid, row, col - 1)) == true) {
			System.out.println("left");
			grid.add(new Label("path"), col, row);
			addCoordinates(row, col - 1);
			return true;
		}
		if ((checkPathConnected(grid, row - 1, col)) == true) {
			System.out.println("up");
			grid.add(new Label("path"), col, row);
			addCoordinates(row - 1, col);
			return true;
		}

		grid.add(new Label("path"), col, row);
		return false;
	}

	public void addCoordinates(int row, int col) {
		double x = getNode(grid, col, row).getBoundsInParent().getMinX();
		double y = getNode(grid, col, row).getBoundsInParent().getMinY();
		Point point = new Point((int) x, (int) y);
		pathCoords.add(point);
	}

	public Node getNode(GridPane gridPane, int col, int row) {
		Node result = null;
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
				result = null;
			}
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

	public void setUpForWaves(EventHandler<MouseEvent> action) {
		makeUnDraggable();
		for(DraggableImage image : draggableImagesOnScreen) {
			image.getPathImage().setOnMouseClicked(e -> 
			{
				myCurrentClicked = image;
				action.handle(e);
			});
		}
	}
	private void makeUnDraggable() {
		for(DraggableImage image : draggableImagesOnScreen) {
			image.disableDraggable();
		}
	}

	protected DraggableImage getMostRecentlyClicked() {
		return myCurrentClicked;
	}

	public List<Point> getStartingPosition() {
		return startPoints;
	}

	public void setBackgroundImage(String backGroundFileName) {
		grid.setStyle("-fx-background-image: url(" + backGroundFileName + ")");
	}

	public GridPane getGrid() {
		return grid;
	}

	public GridPane getCheckGrid() {
		return checkGrid;
	}

	public double getPathSize() {
		return pathSize;
	}

	public List<Point> getAbsoluteCoordinates() {
		return pathCoords;
	}

	public int isStartInGrid() {
		return startCount;
	}

	//TODO: fix this
	public HashMap<String, List<Point>> getGridImageCoordinates() {
		gridImageCoordinates.put(startImage.getImage().getUrl(), startPoints);
		gridImageCoordinates.put(endImage.getImage().getUrl(), endPoints);
		gridImageCoordinates.put(pathImage.getImage().getUrl(), pathPoints);
		return gridImageCoordinates;
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