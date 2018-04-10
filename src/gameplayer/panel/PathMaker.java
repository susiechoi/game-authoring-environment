package gameplayer.panel;

import java.util.HashMap;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class PathMaker {

    private int colIndex;
    private int rowIndex;
    private GridPane grid;

    private HashMap<String, List<Point2D>> imageMap = new HashMap<String, List<Point2D>>(); //this is passed

    public GridPane populateGrid() { //populates grid that is

	grid = new GridPane();
	grid.setMaxSize(1000, 750);

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
			    //set draggable images (towers), need to make these draggable images
			    Image path = db.getImage(); 
			    ImageView pathImageView = new ImageView(path);
			    pathImageView.fitWidthProperty().bind(cell.widthProperty()); 
			    pathImageView.fitHeightProperty().bind(cell.heightProperty()); 
			    grid.add(pathImageView, colIndex, rowIndex);
			    success = true;
			}
			event.setDropCompleted(success);
			event.consume();
		    }
		});
		grid.add(cell, x, y);
	    }
	}
	return grid;
    }

    public void addImagesToGrid() {
	for (String key: imageMap.keySet()) { //goes through images
	    for (int i = 0; i < imageMap.keySet().size(); i++) {
		Point2D point = imageMap.get(key).get(0);
		grid.add(new ImageView(new Image(key)), (int)point.getX(), (int)point.getY());
	    }
	}
    }

    //	public void setGridConstraints(GridPane grid, HashMap<String, List<Point2D>> map) {
    //		imageMap = map;
    //		grid.getColumnConstraints().clear();
    //		grid.getRowConstraints().clear();
    //		for (int i = 0; i < 1000/60; i++) {
    //			ColumnConstraints colConst = new ColumnConstraints();
    //			colConst.setPrefWidth(60);
    //			grid.getColumnConstraints().add(colConst);
    //		}
    //		for (int i = 0; i < 750/60; i++) {
    //			RowConstraints rowConst = new RowConstraints();
    //			rowConst.setPrefHeight(60);
    //			grid.getRowConstraints().add(rowConst);         
    //		}
    //	populateGrid();
    //}
}