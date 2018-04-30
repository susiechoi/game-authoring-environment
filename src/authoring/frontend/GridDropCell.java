package authoring.frontend;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GridDropCell {
    private GridPane myGrid;
    private GridPane myCheckGrid;

    GridDropCell(GridPane grid, GridPane checkGrid, int col, int row) {
	StackPane cell = new StackPane();
	myGrid = grid;
	myCheckGrid = checkGrid;
	setCellDragProperties(cell, col, row);
    }

    private Pane setCellDragProperties(Pane cell, int col, int row) {
	cell.setOnDragOver(new EventHandler <DragEvent>() {
	    public void handle(DragEvent event) {
		if (event.getDragboard().hasImage()) {
		    event.acceptTransferModes(TransferMode.ANY);
		}
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
		    path.setDraggable(myCheckGrid, row, col);
		    path.getPathImage().fitWidthProperty().bind(cell.widthProperty()); 
		    path.getPathImage().fitHeightProperty().bind(cell.heightProperty());
		    myGrid.add(path.getPathImage(), col, row);
		    if (((ImageView) event.getGestureSource()).getId() == "start") {	
			path.getPathImage().setId("start");
			myCheckGrid.add(new Label("start"), col, row);
		    } else if (((ImageView) event.getGestureSource()).getId() == "end") {
			path.getPathImage().setId("end");
			myCheckGrid.add(new Label("end"), col, row);
		    } else if (((ImageView) event.getGestureSource()).getId() == "path") {
			path.getPathImage().setId("path");
			myCheckGrid.add(new Label("path"), col, row);
		    }
		    success = true;
		}
		event.setDropCompleted(success);
		event.consume();
	    }
	});
	myGrid.add(cell, col, row);
	return cell;
    }
}
