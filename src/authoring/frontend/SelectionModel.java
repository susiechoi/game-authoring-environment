package authoring.frontend;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Creates the selection model that items get added to on shift selection and the functionalities of copy and paste
 * @author erikriis
 */
public class SelectionModel {
    public static final String CELL_SELECT_STYLE = "-fx-background-color: rgba(255,255,255, 0.3); -fx-background-radius: 10;";
    public static final String CELL_DESELECT_STYLE = "-fx-background-color: rgba(0,0,0,0);";
    private Set<Pane> selectedCells = new HashSet<>();
    private Node selectedNode;
    private Node copyNode;
    private GridPane myGrid;
    private GridPane myCheckGrid;
    private int mySize;

    /**
     * Constructor for the Selection Model
     * @param grid
     * @param checkGrid
     * @param size
     */
    public SelectionModel(GridPane grid, GridPane checkGrid, int size) {
	myGrid = grid;
	myCheckGrid = checkGrid;
	mySize = size;
    }

    /**
     * Adds a gridPane cell to selection model
     * @param pane
     */
    public void addCell(Pane pane) {
	pane.setStyle(CELL_SELECT_STYLE);
	selectedCells.add(pane);
    }

    /**
     * Removes a gridPane cell from selection model
     * @param pane
     */
    public void removeCell(Pane pane) {
	pane.setStyle(CELL_DESELECT_STYLE);
	selectedCells.remove(pane);
    }

    /**
     * Sets a path block node as "selected"
     * @param node
     */
    public void addNode(Node node) {
	if (selectedNode != null) {
	    selectedNode.setEffect(null);
	}
	ColorAdjust colorAdjust = new ColorAdjust();
	colorAdjust.setBrightness(0.5);
	node.setEffect(colorAdjust);
	selectedNode = node;
    }

    /**
     * Sets a path block node as "unselected"
     * @param node
     */
    public void removeNode(Node node) {
	node.setEffect(null);
	selectedNode = null;
    }

    /**
     * Returns a set that contains all of the selected gridPane cells
     * @return
     */
    public Set<Pane> getSelectedCells() {
	return selectedCells;
    }

    /**
     * Sets the selected path block node as "copied"
     */
    public void copy() {
	copyNode = selectedNode;
    }

    /**
     * Pastes the selected path block node into every selected gridPane cell
     */
    public void paste() {
	if (copyNode != null && copyNode instanceof ImageView) {
	    for (Pane cell: selectedCells) {
		ImageView copyImage = new ImageView(((ImageView) copyNode).getImage());
		copyImage.setFitHeight(mySize);
		copyImage.setFitWidth(mySize);
		if (copyImage.getId() == CreatePathGrid.START) {
		    myCheckGrid.add(new Label(CreatePathGrid.START), GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
		} else if (copyImage.getId() == CreatePathGrid.PATH) {
		    myCheckGrid.add(new Label(CreatePathGrid.PATH), GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
		} else if (copyImage.getId() == CreatePathGrid.END) {
		    myCheckGrid.add(new Label(CreatePathGrid.END), GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
		}
		myGrid.add(copyImage, GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
	    }
	}
	
//	for (String key: map.keySet()) {
//	    String imageKey = key.substring(1);
//	    List<Point> pointList = map.get(key);
//	    for (int i = 0; i < pointList.size(); i++) {
//		Point point = pointList.get(i);
//		DraggableImage path = new DraggableImage(new Image(imageKey));
//		path.setDraggable(checkGrid, (int)point.getY(), (int)point.getX());
//		path.getPathImage().setFitWidth(pathSize);
//		path.getPathImage().setFitHeight(pathSize);
//		grid.add(path.getPathImage(), (int)point.getX(), (int)point.getY());
//		if (key.equals("s"+myView.getObjectAttribute("Path", "", "myStartImage"))) {
//		    checkGrid.add(new Label(START), (int)point.getX(), (int)point.getY());
//		    path.getPathImage().setId(START);
//		} else if (key.equals("p"+myView.getObjectAttribute("Path", "", "myPathImage"))) {
//		    checkGrid.add(new Label(PATH), (int)point.getX(), (int)point.getY());
//		    path.getPathImage().setId(PATH);
//		} else if (key.equals("e"+myView.getObjectAttribute("Path", "", "myEndImage"))) {
//		    checkGrid.add(new Label(END), (int)point.getX(), (int)point.getY());
//		    path.getPathImage().setId(END);
//		}
//		GridPane.setFillWidth(path.getPathImage(), true);
//		GridPane.setFillHeight(path.getPathImage(), true);
//	    }
//	}
    }
}

