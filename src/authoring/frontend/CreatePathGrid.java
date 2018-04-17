package authoring.frontend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * IMPORTANT TODO: remove from grid on set on trash, and move to other location (maybe cursor becomes delete tool), change images on set new image (for check grid completion),  
 * 
 * Right click to be able to get specialty paths
 * Back to main button, if apply has not been clicked then prompt (changes will not be saved)
 * 
 * Need a way to get style info for defaults
 * 
 * Auto-generate new levels, reading in for default parameters
 * auto-populate cells by mouse drag (right click?) or copy and paste
 */

/**
 * Class to create/manage the GridPane for path authoring and disply.
 * @author Erik Riis
 *
 */
public class CreatePathGrid extends AdjustScreen {

    public static final int INITIAL_PATH_SIZE = 60;
    private double pathSize;
    private int colIndex;
    private int rowIndex;
    private GridPane grid = new GridPane();
    private SelectionModel model;
    private ImageView startImage = new ImageView(new Image("file:images/brick.png"));
    private ImageView endImage = new ImageView(new Image("file:images/darkstone.png"));
    private ImageView pathImage = new ImageView(new Image("file:images/cobblestone.png"));
    private GridPane checkGrid;
    private Label startLabel;
    private Label endLabel;
    private Label pathLabel;
    private ArrayList<Point> pathCoords = new ArrayList<Point>();
    private ArrayList<DraggableImage> draggableImagesOnScreen;
    private HashMap<String, List<Point>> gridImageCoordinates = new HashMap<String, List<Point>>(); //map (imagefileName, (row,col))
    private ArrayList<Point> startPoints = new ArrayList<Point>();
    private ArrayList<Point> endPoints = new ArrayList<Point>();
    private ArrayList<Point> pathPoints = new ArrayList<Point>();
    private DraggableImage myCurrentClicked;
    private DraggableImage path;
    private int startCount = 0;
    private EventHandler<DragEvent> myOnDragDropped;
    private EventHandler<MouseEvent> myOnMouseClicked = new EventHandler <MouseEvent>() {
	public void handle(MouseEvent event) {
	}
    };
	private boolean unDraggable;
	public CreatePathGrid(AuthoringView view) {
	    super(view);
	    unDraggable = false;
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
	    populateGrid();

	    if (getView().getPathGrid().getChildren().size() != 0) {
		grid = getView().getPathGrid();
	    }
	    draggableImagesOnScreen = new ArrayList<>();
	    return grid;
	}

	//TODO: REFACTOR

	//Given: path images and locations as defaults, change to populate with initial params
	private void populateGrid() {
	    for (int x = 0 ; x < grid.impl_getColumnCount(); x++) {
		for (int y = 0 ; y < grid.impl_getRowCount(); y++) {
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
		    myOnDragDropped = new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {
			    event.acceptTransferModes(TransferMode.ANY);
			    Dragboard db = event.getDragboard();
			    boolean success = false;
			    if (db.hasImage()) {
				path = new DraggableImage(db.getImage());
				path.setDraggable(checkGrid, rowIndex, colIndex);
				path.getPathImage().fitWidthProperty().bind(cell.widthProperty()); 
				path.getPathImage().fitHeightProperty().bind(cell.heightProperty());
				System.out.println("should be adding??");
				if(draggableImagesOnScreen==null) {
				    draggableImagesOnScreen = new ArrayList<>();
				}
				if(!draggableImagesOnScreen.contains(path)) {
				    draggableImagesOnScreen.add(path);
				}
				grid.add(path.getPathImage(), colIndex, rowIndex);

				//imagecompare doesn't work if all same image
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
		    };
		    cell.setOnDragDropped(myOnDragDropped);
		    grid.add(cell, x, y);
		}
	    }
	}



	protected void setGridConstraints(GridPane grid, double size) {
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

	protected boolean imageCompare(Image image1, Image image2) {
	    for (int i = 0; i < image1.getWidth(); i++){
		for (int j = 0; j < image1.getHeight(); j++){
		    if (image1.getPixelReader().getArgb(i, j) != image2.getPixelReader().getArgb(i, j)) {
			return false;
		    }
		}
	    }
	    return true;
	}

	protected void setUpForWaves(EventHandler<MouseEvent> action) {
	    makeUnDraggable(action);
	}
	private void makeUnDraggable(EventHandler<MouseEvent> action) {
	    ///System.out.println("trying to make undraggable");
	    for(Node newNode: grid.getChildren()){
		newNode.removeEventHandler(DragEvent.DRAG_DROPPED, myOnDragDropped);
		newNode.setOnDragDropped(e -> {});
		myOnMouseClicked = new EventHandler <MouseEvent>() {
		    public void handle(MouseEvent event) {
			myCurrentClicked = new DraggableImage(startImage.getImage()); //TODO
			action.handle(event);
		    }
		};
		newNode.setOnMouseClicked(myOnMouseClicked);
	    }
	}
	protected void setUpForPathCreation() {
	    for(Node newNode: grid.getChildren()){
		if(newNode instanceof StackPane) {
		    //System.out.println("seeing the node");
		    //if(myOnMouseClicked!=null|| unDraggable) {
		    // System.out.println("hellppp");
		    newNode.removeEventHandler(MouseEvent.MOUSE_CLICKED, myOnMouseClicked);
		    newNode.setOnDragDropped(myOnDragDropped);
		}
	    }
	}
	//myOnMouseClicked = null;
	// }
	//		if(node instanceof ImageView && (imageCompare(((ImageView)node).getImage(), startImage.getImage())) {
	//		    node.setOnMouseClicked(e ->
	//		    		myCurrentClicked = new DraggableImage(startImage);
	//		    		myCurrentClicked.
	//			    );
	//		}

	protected DraggableImage getMostRecentlyClicked() {
	    return myCurrentClicked;
	}

	protected List<Point> getStartingPosition() {
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

	protected double getPathSize() {
	    return pathSize;
	}

	protected List<Point> getAbsoluteCoordinates() {
	    return pathCoords;
	}

	protected int isStartInGrid() {
	    return startCount;
	}


	protected HashMap<String, List<Point>> getGridImageCoordinates() {
	    gridImageCoordinates.put(startImage.getImage().impl_getUrl(), startPoints);
	    gridImageCoordinates.put(endImage.getImage().impl_getUrl(), endPoints);
	    gridImageCoordinates.put(pathImage.getImage().impl_getUrl(), pathPoints);
	    return gridImageCoordinates;
	}


	public void setStartImage(ImageView newImage) {
	    startImage = newImage;
	}
	public void setEndImage(ImageView newImage) {
	    System.out.println("Change end image: " +newImage);
	    endImage = newImage;
	}
	public void setPathImage(ImageView newImage) {
	    System.out.println("Change path image: " +newImage);
	    pathImage = newImage;
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