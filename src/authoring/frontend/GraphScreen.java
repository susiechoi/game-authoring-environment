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

	protected NumberAxis myX; 
	protected NumberAxis myY;
	protected LineChart<Number, Number> myGraph; 

	public GraphScreen(AuthoringView view) {
		super(view);
		setSaved(); 
	}

	protected LineChart<Number, Number> makeGraph(boolean legendVisible) {
		myX = new NumberAxis();
		myX.setLabel(getErrorCheckedPrompt("Time"));
//		myX.setTickLabelsVisible(false);
		myX.setForceZeroInRange(false);
		myY = new NumberAxis(); 
		myY.setLabel(getErrorCheckedPrompt("Score"));
		myGraph = new LineChart<Number, Number>(myX, myY);
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

			boolean min = true; 
			try {
				while ((point = br.readLine()) != null) {
					String[] xyCoors = point.split("\\s+");
					if (xyCoors.length == 2) {
						series.getData().add(new XYChart.Data(Integer.parseInt(xyCoors[0]), Integer.parseInt(xyCoors[1])));
						if (min) {
							if (myX.getLowerBound() == 0 ||  (myX.getLowerBound() > Integer.parseInt(xyCoors[0]))) {
								//System.out.println("MY MIN IS "+Integer.parseInt(xyCoors[0]));
								myX.setLowerBound(Integer.parseInt(xyCoors[0]));
							}
							min = false; 
						}
						if (myX.getUpperBound() < Integer.parseInt(xyCoors[0])) {
						//	System.out.println("mY MAX IS "+Integer.parseInt(xyCoors[0]));
							myX.setUpperBound(Integer.parseInt(xyCoors[0]));
						}
					}
				}
			} catch (NumberFormatException | IOException e) {
				getView().loadErrorAlert("InvalidValues");
			}
		}
	}

}