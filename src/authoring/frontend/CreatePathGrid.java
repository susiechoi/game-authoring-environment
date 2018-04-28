package authoring.frontend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

/* 
 * IMPORTANT TODO: fix image checking, Path (enemies go backwards/ not getting to last point)
 * 
 * Right click to be able to get specialty paths
 * Apply goes back one screen
 * Auto-generate new levels
 */

/**
 * Class to create/manage the GridPane for path authoring and display.
 * @author Erik Riis
 *
 */
public class CreatePathGrid {

	public static final int INITIAL_PATH_SIZE = 60; 
	private int pathSize;
	private int colIndex;
	private int rowIndex;
	private GridPane grid = new GridPane();
	private SelectionModel model;
	private ImageView startImage = new ImageView(new Image("file:images/brick.png"));
	private ImageView endImage = new ImageView(new Image("file:images/darkstone.png"));
	private ImageView pathImage = new ImageView(new Image("file:images/cobblestone.png"));
	private GridPane checkGrid;
	private Label startLabel = new Label("start");
	private Label endLabel = new Label("end");
	private Label pathLabel = new Label("path");
	private ArrayList<Point> pathCoords = new ArrayList<Point>();
	private ArrayList<Point> startPoints = new ArrayList<Point>();
	private ArrayList<Point> endPoints = new ArrayList<Point>();
	private ArrayList<Point> pathPoints = new ArrayList<Point>();
	private Point myCurrentClicked;
	private DraggableImage path;
	private int startCount = 0;
	private AuthoringView myView;
	private EventHandler<MouseEvent> myOnMouseClicked = new EventHandler <MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			//DO NOTHING - just an initialization of the eventHandler
		}
	};
	public CreatePathGrid(AuthoringView view) {
		myView = view;
	}
	private AuthoringView getView() {
		return myView;
	}


	/**
	 * @return
	 */
	protected GridPane makePathGrid() {
		grid = new GridPane();

		checkGrid = new GridPane();
		checkGrid.setMaxSize(1020.0, 650.0);
		setGridConstraints(checkGrid, INITIAL_PATH_SIZE);

		grid.setMaxSize(1020.0, 650.0); 
		setGridConstraints(grid, INITIAL_PATH_SIZE);

		model = new SelectionModel();
		new ShiftSelection(grid, model);

		grid.setStyle("-fx-background-image: url('file:images/generalbackground.jpg')"); 
		populateGrid(grid);
	
		if (((Map<String, List<Point>>) getView().getObjectAttribute("Path", "", "myPathMap")).size() > 2) { //TODO: better way to check if not default
			setGridConstraints(grid, (int) getView().getObjectAttribute("Path", "", "myPathSize"));
			grid.setStyle("-fx-background-image: url("+getView().getObjectAttribute("Path", "", "myBackgroundImage")+")");
			addImagesToGrid((Map<String, List<Point>>) getView().getObjectAttribute("Path", "", "myPathMap"), (int) getView().getObjectAttribute("Path", "", "myPathSize"));
		}
		return grid;
	}

	//Given: path images and locations as defaults, change to populate with initial params
	private void populateGrid(GridPane grid) { //grid, 
		
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
							path = new DraggableImage(db.getImage());
							path.setDraggable(checkGrid, rowIndex, colIndex);
							path.getPathImage().fitWidthProperty().bind(cell.widthProperty()); 
							path.getPathImage().fitHeightProperty().bind(cell.heightProperty());
							//							draggableImagesOnScreen.add(path);
							removeNode(grid, colIndex, rowIndex);
							grid.add(path.getPathImage(), colIndex, rowIndex);
							if (((ImageView) event.getGestureSource()).getId() == "start") {	
								path.getPathImage().setId("start");
								checkGrid.add(new Label("start"), colIndex, rowIndex);
							} else if (((ImageView) event.getGestureSource()).getId() == "end") {
								path.getPathImage().setId("end");
								checkGrid.add(new Label("end"), colIndex, rowIndex);
							} else if (((ImageView) event.getGestureSource()).getId() == "path") {
								path.getPathImage().setId("path");
								checkGrid.add(new Label("path"), colIndex, rowIndex);
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

	protected void setGridConstraints(GridPane grid, int size) {
		grid.getColumnConstraints().clear();
		grid.getRowConstraints().clear();
		pathSize = size;
		for (int i = 0; i < 1020.0/pathSize; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPrefWidth(pathSize);
			grid.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < 650.0/pathSize; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPrefHeight(pathSize);
			grid.getRowConstraints().add(rowConst);         
		}
	}

	//Very similar to populate grid (duplicate code)
	public void addImagesToGrid(Map<String, List<Point>> map, int pathSize) {
		checkGrid.getChildren().clear();
		for (String key: map.keySet()) {
			List<Point> pointList = map.get(key);
			for (int i = 0; i < pointList.size(); i++) {
				Point point = pointList.get(i);
				DraggableImage path = new DraggableImage(new Image(key));
				path.setDraggable(checkGrid, (int)point.getY(), (int)point.getX());
				path.getPathImage().setFitWidth(pathSize);
				path.getPathImage().setFitHeight(pathSize);
				if (key.equals(getView().getObjectAttribute("Path", "", "myStartImage"))) {
					checkGrid.add(new Label("start"), (int)point.getX(), (int)point.getY());
					path.getPathImage().setId("start");
					setPathImage(new ImageView(new Image(key)));
				} else if (key.equals(getView().getObjectAttribute("Path", "", "myPathImage"))) {
					checkGrid.add(new Label("path"), (int)point.getX(), (int)point.getY());
					setPathImage(new ImageView(new Image(key)));
				} else if (key.equals(getView().getObjectAttribute("Path", "", "myEndImage"))) {
					checkGrid.add(new Label("end"), (int)point.getX(), (int)point.getY());
					setEndImage(new ImageView(new Image(key)));
				}
				GridPane.setFillWidth(path.getPathImage(), true);
				GridPane.setFillHeight(path.getPathImage(), true);
				grid.add(path.getPathImage(), (int)point.getX(), (int)point.getY());
			}
		}
	}

	protected boolean checkPathConnected(GridPane grid, int row, int col) {
		Label checkLabel = (Label) getNode(grid, col, row);
		if (getNode(grid, col, row) != null) {
			if (checkLabel.getText() == "end") {
				addCoordinates(row, col);
				return true;
			} 
		} else {
			return false;
		}

		removeNode(grid, row, col);
		addCoordinates(row, col);

		if ((checkPathConnected(grid, row, col + 1)) == true) {
			grid.add(new Label("path"), col, row);
			return true;
		}
		if ((checkPathConnected(grid, row + 1 , col)) == true) {
			grid.add(new Label("path"), col, row);
			return true;
		}
		if ((checkPathConnected(grid, row, col - 1)) == true) {
			grid.add(new Label("path"), col, row);
			return true;
		}
		if ((checkPathConnected(grid, row - 1, col)) == true) {
			grid.add(new Label("path"), col, row);
			return true;
		}
		return false;
	}

	protected void addCoordinates(int row, int col) {
		Bounds nodeBounds = getNode(grid, col, row).localToScreen(getNode(grid, col, row).getBoundsInLocal());
		double x = (nodeBounds.getMinX() + nodeBounds.getWidth())/2;
		double y = (nodeBounds.getMinY() + nodeBounds.getHeight())/2;
		Point point = new Point((int) Math.round(x), (int) Math.round(y));
		pathCoords.add(point);
	}

	protected Node getNode(GridPane gridPane, int col, int row) {
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


	protected void removeNode(GridPane grid, int row, int col) {
		for(Node node : grid.getChildren()) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
				grid.getChildren().remove(node);
				break;
			}
		} 
	}

	protected void setUpForWaves(EventHandler<MouseEvent> action) {
		makeUnDraggable(action);
	}


	private void makeUnDraggable(EventHandler<MouseEvent> action) {	
		List<Point> startCoords = getStartingPosition(checkGrid);
		
		grid.setOnMouseClicked(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//have start coords, first in the coords is first path...
				Node node = (Node) event.getTarget();
				if (node instanceof ImageView && ((ImageView) node).getId() == "start") {
				    Bounds nodeBounds = node.localToScreen(node.getBoundsInLocal());
					double x = (nodeBounds.getMinX() + nodeBounds.getWidth())/2;
					double y = (nodeBounds.getMinY() + nodeBounds.getHeight())/2;
					Point point = new Point((int) Math.round(x), (int) Math.round(y));
					myCurrentClicked = point;
					action.handle(event);
					ColorAdjust colorAdjust = new ColorAdjust();
					colorAdjust.setBrightness(0.5);
					((ImageView) node).setEffect(colorAdjust);
					
					//have startCoords sort based on id
					
					
					//TODO: with waves, need the starting point, specify waves for each point
				}
			}
		});
	}

	protected void setUpForPathCreation() {
		//			for(Node newNode: grid.getChildren()){
		//				if(newNode instanceof StackPane) {
		//					System.out.println("seeing the node");
		//					if(myOnMouseClicked!=null|| unDraggable) {
		//					newNode.removeEventHandler(MouseEvent.MOUSE_CLICKED, myOnMouseClicked);
		//					newNode.setOnDragDropped(myOnDragDropped);
		//				}
		//			}
	}

	protected Point getMostRecentlyClicked() {
		return myCurrentClicked;
	}

	protected List<Point> getStartingPosition(GridPane grid) { //TODO: refactor, should not iterate through grid for this and getGridImageCoordinates
		startPoints.clear();
		for (int x = 0; x < grid.getColumnCount(); x++) {
			for (int y = 0; y < grid.getRowCount(); y++) {
				if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText().equals("start")) {
//					if (getNode(grid, x, y))
					//get number on id of each node added, sort based on that
					startPoints.add(new Point(x, y));
				}
			}
		}
		return startPoints;
	}
	
	//getStartPointNames

	protected void setBackgroundImage(String backGroundFileName) {
		grid.setStyle("-fx-background-image: url(" + backGroundFileName + ")");
	}

	protected GridPane getGrid() {
		return grid;
	}

	protected GridPane getCheckGrid() {
		return checkGrid;
	}

	protected int getPathSize() {
		return pathSize;
	}

	protected List<Point> getAbsoluteCoordinates() {
		return pathCoords;
	}

	protected HashMap<String, List<Point>> getGridImageCoordinates(GridPane grid, String startImage, String pathImage, String endImage) {
		pathPoints.clear();
		endPoints.clear();
		HashMap<String, List<Point>> gridImageCoordinates = new HashMap<String, List<Point>>();
		for (int x = 0; x < grid.getColumnCount(); x++) {
			for (int y = 0; y < grid.getRowCount(); y++) {
				 if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText().equals("path")) {
					pathPoints.add(new Point(x, y));
				} else if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText().equals("end")) {
					endPoints.add(new Point(x, y));
				}
			}
		}
		gridImageCoordinates.put(startImage, startPoints);
		gridImageCoordinates.put(endImage, endPoints);
		gridImageCoordinates.put(pathImage, pathPoints);
		return gridImageCoordinates;
	}

	public void setStartImage(ImageView newImage) {
		startImage = newImage;
	}

	public void setEndImage(ImageView newImage) {
		endImage = newImage;
	}

	public void setPathImage(ImageView newImage) {
		pathImage = newImage;
	}

	public int getColumnCount() {
		return grid.getColumnCount();
	}

	public int getRowCount() {
		return grid.getRowCount();
	}
	
	public GridPane copyGrid(GridPane grid) {
		GridPane copiedGrid = new GridPane();
		for (int x = 0; x < grid.getColumnCount(); x++) {
			for (int y = 0; y < grid.getRowCount(); y++) {
				if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText() == "start") {
					copiedGrid.add(new Label("start"), x, y);
				} else if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText() == "path") {
					copiedGrid.add(new Label("path"), x, y);
				} else if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText() == "end") {
					copiedGrid.add(new Label("end"), x, y);
				}
			}
		}
		return copiedGrid;
	}
	
}
