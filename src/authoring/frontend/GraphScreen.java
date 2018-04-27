package authoring.frontend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import frontend.Screen;
import frontend.View;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

abstract class GraphScreen extends AuthoringScreen {

	LineChart<Number, Number> myGraph; 

	public GraphScreen(AuthoringView view) {
		super(view);
	}

	protected LineChart<Number, Number> makeGraph(boolean legendVisible) {
		NumberAxis x = new NumberAxis();
		x.setLabel(getErrorCheckedPrompt("Time"));
		x.isAutoRanging(); 
		x.setTickLabelsVisible(false);
		NumberAxis y = new NumberAxis(); 
		y.setLabel(getErrorCheckedPrompt("Score"));
		myGraph = new LineChart<Number, Number>(x, y);
		myGraph.setLegendVisible(legendVisible);
		return myGraph; 
	}

	protected void addPointsToGraph(String filepath) {
		if (myGraph != null) {
			Series series = new XYChart.Series<>(); 
			myGraph.getData().add(series); 

			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(filepath));
			} catch (FileNotFoundException e) {
				getView().loadErrorScreen("NoGraph");
			}
			String point = null;
			try {
				while ((point = br.readLine()) != null) {
					String[] xyCoors = point.split("\\s+");
					if (xyCoors.length == 2) {
						series.getData().add(new XYChart.Data(Integer.parseInt(xyCoors[0]), Integer.parseInt(xyCoors[1])));
					}
				}
			} catch (NumberFormatException | IOException e) {
				getView().loadErrorAlert("InvalidValues");
			}
		}
	}

}