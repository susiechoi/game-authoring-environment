package gameplayer.panel;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import com.sun.javafx.tools.packager.Log;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class PathMaker {

    private GridPane grid;
    private int myPathSize;

    public GridPane initGrid(Map<String, List<Point>> map, String backgroundImage, int pathSize, int width, int height) {
	grid = new GridPane();
	grid.setMaxSize(width, height);
	grid.setMinSize(width, height);
	grid.setGridLinesVisible(true);


	//	grid.prefHeightProperty().bind(gamePane.heightProperty());
	//	grid.prefWidthProperty().bind(gamePane.widthProperty());
	//	grid.maxHeightProperty().bind(gamePane.heightProperty());
	//	grid.maxWidthProperty().bind(gamePane.widthProperty());
	//	grid.minHeightProperty().bind(gamePane.heightProperty());
	//	grid.minWidthProperty().bind(gamePane.widthProperty());
	//	NumberBinding maxScale = Bindings.min(gamePane.widthProperty(), gamePane.heightProperty());
	//	grid.scaleXProperty().bind(maxScale);
	//	grid.scaleYProperty().bind(maxScale);

	myPathSize = pathSize;
	setGridConstraints(grid, width, height);
	if(map!= null) {
	    addImagesToGrid(map);
	}
	return grid;
    }

    private void addImagesToGrid(Map<String, List<Point>> map) {
	for (String key: map.keySet()) {
	    String imageKey = key.substring(1);
	    List<Point> pointList = map.get(key);
	    for (int i = 0; i < pointList.size(); i++) {
		Point point = pointList.get(i);
		// TODO handle IllegalArgumentException where key is invalid
		ImageView image = new ImageView();
		try{
		    image = new ImageView(new Image(imageKey));

		}
		catch(IllegalArgumentException e){
		    Log.debug(e);
		    image = new ImageView(); //TODO this should not be hardcoded
		}
		image.setFitWidth(myPathSize);
		image.setFitHeight(myPathSize);
		GridPane.setFillWidth(image, true);
		GridPane.setFillHeight(image, true);
		grid.add(image, (int)point.getX(), (int)point.getY());
	    }
	}
    }

    private void setGridConstraints(GridPane grid, int width, int height) {
	for (int i = 0; i < width/myPathSize; i++) {
	    ColumnConstraints colConst = new ColumnConstraints();
	    colConst.setPrefWidth(myPathSize);
	    grid.getColumnConstraints().add(colConst);
	}
	for (int i = 0; i < height/myPathSize; i++) {
	    RowConstraints rowConst = new RowConstraints();
	    rowConst.setPrefHeight(myPathSize);
	    grid.getRowConstraints().add(rowConst);         
	}
    }



}