/**
 * @author susiechoi
 * The purpose of the Graph object is to allow any outside class to generate and display a graph of points (either from a file or manually-inputed),
 * 		thereby invalidating the need for the GraphScreen in the submitted project's implementation. 
 * The new Graph object addresses a few issues from GraphScreen:
 * 1. As an abstract class, GraphScreen operated under the assumption that the primary purpose of Screens with graphs would be to display those graphs.
 * 		Classes which were not subclasses of GraphScreen did not have access to graphing functionality. 
 * 		Now, as an object that does not fit into a hierarchy, the Graph object enables the construction of graphs from any Screen. 
 * 		Thus, inheritance has effectively been replaced by composition in the classes/Screens that require graph displays. 
 * 2. GraphScreen rested on the assumption that the source of datapoints would be saved files. Flexibility has been introduced in Graph
 * 		by allowing clients to add points to the graph in real time, through the addCoordinate method. This opens the door for new applications of graph displays, 
 * 		such as in generating real-time visualizations of score during gameplay. 
 * 3. The Graph class embodies the usage of composition over inheritance. Rather than having a Graph abstract class with two subclasses 
 * 		(one for managing graph creation from file data points, another for managing manaully-inputed data points), the Graph class is composed of two objects --
 * 		a DataPointParser and a CoordinateAdder, which may work independently or together to plot data points from a file and/or via method arguments. 
 * 4. Clients no longer require dependencies on elements of graph creation such as LineChart, Axis, or Series, as they need only interface with the Graph object,
 * 		whose parameters and return values are commonly-used Java data types. 
 */

package file;

import java.util.List;

import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis; 

public class Graph {

	private DataPointParser myDataPointParser; 
	private CoordinateAdder myCoordinateAdder;
	
	/**
	 * Creates LineChart consisting of axes & creates CoordinateAdder, DataPointParser to prepare for further additions to this Chart
	 * @param title - Title label for LineChart
	 * @param xLabel - X-axis label for LineChart
	 * @param yLabel - Y-axis label for LineChart
	 * @param fitAxes - whether or not the axes of the LineChart should fit to the ranges of the x- & y-axes
	 */
	public Graph(String title, String xLabel, String yLabel, boolean fitAxes) {
		NumberAxis x = new NumberAxis();
		x.setLabel(xLabel);
		NumberAxis y = new NumberAxis(); 
		y.setLabel(yLabel);
		LineChart<Number, Number> chart = new LineChart<Number, Number>(x, y);
		chart.setTitle(title);
		myCoordinateAdder = new CoordinateAdder(chart, x, y, fitAxes);
		myDataPointParser = new DataPointParser(myCoordinateAdder);
	}
	
	/**
	 * Adds points from filepath to the LineChart by invoking DataPointParser functionality
	 * @param filepath - path containing files
	 * @return boolean representing the success of finding the file at the specified, parameterized filepath
	 */
	public boolean addPointsFromFile(String filepath) {
		return myDataPointParser.addPointsFromFile(filepath);
	}

	/**
	 * Adds argument-specified points to the LineChart by invoking CoordinateAdder functionality
	 * @param xPoints - List containing x-points 
	 * @param yPoints - List containing y-points 
	 * @return boolean representing the successful pairing of x- and y-points & the addition of these points to the LineChart
	 */
	public boolean addCoordinate(List<Double> xPoints, List<Double> yPoints) {
		return myCoordinateAdder.addCoordinate(xPoints, yPoints);
	}

	/**
	 * Returns the generated Chart for the client to attach anywhere on their root
	 * @return genreated Chart
	 */
	public Chart getChart() {
		return myCoordinateAdder.getChart(); 
	}

}