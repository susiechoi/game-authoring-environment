/**
 * @author susiechoi
 * A component of the Graph object, the CooordinateAdder manages the additions of points to the LineChart. Its single responsibility is managing the Model,
 * 		as all of its methods precipitate changes (e.g. addition of points, readjustment of axis bounds) to the LineChart being built, thereby contributing to the 
 * 		compartmentalization of the parsing and plotting processes. 
 * CoordinateAdder makes fewer assumptions about how the graph should be formatted by accepting a boolean fitAxes parameter to determine whether to autofit axis bounds 
 * 		based on the value of coordinates. 
 * As in DataPointParser, methods have been fragmented so as to enhance flexibility associated with changing a specific step of the graph creation process. 
 */

package file;

import java.util.List;

import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

class CoordinateAdder {
	
	private LineChart<Number, Number> myChart; 
	private NumberAxis myXAxis;
	private NumberAxis myYAxis;
	private boolean myFitAxes; 
	
	protected CoordinateAdder(LineChart<Number, Number> chart, NumberAxis x, NumberAxis y, boolean fitAxes) {
		myChart = chart; 
		myXAxis = x;
		myYAxis = y; 
		myFitAxes = fitAxes; 
	}
	
	protected boolean addCoordinate(List<Double> xPoints, List<Double> yPoints) {
		Series<Number, Number> series = new XYChart.Series<>(); 
		for (int i=0; i<xPoints.size(); i++) {
			if (i >= yPoints.size()) {
				return false; 
			}
			double xPoint = xPoints.get(i); 
			double yPoint = yPoints.get(i);
			addCoordinateToSeries(series, xPoints.get(i), yPoints.get(i));
			if (myFitAxes) {
				axesBoundsCheck(xPoint, yPoint);
			}
		}
		myChart.getData().add(series);
		return true; 
	}

	protected void addCoordinateToSeries(Series<Number, Number> series, Double xPoint, Double yPoint) {
		series.getData().add(new XYChart.Data<Number, Number>(xPoint, yPoint));
	}
	
	protected void addPointsToChart(Series<Number, Number> series) {
		myChart.getData().add(series);
	}
	
	private void axesBoundsCheck(Double xPoint, Double yPoint) {
		readjustAxisBounds(myXAxis, xPoint);
		readjustAxisBounds(myYAxis, yPoint);
	}

	private void readjustAxisBounds(NumberAxis axis, Double point) {
		if (axis.getLowerBound() > point) {
			axis.setLowerBound(point);
		}
		if (axis.getUpperBound() < point) {
			axis.setUpperBound(point);
		}
	}
	
	protected Chart getChart() {
		return myChart;
	}
	
}