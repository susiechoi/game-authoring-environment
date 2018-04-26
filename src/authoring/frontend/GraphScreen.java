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

public class GraphScreen extends Screen {

	public static final String DEFAULT_CSS = "styling/GameAuthoringStartScreen.css";
	private String myGraphFilepath; 
	
	public GraphScreen(String filepath) {
		myGraphFilepath = filepath; 
		setStyleSheet(DEFAULT_CSS);
	}

	@Override
	public Parent makeScreenWithoutStyling() {		
		NumberAxis x = new NumberAxis();
		NumberAxis y = new NumberAxis(); 
		LineChart<Number, Number> graph = new LineChart<Number, Number>(x, y);
		XYChart.Series series = new XYChart.Series<>(); 
		graph.setTitle("Score Over Game Play Time");
		graph.getData().add(series); 

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(myGraphFilepath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graph;
	}

	@Override
	protected View getView() {
		// TODO Auto-generated method stub
		return null;
	}

}