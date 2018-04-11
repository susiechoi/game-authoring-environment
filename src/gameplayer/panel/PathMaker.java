package gameplayer.panel;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
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

    public GridPane populateGrid(Map<String, List<Point>> map, String backgroundImage) { //populates grid that is
	grid = new GridPane();
	grid.setMaxSize(1000, 750);

	for (int x = 0 ; x < grid.getColumnCount(); x++) {
	    for (int y = 0 ; y < grid.getRowCount(); y++) {
		StackPane cell = new StackPane();
//		needs to be fx styled
//		grid.setStyle(backgroundImage);
		grid.add(cell, x, y);

	    }
	}
	setGridConstraints(grid);
	addImagesToGrid(map);
	return grid;
    }


    public void addImagesToGrid(Map<String, List<Point>> map) {
	for (String key: map.keySet()) { //goes through images
	    List<Point> pointList = map.get(key);
	    for (int i = 0; i < pointList.size(); i++) {
		Point point = pointList.get(i);
		ImageView image = new ImageView(new Image(key));
		image.setFitWidth(60);
		image.setFitHeight(60);
		GridPane.setFillWidth(image, true);
		GridPane.setFillHeight(image, true);
		grid.add(image, (int)point.getX(), (int)point.getY());
	    }
	}

    }


    public void setGridConstraints(GridPane grid) {
	for (int i = 0; i < 1000/60; i++) {
	    ColumnConstraints colConst = new ColumnConstraints();
	    colConst.setPrefWidth(60);
	    grid.getColumnConstraints().add(colConst);
	}
	for (int i = 0; i < 750/60; i++) {
	    RowConstraints rowConst = new RowConstraints();
	    rowConst.setPrefHeight(60);
	    grid.getRowConstraints().add(rowConst);         
	}
    }
}

