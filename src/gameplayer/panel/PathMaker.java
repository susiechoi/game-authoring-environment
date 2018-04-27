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


	public GridPane initGrid(Map<String, List<Point>> map, String backgroundImage) {
		grid = new GridPane();
		grid.setMaxSize(1020, 650);
		grid.setStyle("-fx-background-image: url('file:images/generalbackground.jpg')"); 
		setGridConstraints(grid);
		addImagesToGrid(map);
		return grid;
	}

	public void addImagesToGrid(Map<String, List<Point>> map) {
		for (String key: map.keySet()) { //goes through images
			List<Point> pointList = map.get(key);
			for (int i = 0; i < pointList.size(); i++) {
				Point point = pointList.get(i);
				ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(key), 50, 50, true, true));
				image.setFitWidth(40);
				image.setFitHeight(40);
				GridPane.setFillWidth(image, true);
				GridPane.setFillHeight(image, true);
				grid.add(image, (int)point.getX(), (int)point.getY());
			}
		}
	}

	public void setGridConstraints(GridPane grid) {
		for (int i = 0; i < 1020/40; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPrefWidth(60);
			grid.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i <  750/40; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPrefHeight(60);
			grid.getRowConstraints().add(rowConst);         
		}
	}
}

