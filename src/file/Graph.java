/**
 * @author susiechoi
 * The purpose of the Graph object is to allow any outside class to generate and display a graph of points (either from a file or manually-inputed),
 * 	thereby invalidating the need for the GraphScreen in the submitted project's implementation. 
 * The new Graph object addresses a few issues from GraphScreen:
 * 1. As an abstract class, GraphScreen operated under the assumption that the primary purpose of Screens with graphs would be to display those graphs.
 * 		Classes which were not subclasses of GraphScreen did not have access to graphing functionality. 
 * 		Now, as an object that does not fit into a hierarchy, the Graph object enables the construction of graphs from any Screen. 
 * 		Thus, inheritance has effectively been replaced by composition in the classes/Screens that require graph displays. 
 * 2. GraphScreen incorporated assumptions about aspects of the graph such as how graph files were formatted and whether graph axes should be auto-scaled.
 * 		These are now customizable by clients through Properties files and method arguments. 
 * 3. GraphScreen rested on the assumption that the source of datapoints would be saved files. Flexibility has been introduced in Graph
 * 		by allowing clients to add points to the graph in real time. This opens the door for new applications of graph displays, such as in 
 * 		generating real-time visualizations of score during gameplay. 
 * 4. Methods have been fragmented so as to enhance flexibility associated with changing techniques at a specific step of the graph creation or point-parsing process.
 */

package file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.sun.javafx.tools.packager.Log;
import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import frontend.PropertiesReader;
import file.DataPointWriter; 

public class Graph {

	private LineChart<Number, Number> myChart; 
	private NumberAxis myXAxis; 
	private NumberAxis myYAxis; 
	private PropertiesReader myPropertiesReader; 

	/**
	 * Creates LineChart consisting of axes
	 * @param title - Title label for LineChart
	 * @param xLabel - X-axis label for LineChart
	 * @param yLabel - Y-axis label for LineChart
	 */
	public Graph(String title, String xLabel, String yLabel) {
		NumberAxis x = new NumberAxis();
		x.setLabel(xLabel);
		myXAxis = x; 
		NumberAxis y = new NumberAxis(); 
		y.setLabel(yLabel);
		myYAxis = y; 
		LineChart<Number, Number> chart = new LineChart<Number, Number>(x, y);
		chart.setTitle(title);
		myChart = chart; 
		myPropertiesReader = new PropertiesReader();
	}

	/**
	 * Adds points from filepath to the LineChart
	 * @param filepath - path containing files
	 * @param fitAxes - whether or not the axes of the LineChart should fit to the ranges of the x- & y-axes
	 * @return boolean representing the success of finding the file at the specified, parameterized filepath
	 */
	public boolean addPointsFromFile(String filepath, boolean fitAxes) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = makeBufferedReader(filepath);
		} catch (FileNotFoundException e) {
			Log.debug(e);
			return false; 
		}

		try {
			myChart.getData().add(addToSeriesFromFile(bufferedReader, fitAxes));
		} catch (NumberFormatException e) {
			Log.debug(e);
		} catch (IOException e) {
			Log.debug(e);
		} 

		return true; 
	}

	/**
	 * Adds argument-specified points to the LineChart
	 * @param xPoints - List containing x-points 
	 * @param yPoints - List containing y-points 
	 * @return boolean representing the successful pairing of x- and y-points & the addition of these points to the LineChart
	 */
	public boolean addCoordinate(List<Double> xPoints, List<Double> yPoints) {
		Series<Number, Number> series = new XYChart.Series<>(); 
		for (int i=0; i<xPoints.size(); i++) {
			if (i >= yPoints.size()) {
				return false; 
			}
			addCoordinateToSeries(series, xPoints.get(i), yPoints.get(i));
		}
		myChart.getData().add(series);
		return true; 
	}

	/**
	 * Returns the generated Chart for the client to attach anywhere on their root
	 * @return genreated Chart
	 */
	public Chart getChart() {
		return myChart; 
	}

	private BufferedReader makeBufferedReader(String filepath) throws FileNotFoundException {
		FileReader fileReader = new FileReader(filepath); 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		return bufferedReader; 
	}

	private Series<Number, Number> addToSeriesFromFile(BufferedReader bufferedReader, boolean fitAxes) throws NumberFormatException, IOException {
		Series<Number, Number> series = new XYChart.Series<>(); 
		String point;
		String delimiter = "";
		int xyCoordinateLength = -1; 
		int xIndex = -1; 
		int yIndex = -1; 
		try {
			delimiter = parseProperty(DataPointWriter.DEFAULT_DELIMITER_KEY);
			xyCoordinateLength = Integer.parseInt(parseProperty(DataPointWriter.DEFAULT_COORDINATELENGTH_KEY));
			xIndex = Integer.parseInt(parseProperty(DataPointWriter.DEFAULT_XINDEX_KEY));
			yIndex = Integer.parseInt(parseProperty(DataPointWriter.DEFAULT_YINDEX_KEY));
		} catch (MissingPropertiesException e) {
			Log.debug(e);
		}
		while ((point = bufferedReader.readLine()) != null) {
			String[] xyPoints = point.split(delimiter);
			if (xyPoints.length == xyCoordinateLength) {
				Double xPoint = Double.parseDouble(xyPoints[xIndex]);
				Double yPoint = Double.parseDouble(xyPoints[yIndex]);
				addCoordinateToSeries(series, xPoint, yPoint);
				if (fitAxes) {
					readjustAxisBounds(myXAxis, xPoint);
					readjustAxisBounds(myYAxis, yPoint);
				}
			}
		}
		return series; 
	}

	private void addCoordinateToSeries(Series<Number, Number> series, Double xPoint, Double yPoint) {
		series.getData().add(new XYChart.Data<Number, Number>(xPoint, yPoint));
	}

	private String parseProperty(String key) throws MissingPropertiesException {
		return myPropertiesReader.findVal(DataPointWriter.DEFAULT_CONSTANTS_FILEPATH, key);
	}

	private void readjustAxisBounds(NumberAxis axis, Double point) {
		if (axis.getLowerBound() > point) {
			axis.setLowerBound(point);
		}
		if (axis.getUpperBound() < point) {
			axis.setUpperBound(point);
		}
	}

}