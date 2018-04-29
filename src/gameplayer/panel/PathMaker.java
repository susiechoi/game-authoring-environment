package gameplayer.panel;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import com.sun.javafx.tools.packager.Log;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class PathMaker {

	private GridPane grid;
	private int myPathSize;

	public GridPane initGrid(Map<String, List<Point>> map, String backgroundImage, int pathSize, int col, int row, Pane gamePane) {
		grid = new GridPane();
		grid.setGridLinesVisible(true);
		grid.setStyle("-fx-background-image: url(" + backgroundImage + ")"); 
		grid.prefHeightProperty().bind(gamePane.heightProperty());
		grid.prefWidthProperty().bind(gamePane.widthProperty());
		grid.maxHeightProperty().bind(gamePane.heightProperty());
		grid.maxWidthProperty().bind(gamePane.widthProperty());
		grid.minHeightProperty().bind(gamePane.heightProperty());
		grid.minWidthProperty().bind(gamePane.widthProperty());
		return grid; 
	}

	//	NumberBinding maxScale = Bindings.min(gamePane.widthProperty(), gamePane.heightProperty());
	//	grid.scaleXProperty().bind(maxScale);
	//	grid.scaleYProperty().bind(maxScale);

	public void addImagesToGrid(Map<String, List<Point>> map) {
		for (String key: map.keySet()) { //goes through images
			List<Point> pointList = map.get(key);
			for (int i = 0; i < pointList.size(); i++) {
				Point point = pointList.get(i);
				// TODO handle IllegalArgumentException where key is invalid
				ImageView image = new ImageView();
				try{
					image = new ImageView(new Image("file:"+key, 50, 50, true, true));
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

	private void setGridConstraints(GridPane grid, int numRow, int numCol) {
		for (int i = 0; i < numCol; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100.0/numCol);
			grid.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < numRow; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0/numRow);
			grid.getRowConstraints().add(rowConst);         
		}
	}
}

