package authoring.frontend;

import java.awt.Point;
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
 * IMPORTANT TODO: Correctly pass background image, fix image checking, integrate styling 
 * TONIGHT: PASS Row and column counts (make methods to get rid of get column count...?), make laod to edit work
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
public class CreatePathGrid extends AdjustScreen {

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
    //private ArrayList<DraggableImage> draggableImagesOnScreen;


    private HashMap<String, List<Point>> gridImageCoordinates = new HashMap<String, List<Point>>(); //map (imagefileName, (row,col))
    private ArrayList<Point> startPoints = new ArrayList<Point>();
    private ArrayList<Point> endPoints = new ArrayList<Point>();
    private ArrayList<Point> pathPoints = new ArrayList<Point>();
    private DraggableImage myCurrentClicked;
    private DraggableImage path;
    private int startCount = 0;
    // private EventHandler<DragEvent> myOnDragDropped;
    // private EventHandler<DragEvent> myOnDragOver;
    private EventHandler<MouseEvent> myOnMouseClicked = new EventHandler <MouseEvent>() {
	@Override
	public void handle(MouseEvent event) {
	}
    };
    public CreatePathGrid(AuthoringView view) {
	super(view);
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

	System.out.println("HERE: " +getView().getObjectAttribute("Path", "", "myPathMap"));
	if (((Map<String, List<Point>>) getView().getObjectAttribute("Path", "", "myPathMap")).size() > 2) {
	    addImagesToGrid((Map<String, List<Point>>) getView().getObjectAttribute("Path", "", "myPathMap"), (int) getView().getObjectAttribute("Path", "", "myPathSize"));
	    setGridConstraints(grid, (int) getView().getObjectAttribute("Path", "", "myPathSize"));
	    grid.setStyle("-fx-background-image: url(" + getView().getObjectAttribute("Path", "", "myBackgroundImage") + ")");
	}
	return grid;
    }

