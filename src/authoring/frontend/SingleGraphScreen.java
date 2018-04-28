package authoring.frontend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SingleGraphScreen extends GraphScreen {
	
	private String myGameFilepath; 

	protected SingleGraphScreen(AuthoringView view, String filepath) {
		super(view);
		myGameFilepath = filepath; 
		setSaved(); 
	}

	@Override
	public Parent makeScreenWithoutStyling() {	
		VBox vb = new VBox();
		
		Text graphTitle = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("GraphTitle"));
		LineChart<Number, Number> graph = makeGraph(false); 
		addPointsToGraph(myGameFilepath);
		vb.getChildren().addAll(graphTitle, graph, setupBackButton());
		
		return vb;
	}
}
