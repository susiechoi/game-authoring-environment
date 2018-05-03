package gameplayer.panel;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import com.sun.javafx.tools.packager.Log;
import gameplayer.GameplayerAlert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


/**
 * @Author Andrew Arnold & Erik Riis
 */

public class PathMaker {


    private GridPane grid;
    private int myPathSize;
    private GameplayerAlert ALERT;

    public GridPane initGrid(Map<String, List<Point>> map, String backgroundImage, int pathSize, int width, int height) {
	grid = new GridPane();

	grid.setMaxSize(width, height);
	grid.setMinSize(width, height);

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
		ImageView image = new ImageView();
		try{
		    image = new ImageView(new Image(imageKey));
		}
		catch(IllegalArgumentException e){
		    Log.debug(e);
		    image = new ImageView();
			ALERT = new GameplayerAlert(e.getMessage());
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