    //Given: path images and locations as defaults, change to populate with initial params
    private void populateGrid(GridPane grid) { //grid, 

	for (int x = 0 ; x < grid.impl_getColumnCount(); x++) {
	    for (int y = 0 ; y < grid.impl_getRowCount(); y++) {
		StackPane cell = new StackPane();

		final int col = x;
		final int row = y;

		cell.setOnDragOver(new EventHandler <DragEvent>() {
		    @Override
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
		    @Override
		    public void handle(DragEvent event) {
			event.acceptTransferModes(TransferMode.ANY);
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasImage()) {
			    path = new DraggableImage(db.getImage());
			    path.setDraggable(checkGrid, rowIndex, colIndex);
			    path.getPathImage().fitWidthProperty().bind(cell.widthProperty()); 
			    path.getPathImage().fitHeightProperty().bind(cell.heightProperty());
			    //draggableImagesOnScreen.add(path);
			    grid.add(path.getPathImage(), colIndex, rowIndex);
			    if (((ImageView) event.getGestureSource()).getId() == "start") {
				startCount++;
				path.getPathImage().setId("start");
				path.setPathName(startCount);
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

    public void addImagesToGrid(Map<String, List<Point>> map, int pathSize) {
	int count = 0;
	for (String key: map.keySet()) {
	    count++;
	    List<Point> pointList = map.get(key);
	    System.out.println("key: " +key);
	    System.out.println("points: " +pointList);
	    System.out.println(count);
	    for (int i = 0; i < pointList.size(); i++) {
		Point point = pointList.get(i);
		DraggableImage path = new DraggableImage(new Image(key));
		path.setDraggable(checkGrid, (int)point.getY(), (int)point.getX());
		path.getPathImage().setFitWidth(pathSize);
		path.getPathImage().setFitHeight(pathSize);
		//				TODO: Figure out how to get this to work with path checking
		//				if (count == 1) {
		//					checkGrid.add(startLabel, (int)point.getX(), (int)point.getY());
		//				} else if (count == 2) {
		//					checkGrid.add(pathLabel, (int)point.getX(), (int)point.getY());
		//				} else if (count == 3) {
		//					checkGrid.add(endLabel, (int)point.getX(), (int)point.getY());
		//				}
		//				GridPane.setFillWidth(path.getPathImage(), true);
		//				GridPane.setFillHeight(path.getPathImage(), true);
		grid.add(path.getPathImage(), (int)point.getX(), (int)point.getY());
	    }
	}
    }

    protected boolean checkPathConnected(GridPane grid, int row, int col) {

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
	    grid.add(new Label("path"), col, row);
	    addCoordinates(row, col+1);
	    return true;
	}
	if ((checkPathConnected(grid, row + 1 , col)) == true) {
	    grid.add(new Label("path"), col, row);
	    addCoordinates(row + 1, col);
	    return true;
	}
	if ((checkPathConnected(grid, row, col - 1)) == true) {
	    grid.add(new Label("path"), col, row);
	    addCoordinates(row, col - 1);
	    return true;
	}
	if ((checkPathConnected(grid, row - 1, col)) == true) {
	    grid.add(new Label("path"), col, row);
	    addCoordinates(row - 1, col);
	    return true;
	}
	grid.add(new Label("path"), col, row);
	return false;
    }

    protected void addCoordinates(int row, int col) {
	//TODO: add Ids? for start/path/end
	double x = getNode(grid, col, row).getBoundsInParent().getMinX();
	double y = getNode(grid, col, row).getBoundsInParent().getMinY();
	Point point = new Point((int) x, (int) y);
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
	///System.out.println("trying to make undraggable");
	for(Node newNode: grid.getChildren()){
	    //newNode.removeEventHandler(DragEvent.DRAG_DROPPED, myOnDragDropped);
	    //newNode.setOnDragDropped(e -> {});
	    myOnMouseClicked = new EventHandler <MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
		    myCurrentClicked = new DraggableImage(startImage.getImage()); //TODO
		    action.handle(event);
		}
	    };
	    newNode.setOnMouseClicked(myOnMouseClicked);
	}
    }

    protected void setUpForPathCreation() {
	//			for(Node newNode: grid.getChildren()){
	//				if(newNode instanceof StackPane) {
	//					System.out.println("seeing the node");
	//					if(myOnMouseClicked!=null|| unDraggable) {
	//						System.out.println("hellppp");
	//					newNode.removeEventHandler(MouseEvent.MOUSE_CLICKED, myOnMouseClicked);
	//					newNode.setOnDragDropped(myOnDragDropped);
	//				}
	//			}
    }


    protected DraggableImage getMostRecentlyClicked() {
	return myCurrentClicked;
    }

    protected List<Point> getStartingPosition() { //TODO: refactor, should not iterate through grid for this and getGridImageCoordinates
	for (int x = 0; x < checkGrid.impl_getColumnCount(); x++) {
	    for (int y = 0; y < checkGrid.impl_getRowCount(); y++) {
		if (getNode(checkGrid, x, y) != null && ((Label) getNode(checkGrid, x, y)).getText().equals("start")) {
		    startPoints.add(new Point(x, y));
		}
	    }
	}
	return startPoints;
    }

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

    protected HashMap<String, List<Point>> getGridImageCoordinates() {
	for (int x = 0; x < checkGrid.impl_getColumnCount(); x++) {
	    for (int y = 0; y < checkGrid.impl_getRowCount(); y++) {
		if (getNode(checkGrid, x, y) != null && ((Label) getNode(checkGrid, x, y)).getText() == "path") {
		    pathPoints.add(new Point(x, y));
		} else if (getNode(checkGrid, x, y) != null && ((Label) getNode(checkGrid, x, y)).getText() == "start") {
		    startPoints.add(new Point(x, y));
		} else if (getNode(checkGrid, x, y) != null && ((Label) getNode(checkGrid, x, y)).getText() == "end") {
		    endPoints.add(new Point(x, y));
		}
	    }
	}
	gridImageCoordinates.put(startImage.getImage().impl_getUrl(), startPoints);
	gridImageCoordinates.put(endImage.getImage().impl_getUrl(), endPoints);
	gridImageCoordinates.put(pathImage.getImage().impl_getUrl(), pathPoints);
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
	return grid.impl_getColumnCount();
    }

    public int getRowCount() {
	return grid.impl_getRowCount();
    }


    @Override
    protected Parent populateScreenWithFields() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected void populateFieldsWithData() {
    }
}
