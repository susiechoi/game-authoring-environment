package authoring.frontend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


/**
 * Class to create/manage the GridPane for path authoring and display.
 * @author Erik Riis
 *
 */
public class CreatePathGrid {

    public static final int INITIAL_PATH_SIZE = 50; 
    public static final String DEFAULT_BACKGROUND_IMAGE = "file:images/generalbackground.jpg"; 
    public static final String START = "start";
    public static final String PATH = "path";
    public static final String END = "end";
    private int pathSize = INITIAL_PATH_SIZE;
    private GridPane grid = new GridPane();
    private GridPane checkGrid;
    private ArrayList<Point> pathCoords = new ArrayList<Point>();
    private ArrayList<Point> startPoints = new ArrayList<Point>();
    private AuthoringView myView;
    private int myGridWidth;
    private int myGridHeight;

    public CreatePathGrid(AuthoringView view, int width, int height) {
	myView = view;
	myGridWidth = width;
	myGridHeight = height;
    }

    /**
     * @return
     */
    protected GridPane makePathGrid() {
	grid = new GridPane();
	grid.setMaxSize(myGridWidth, myGridHeight); 
	setGridConstraints(grid, INITIAL_PATH_SIZE);

	checkGrid = new GridPane();
	checkGrid.setMaxSize(myGridWidth, myGridHeight);
	setGridConstraints(checkGrid, INITIAL_PATH_SIZE);

	SelectionModel model = new SelectionModel(grid, checkGrid, pathSize);
	new ShiftSelection(grid, model);

	grid.setStyle("-fx-background-image: url(" + DEFAULT_BACKGROUND_IMAGE + ")"); 
	populateGridCells(grid);
	
	@SuppressWarnings("unchecked")
	Map<String, List<Point>> loadedPathMap = (Map<String, List<Point>>) myView.getObjectAttribute("Path", "", "myPathMap");

	if (loadedPathMap.size() > 2) {
	    setGridConstraints(grid, (int) myView.getObjectAttribute("Path", "", "myPathSize"));
	    grid.setStyle("-fx-background-image: url("+myView.getObjectAttribute("Path", "", "myBackgroundImage")+")");
	    addImagesToGrid(loadedPathMap, (int) myView.getObjectAttribute("Path", "", "myPathSize"));
	}
	return grid;
    }

    private void populateGridCells(GridPane grid) { 
	for (int x = 0 ; x < grid.getColumnCount(); x++) {
	    for (int y = 0 ; y < grid.getRowCount(); y++) {
		new GridDropCell(grid, checkGrid, x, y);
	    }
	}
    }

    protected void setGridConstraints(GridPane grid, int size) {
	grid.getColumnConstraints().clear();
	grid.getRowConstraints().clear();
	pathSize = size;
	for (int i = 0; i < myGridWidth/pathSize; i++) {
	    ColumnConstraints colConst = new ColumnConstraints();
	    colConst.setPercentWidth(myGridWidth/pathSize);
	    grid.getColumnConstraints().add(colConst);
	}
	for (int i = 0; i < myGridHeight/pathSize; i++) {
	    RowConstraints rowConst = new RowConstraints();
	    rowConst.setPercentHeight(myGridHeight/pathSize);
	    grid.getRowConstraints().add(rowConst);         
	}
    }

    private void addImagesToGrid(Map<String, List<Point>> map, int pathSize) {
	checkGrid.getChildren().clear();
	for (String key: map.keySet()) {
	    String imageKey = key.substring(1);
	    List<Point> pointList = map.get(key);
	    for (int i = 0; i < pointList.size(); i++) {
		Point point = pointList.get(i);
		DraggableImage path = new DraggableImage(new Image(imageKey));
		path.setDraggable(checkGrid, (int)point.getY(), (int)point.getX());
		path.getPathImage().setFitWidth(pathSize);
		path.getPathImage().setFitHeight(pathSize);
		grid.add(path.getPathImage(), (int)point.getX(), (int)point.getY());
		if (key.equals("s"+myView.getObjectAttribute("Path", "", "myStartImage"))) {
		    checkGrid.add(new Label(START), (int)point.getX(), (int)point.getY());
		    path.getPathImage().setId(START);
		} else if (key.equals("p"+myView.getObjectAttribute("Path", "", "myPathImage"))) {
		    checkGrid.add(new Label(PATH), (int)point.getX(), (int)point.getY());
		    path.getPathImage().setId(PATH);
		} else if (key.equals("e"+myView.getObjectAttribute("Path", "", "myEndImage"))) {
		    checkGrid.add(new Label(END), (int)point.getX(), (int)point.getY());
		    path.getPathImage().setId(END);
		}
		GridPane.setFillWidth(path.getPathImage(), true);
		GridPane.setFillHeight(path.getPathImage(), true);
	    }
	}
    }

