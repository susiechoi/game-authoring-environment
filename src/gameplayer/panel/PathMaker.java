package gameplayer.panel;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class PathMaker {

	private GridPane grid;
	private int myPathSize;

	public GridPane initGrid(Map<String, List<Point>> map, String backgroundImage, int pathSize) {
		grid = new GridPane();
		grid.setMaxSize(1020, 650);
		grid.setStyle("-fx-background-image: url(" + backgroundImage + ")"); 
		myPathSize = pathSize;
		setGridConstraints(grid);
		addImagesToGrid(map);
		return grid;
	}

	public void addImagesToGrid(Map<String, List<Point>> map) {
		for (String key: map.keySet()) { //goes through images
			List<Point> pointList = map.get(key);
			for (int i = 0; i < pointList.size(); i++) {
				Point point = pointList.get(i);
				// TODO handle IllegalArgumentException where key is invalid
				ImageView image = new ImageView();
				try{
				image = new ImageView(new Image(key));
				}
				catch(IllegalArgumentException e){
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

	public void setGridConstraints(GridPane grid) {
		for (int i = 0; i < 1020/myPathSize; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPrefWidth(myPathSize);
			grid.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i <  750/myPathSize; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPrefHeight(myPathSize);
			grid.getRowConstraints().add(rowConst);         
		}
	}
}

