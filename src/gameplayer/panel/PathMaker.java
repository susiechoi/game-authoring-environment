package gameplayer.panel;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class PathMaker {

    private int colIndex;
    private int rowIndex;
    private GridPane grid;

    public GridPane populateGrid(Map<String, List<Point>> map, String backgroundImage) { //populates grid that is

	for (int x = 0 ; x < grid.getColumnCount(); x++) {
	    for (int y = 0 ; y < grid.getRowCount(); y++) {
		StackPane cell = new StackPane();


		final int col = x;
		final int row = y;

		//		needs to be fx styled
		//		grid.setStyle(backgroundImage);


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
			    ImageView pathImage = new ImageView(db.getImage()); 

			    pathImage.fitWidthProperty().bind(cell.widthProperty()); 
			    pathImage.fitHeightProperty().bind(cell.heightProperty()); 
			    grid.add(pathImage, colIndex, rowIndex);
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


    public void addImagesToGrid(HashMap<String, List<Point>> map) {
	for (String key: map.keySet()) { //goes through images
	    List<Point> pointList = map.get(key);
	    for (int i = 0; i < pointList.size(); i++) {
		Point point = pointList.get(i);
		grid.add(new ImageView(new Image(key)), (int)point.getX(), (int)point.getY());
	    }
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