    protected boolean checkPathConnected(GridPane grid, int row, int col) {
	Label checkLabel = (Label) getNode(grid, col, row);
	if (getNode(grid, col, row) != null) {
	    if (checkLabel.getText() == END) {
		return true;
	    } 
	} else {
	    return false;
	}
	removeNode(grid, row, col);
	addCoordinates(row, col);

	if ((checkPathConnected(grid, row + 1 , col)) == true) {
	    grid.add(new Label(PATH), col, row);
	    return true;
	}
	if ((checkPathConnected(grid, row, col + 1)) == true) {
	    grid.add(new Label(PATH), col, row);
	    return true;
	}
	if ((checkPathConnected(grid, row, col - 1)) == true) {
	    grid.add(new Label(PATH), col, row);
	    return true;
	}
	if ((checkPathConnected(grid, row - 1, col)) == true) {
	    grid.add(new Label(PATH), col, row);
	    return true;
	}
	return false;
    }

    protected void addCoordinates(int row, int col) {
	Bounds nodeBounds = getNode(grid, col, row).getBoundsInParent();
	double x = nodeBounds.getMinX();
	double y = nodeBounds.getMinY();
	Point point = new Point((int) Math.round(x), (int) Math.round(y));
	pathCoords.add(point);
    }

    protected List<Point> getStartingPosition(GridPane grid) { //TODO: refactor, should not iterate through grid for this and getGridImageCoordinates
	startPoints = new ArrayList<Point>();
	for (int x = 0; x < grid.getColumnCount(); x++) {
	    for (int y = 0; y < grid.getRowCount(); y++) {
		if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText().equals(START)) {
		    startPoints.add(new Point(x, y));
		}
	    }
	}
	return startPoints;
    }

    protected HashMap<String, List<Point>> getGridImageCoordinates(GridPane grid, String startImage, String pathImage, String endImage) {
	List<Point> pathPoints = new ArrayList<Point>();
	List<Point> endPoints = new ArrayList<Point>();
	HashMap<String, List<Point>> gridImageCoordinates = new HashMap<String, List<Point>>();
	for (int x = 0; x < grid.getColumnCount(); x++) {
	    for (int y = 0; y < grid.getRowCount(); y++) {
		if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText().equals(PATH)) {
		    pathPoints.add(new Point(x, y));
		} else if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText().equals(END)) {
		    endPoints.add(new Point(x, y));
		}
	    }
	}
	gridImageCoordinates.put("s"+startImage, startPoints);
	gridImageCoordinates.put("e"+endImage, endPoints);
	gridImageCoordinates.put("p"+pathImage, pathPoints);
	return gridImageCoordinates;
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
    public GridPane copyGrid(GridPane grid) {
	GridPane copiedGrid = new GridPane();
	for (int x = 0; x < grid.getColumnCount(); x++) {
	    for (int y = 0; y < grid.getRowCount(); y++) {
		if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText() == START) {
		    copiedGrid.add(new Label(START), x, y);
		} else if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText() == PATH) {
		    copiedGrid.add(new Label(PATH), x, y);
		} else if (getNode(grid, x, y) != null && ((Label) getNode(grid, x, y)).getText() == END) {
		    copiedGrid.add(new Label(END), x, y);
		}
	    }
	}
	return copiedGrid;
    }


    public int getGridWidth() {
	return myGridWidth;
    }

    public int getGridHeight() {
	return myGridHeight;
    }

    protected GridPane getGrid() {
	return grid;
    }

    protected GridPane getCheckGrid() {
	return checkGrid;
    }

    protected void setBackgroundImage(String backGroundFileName) {
	grid.setStyle("-fx-background-image: url(" + backGroundFileName + ")");
    }

    protected int getPathSize() {
	return pathSize;
    }

    protected List<Point> getAbsoluteCoordinates() {
	return pathCoords;
    }
}