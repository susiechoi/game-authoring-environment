/**
 * Other classes don't need dependency on LineChart
 * Can choose to include tick marks 
 * Representable by doubles
 */

package file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.sun.javafx.tools.packager.Log;
import authoring.frontend.exceptions.MissingPropertiesException;
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
	
	
	public boolean addPointsFromFile(String filepath, String delimiter, boolean fitAxes) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = makeBufferedReader(filepath);
		} catch (FileNotFoundException e) {
			Log.debug(e);
			return false; 
		}

		try {
			myChart.getData().add(addToSeriesFromFile(bufferedReader, delimiter, fitAxes));
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
	
	private Series<Number, Number> addToSeriesFromFile(BufferedReader bufferedReader, String delimiter, boolean fitAxes) throws NumberFormatException, IOException {
		Series<Number, Number> series = new XYChart.Series<>(); 
		String point;
		int xyCoordinateLength = -1; 
		int xIndex = -1; 
		int yIndex = -1; 
		try {
			xyCoordinateLength = parseProperty(DataPointWriter.DEFAULT_COORDINATELENGTH_KEY);
			xIndex = parseProperty(DataPointWriter.DEFAULT_XINDEX_KEY);
			yIndex = parseProperty(DataPointWriter.DEFAULT_YINDEX_KEY);
		} catch (MissingPropertiesException e) {
			Log.debug(e);
		}
		while ((point = bufferedReader.readLine()) != null) {
			String[] xyPoints = point.split(delimiter);
			if (xyPoints.length == xyCoordinateLength) {
				Double xPoint = Double.parseDouble(xyPoints[xIndex]);
				Double yPoint = Double.parseDouble(xyPoints[yIndex]);
				series.getData().add(new XYChart.Data<Number, Number>(xPoint, yPoint));
				if (fitAxes) {
					readjustAxisBounds(myXAxis, xPoint);
					readjustAxisBounds(myYAxis, yPoint);
				}
			}
		}
		return series; 
	}
	
	private int parseProperty(String key) throws MissingPropertiesException {
		String val = myPropertiesReader.findVal(DataPointWriter.DEFAULT_CONSTANTS_FILEPATH, key);
		int parsedVal = Integer.parseInt(val);
		return parsedVal; 
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