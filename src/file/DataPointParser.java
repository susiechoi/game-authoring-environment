/**
 * @author susiechoi
 * A component of the Graph object, the DataPointParser coordinates the addition of data points to the LineChart that have been read from a file
 * 		by receiving file information from Graph & communicating with CoordinateAdder to ensure these data points are added. 
 * The single responsibility of DataPointParser is to read and parse datapoints from a file; in comparison, in the original implementations of GraphScreen, 
 * 		GraphScreen had to both parse and plot datapoints, thereby deviating further from the Single Responsibility Principle. 
 * As compared to GraphScreen, DataPointParser makes fewer assumptions about the nature of the files that it is parsing. For example, 
 * 		Properties files are used to determine what is used as a delimiter to separate x- from y-points. 
 * Methods have been fragmented so as to enhance flexibility associated with changing a specific step of the point-parsing process,
 * 		such as the initialization of the file-reading structure (makeBufferedReader method) or the source of properties values (parseProperty method). 
 */

package file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.sun.javafx.tools.packager.Log;

import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.PropertiesReader;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

class DataPointParser {

	private CoordinateAdder myCoordinateAdder;
	private PropertiesReader myPropertiesReader; 
	
	protected DataPointParser(CoordinateAdder coordinateAdder) {
		myCoordinateAdder = coordinateAdder;
		myPropertiesReader = new PropertiesReader(); 
	}
	
	protected boolean addPointsFromFile(String filepath) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = makeBufferedReader(filepath);
		} catch (FileNotFoundException e) {
			Log.debug(e);
			return false; 
		}

		try {
			myCoordinateAdder.addPointsToChart(addToSeriesFromFile(bufferedReader));
		} catch (NumberFormatException e) {
			Log.debug(e);
		} catch (IOException e) {
			Log.debug(e);
		} 

		return true; 
	}
	
	private BufferedReader makeBufferedReader(String filepath) throws FileNotFoundException {
		FileReader fileReader = new FileReader(filepath); 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		return bufferedReader; 
	}
	
	private Series<Number, Number> addToSeriesFromFile(BufferedReader bufferedReader) throws NumberFormatException, IOException {
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
				myCoordinateAdder.addCoordinateToSeries(series, xPoint, yPoint);
			}
		}
		return series; 
	}
	
	private String parseProperty(String key) throws MissingPropertiesException {
		return myPropertiesReader.findVal(DataPointWriter.DEFAULT_CONSTANTS_FILEPATH, key);
	}
	
}